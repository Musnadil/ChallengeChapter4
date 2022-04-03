package com.musnadil.challengechapter4.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter4.R
import com.musnadil.challengechapter4.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        val username = arguments?.getString(RegisterFragment.USERNAME)
        if (username.isNullOrEmpty()){
            binding.etUsername.hint = ""
        }else {
            binding.etUsername.setText(username)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}