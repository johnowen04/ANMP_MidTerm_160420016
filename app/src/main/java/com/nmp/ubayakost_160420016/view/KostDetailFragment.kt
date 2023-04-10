package com.nmp.ubayakost_160420016.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.util.loadImage
import com.nmp.ubayakost_160420016.viewmodel.KostViewModel
import kotlinx.android.synthetic.main.fragment_kost_detail.*
import kotlinx.android.synthetic.main.item_kost.view.*

class KostDetailFragment : Fragment() {
    private lateinit var kostViewModel: KostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kost_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = KostDetailFragmentArgs.fromBundle(requireArguments()).id
        kostViewModel = ViewModelProvider(this)[KostViewModel::class.java]
        kostViewModel.fetchDetails(id) { }

        observeViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        kostViewModel.selectedKostLiveData.observe(viewLifecycleOwner) {
            imgKostDetailKost.loadImage(it.mainPhoto)
            txtNamaDetailKost.text = it.name
            txtLocationDetailKost.text = "${it.distance} meter"
            txtPriceDetailKost.text = "$${it.pricePerMonth}"
            txtOwnerDetailKost.text = it.owner
            txtPropertyTypeDetailKost.text = "Kost ${it.types}"
            txtBathroomTypeDetailKost.text = "Kamar Mandi ${it.bathroomType}"
            ratingBarDetailKost.rating = it.rating
            imgBtnFavoriteDetailKost.setImageResource(
                if (it.isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            )
            txtBedroomDetailKost.text = "${it.capacity}"
            txtBathroomDetailKost.text = "${it.bathroom}"
        }
    }
}