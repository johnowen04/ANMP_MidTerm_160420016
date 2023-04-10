package com.nmp.ubayakost_160420016.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.view.adapter.PesananAdapter
import com.nmp.ubayakost_160420016.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_pesanan_list.*

class PesananListFragment : Fragment() {
    private lateinit var orderViewModel: OrderViewModel

    private lateinit var adapter: PesananAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesanan_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        orderViewModel.fetch("pesanan") { }

        adapter = PesananAdapter(arrayListOf())

        rvPesanan.layoutManager = LinearLayoutManager(context)
        rvPesanan.adapter = adapter

        observeViewModel()
    }

    private fun observeViewModel() {
        orderViewModel.orderLiveData.observe(viewLifecycleOwner) {
            adapter.updatePesananList(it)
        }
    }
}