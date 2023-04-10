package com.nmp.ubayakost_160420016.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.view.adapter.KostAdapter
import com.nmp.ubayakost_160420016.viewmodel.KostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favorite_list.*

class FavoriteListFragment : Fragment() {
    private lateinit var kostViewModel: KostViewModel

    private lateinit var adapter: KostAdapter

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
        return inflater.inflate(R.layout.fragment_favorite_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        kostViewModel = ViewModelProvider(this)[KostViewModel::class.java]
        kostViewModel.fetchFavorite() { }

        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = FavoriteListFragmentDirections.actionFavoriteToHome()
                Navigation.findNavController(view).navigate(action)
            }
        })

        adapter = KostAdapter(arrayListOf(), "favorite") {
            kostViewModel.favorite(it) { }
        }

        rvFavorite.layoutManager = LinearLayoutManager(context)
        rvFavorite.adapter = adapter

        observeViewModel()
    }

    private fun observeViewModel() {
        kostViewModel.kostLiveData.observe(viewLifecycleOwner) {
            adapter.updateKostList(it)
        }
    }
}