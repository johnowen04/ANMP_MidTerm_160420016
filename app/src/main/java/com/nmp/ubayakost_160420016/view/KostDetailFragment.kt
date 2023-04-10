package com.nmp.ubayakost_160420016.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.util.loadImage
import com.nmp.ubayakost_160420016.viewmodel.KostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_kost_detail.*

class KostDetailFragment : Fragment() {
    private lateinit var kostViewModel: KostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            it.drawerButton.visibility = View.GONE
            it.bottomNav.visibility = View.GONE
            it.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kost_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = KostDetailFragmentArgs.fromBundle(requireArguments()).id
        kostViewModel = ViewModelProvider(this)[KostViewModel::class.java]
        kostViewModel.fetchDetails(id) { }

        var fromFragment: String

        arguments.let {
            fromFragment = KostDetailFragmentArgs.fromBundle(requireArguments()).fromFragment
        }

        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = when (fromFragment) {
                    "home" -> KostDetailFragmentDirections.actionKostDetailToHome()
                    else -> KostDetailFragmentDirections.actionKostDetailToFavorite()
                }
                Navigation.findNavController(view).navigate(action)
            }
        })

        imgBtnBackDetailKost.setOnClickListener {
            val action = when (fromFragment) {
                "home" -> KostDetailFragmentDirections.actionKostDetailToHome()
                else -> KostDetailFragmentDirections.actionKostDetailToFavorite()
            }
            Navigation.findNavController(view).navigate(action)

        }

        observeViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        kostViewModel.selectedKostLiveData.observe(viewLifecycleOwner) { kost ->
            imgKostDetailKost.loadImage(kost.mainPhoto)
            txtNamaDetailKost.text = kost.name
            txtLocationDetailKost.text = "${kost.distance} meter"
            txtPriceDetailKost.text = "$${kost.pricePerMonth}"
            txtOwnerDetailKost.text = kost.owner
            txtPropertyTypeDetailKost.text = "Kost ${kost.types}"
            txtBathroomTypeDetailKost.text = "Kamar Mandi ${kost.bathroomType}"
            ratingBarDetailKost.rating = kost.rating
            imgBtnFavoriteDetailKost.setImageResource(
                if (kost.isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            )
            txtBedroomDetailKost.text = "${kost.capacity}"
            txtBathroomDetailKost.text = "${kost.bathroom}"

            imgBtnFavoriteDetailKost.setOnClickListener {
                kost.isFavorite = !kost.isFavorite
                kostViewModel.favorite(kost.id) {}
                imgBtnFavoriteDetailKost.setImageResource(
                    if (kost.isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )
            }
        }
    }
}