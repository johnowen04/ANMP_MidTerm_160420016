package com.nmp.ubayakost_160420016.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.view.adapter.KostAdapter
import com.nmp.ubayakost_160420016.view.adapter.RiwayatAdapter
import com.nmp.ubayakost_160420016.viewmodel.KostViewModel
import com.nmp.ubayakost_160420016.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_kost_list.*

class RiwayatListFragment : Fragment() {
    private lateinit var orderViewModel: OrderViewModel

    private lateinit var adapter: RiwayatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riwayat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        orderViewModel.fetch("riwayat") { }

        adapter = RiwayatAdapter(arrayListOf())

        rvKostHome.layoutManager = LinearLayoutManager(context)
        rvKostHome.adapter = adapter

        observeViewModel()
    }

    private fun observeViewModel() {
        orderViewModel.orderLiveData.observe(viewLifecycleOwner) {
            adapter.updateRiwayatList(it)
        }
    }
}