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
import com.nmp.ubayakost_160420016.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel

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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        var fromFragment: String
        arguments.let {
            fromFragment = ProfileFragmentArgs.fromBundle(requireArguments()).fromFragment
        }

        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = when (fromFragment) {
                    "home" -> ProfileFragmentDirections.actionProfileToHome()
                    "order" -> ProfileFragmentDirections.actionProfileToPesanan()
                    else -> ProfileFragmentDirections.actionProfileToRiwayat()
                }
                Navigation.findNavController(view).navigate(action)
            }
        })

        val user = userViewModel.getUserFromSharedPref()

        txtNameProfile.text = if (user.firstName.isEmpty()) "No Name" else "${user.firstName} ${user.lastName}"
        txtActiveSinceProfile.text = "Active since ${user.createdAt}"
        txtUsernameProfile.text = user.username
        editFirstNameProfile.setText(txtNameProfile.text.split(" ")[0])
        editLastNameProfile.setText(txtNameProfile.text.split(" ").drop(1).joinToString(" "))

        observeViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            txtNameProfile.text = if (user.firstName.isEmpty()) "No Name" else "${user.firstName} ${user.lastName}"
            txtActiveSinceProfile.text = "Active since ${user.createdAt}"
            txtUsernameProfile.text = user.username
            editFirstNameProfile.setText(txtNameProfile.text.split(" ")[0])
            editLastNameProfile.setText(txtNameProfile.text.split(" ").drop(1).joinToString(" "))
        }
    }
}