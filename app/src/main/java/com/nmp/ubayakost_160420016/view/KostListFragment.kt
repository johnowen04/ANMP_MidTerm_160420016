package com.nmp.ubayakost_160420016.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.view.adapter.KostAdapter
import com.nmp.ubayakost_160420016.viewmodel.KostViewModel
import com.nmp.ubayakost_160420016.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_kost_list.*

class KostListFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var kostViewModel: KostViewModel

    private lateinit var adapter: KostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kost_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        if (!userViewModel.isLogin()) {
            val action = KostListFragmentDirections.actionHomeToLogin()
            Navigation.findNavController(view).navigate(action)
        }

        kostViewModel = ViewModelProvider(this)[KostViewModel::class.java]
        kostViewModel.fetch() { }

        adapter = KostAdapter(arrayListOf())

        rvKostHome.layoutManager = LinearLayoutManager(context)
        rvKostHome.adapter = adapter

        swipeRefreshHome.setOnRefreshListener {
            kostViewModel.fetch() { swipeRefreshHome.isRefreshing = !it }
            observeViewModel()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        kostViewModel.kostLiveData.observe(viewLifecycleOwner) {
            adapter.updateKostList(it)
        }
    }
}