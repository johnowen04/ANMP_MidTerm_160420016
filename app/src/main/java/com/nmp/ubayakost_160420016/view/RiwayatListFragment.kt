package com.nmp.ubayakost_160420016.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.view.adapter.PesananAdapter
import com.nmp.ubayakost_160420016.viewmodel.OrderViewModel
import com.nmp.ubayakost_160420016.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import kotlinx.android.synthetic.main.fragment_riwayat_list.*

class RiwayatListFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var orderViewModel: OrderViewModel

    private lateinit var adapter: PesananAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            it.drawerButton.visibility = View.VISIBLE
            it.bottomNav.visibility = View.VISIBLE
            it.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riwayat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        orderViewModel.fetch("riwayat") { }

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val user = userViewModel.getUserFromSharedPref()

        activity?.navView?.getHeaderView(0).let {
            it?.txtNameHeader?.text = if (user.firstName.isEmpty()) "No Name" else "${user.firstName} ${user.lastName}"
            it?.txtUsernameHeader?.text = user.username

            it?.btnLogOutHeader?.setOnClickListener {
                userViewModel.logout()
                val action = RiwayatListFragmentDirections.actionHistoryToLogin()
                Navigation.findNavController(view).navigate(action)
            }

            it?.imgAvatar?.setOnClickListener { v2 ->
                activity?.drawerLayout?.closeDrawer(GravityCompat.START)
                val action = RiwayatListFragmentDirections.actionHistoryToProfile("history")
                Navigation.findNavController(view).navigate(action)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = RiwayatListFragmentDirections.actionHistoryToHome()
                Navigation.findNavController(view).navigate(action)
            }
        })

        adapter = PesananAdapter(arrayListOf(), "riwayat")

        rvRiwayat.layoutManager = LinearLayoutManager(context)
        rvRiwayat.adapter = adapter

        observeViewModel()
    }

    private fun observeViewModel() {
        orderViewModel.orderLiveData.observe(viewLifecycleOwner) {
            adapter.updatePesananList(it)
        }
    }
}