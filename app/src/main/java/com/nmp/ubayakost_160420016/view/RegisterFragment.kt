package com.nmp.ubayakost_160420016.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = RegisterFragmentDirections.actionRegisterToLogin()
                Navigation.findNavController(view).navigate(action)
            }
        })

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        btnCreateAccRegister.setOnClickListener {
            val username = editUsernameRegister.text.toString()
            val password = editPasswordRegister.text.toString()
            val repeatPassword = editPasswordRepeatRegister.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
                if (username.length < 9) {
                    Toast.makeText(requireContext(), "Enter at least 9 characters for username", Toast.LENGTH_SHORT).show()
                } else if (password.length < 8 || repeatPassword.length < 8) {
                    Toast.makeText(requireContext(), "Enter at least 8 characters for password and repeat password", Toast.LENGTH_SHORT).show()
                } else {
                    if (password == repeatPassword) {
                        viewModel.register(username, password) { success, message ->
                            if (success) {
                                val action = RegisterFragmentDirections.actionRegisterToLogin()
                                Navigation.findNavController(view).navigate(action)
                            } else {
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Repeat password correctly", Toast.LENGTH_SHORT).show()
                    }
                }
            }  else {
                Toast.makeText(requireContext(), "Username or password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}