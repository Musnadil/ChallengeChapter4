package com.musnadil.challengechapter4.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.musnadil.challengechapter4.R
import com.musnadil.challengechapter4.StoreDatabase
import com.musnadil.challengechapter4.User
import com.musnadil.challengechapter4.databinding.FragmentRegisterBinding
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class RegisterFragment : Fragment() {
    private var mDb:StoreDatabase?=null
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
        mDb = StoreDatabase.getInstance(requireContext())

        binding.btnDaftar.setOnClickListener {
            when {
                binding.etUsername.text.isNullOrEmpty() -> {
                    binding.wrapUsername.error = "Username kamu belum diisi"
                }
                binding.etEmail.text.isNullOrEmpty() -> {
                    binding.wrapEmail.error = "Email kamu belum diisi"
                }
                binding.etPassword.text.isNullOrEmpty() -> {
                    binding.wrapPassword.error = "Password kamu belum diisi"
                }
                binding.etKonfirPassword.text.isNullOrEmpty() -> {
                    binding.wrapKonfirPassword.error = "Kamu perlu konfirmasi password"
                }
                binding.etPassword.text.toString().lowercase() != binding.etKonfirPassword.text.toString().lowercase() -> {
                    binding.wrapKonfirPassword.error = "Password tidak sama"
                    binding.etKonfirPassword.setText("")
                }
                else -> {
                    val objectUser = User(
                        null,
                        binding.etUsername.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    GlobalScope.async {
                        val result =mDb?.storeDao()?.addUser(objectUser)
                        runBlocking(Dispatchers.Main) {
                            if (result != 0.toLong()){
                                Toast.makeText(activity, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                                val username = binding.etUsername.text.toString()
                                val bundle = Bundle().apply {
                                    putString(USERNAME, username)
                                }
                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment, bundle)
                            }else{
                                Toast.makeText(activity, "Pendaftaran gagal", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
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