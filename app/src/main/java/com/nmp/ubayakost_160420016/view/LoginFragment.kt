package com.nmp.ubayakost_160420016.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            it.onBackPressedDispatcher.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.moveTaskToBack(true)
                }
            })

            it.drawerButton.visibility = View.GONE
            it.bottomNav.visibility = View.GONE
            it.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        btnSignInLogin.setOnClickListener { btnView ->
            val username = editUsername.text.toString()
            val password = editPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password) {
                    if (it) {
                        val action = LoginFragmentDirections.actionLoginToHome()
                        Navigation.findNavController(view).navigate(action)
                    } else {
                        Toast.makeText(requireContext(), "Username or password incorrect.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Username or password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        btnCreateAccLogin.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginToRegister()
            Navigation.findNavController(it).navigate(action)
        }
    }
}