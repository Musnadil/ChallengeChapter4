package com.musnadil.challengechapter4.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.musnadil.challengechapter4.LoginActivity
import com.musnadil.challengechapter4.MainActivity
import com.musnadil.challengechapter4.R
import com.musnadil.challengechapter4.StoreDatabase
import com.musnadil.challengechapter4.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment() {
    var mDb: StoreDatabase? = null
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
        mDb = StoreDatabase.getInstance(requireContext())

        binding.btnDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        val username = arguments?.getString(RegisterFragment.USERNAME)
        if (username.isNullOrEmpty()) {
            binding.etUsername.hint = ""
        } else {
            binding.etUsername.setText(username)
        }

        binding.btnMasuk.setOnClickListener {

            if (binding.etUsername.text.isNullOrEmpty()) {
                binding.wrapUsername.error = "Kamu harus masukan username"
            } else if (binding.etPassword.text.isNullOrEmpty()) {
                binding.wrapPassword.error = "Kamu harus masukan password"
            } else {
                GlobalScope.async {
                    val result = mDb?.storeDao()?.userCheck(
                        binding.etUsername.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    runBlocking(Dispatchers.Main) {
                        if (result == false) {
                            closeKeyboard()
                            val snackbar = Snackbar.make(it,"Gagal masuk mungkin anda salah memasukan email atau password",Snackbar.LENGTH_INDEFINITE)
                            snackbar.setAction("Oke") {
                                snackbar.dismiss()
                                binding.etUsername.requestFocus()
                                binding.etUsername.setText("")
                                binding.etPassword.setText("")
                            }
                            snackbar.show()
                        } else {
                            Toast.makeText(activity, "Selamat datang ${binding.etUsername.text.toString()}", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(activity,MainActivity::class.java))
                            onDestroy()
                            activity?.finish()
                        }
                    }
                }
            }
        }
    }

    fun closeKeyboard() {
        val activity = activity as LoginActivity

        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}