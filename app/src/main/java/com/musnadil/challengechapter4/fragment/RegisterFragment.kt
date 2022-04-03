package com.musnadil.challengechapter4.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter4.R
import com.musnadil.challengechapter4.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDaftar.setOnClickListener {
            if (binding.etUsername.text.isNullOrEmpty()) {
                binding.wrapUsername.error = "Username kamu belum diisi"
            } else if (binding.etEmail.text.isNullOrEmpty()) {
                binding.wrapEmail.error = "Email kamu belum diisi"
            } else if (binding.etPassword.text.isNullOrEmpty()) {
                binding.wrapPassword.error = "Password kamu belum diisi"
            } else if (binding.etKonfirPassword.text.isNullOrEmpty()) {
                binding.wrapKonfirPassword.error = "Kamu perlu konfirmasi password"
            } else if (binding.etPassword.text.toString().lowercase() != binding.etKonfirPassword.text.toString().lowercase()){
                binding.wrapKonfirPassword.error = "Password tidak sama"
                binding.etKonfirPassword.setText("")
            } else {
                val username = binding.etUsername.text.toString()
                val bundle = Bundle().apply {
                    putString(USERNAME, username)
                }
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment, bundle)
            }
        }
        binding.btnMasuk.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}