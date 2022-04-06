package com.musnadil.challengechapter4.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.musnadil.challengechapter4.MainActivity
import com.musnadil.challengechapter4.R
import com.musnadil.challengechapter4.StoreDatabase
import com.musnadil.challengechapter4.databinding.FragmentLoginBinding
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class LoginFragment : Fragment() {
    private var mDb: StoreDatabase? = null
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val SPUSER = "user_login"
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = StoreDatabase.getInstance(requireContext())

        val preferences = this.activity?.getSharedPreferences(SPUSER, Context.MODE_PRIVATE)
        if (preferences!!.getString(USERNAME,null)!=null){

            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            val username = preferences.getString(USERNAME,null)
            Toast.makeText(context, "Selamat datang $username", Toast.LENGTH_SHORT).show()
        }

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

            when {
                binding.etUsername.text.isNullOrEmpty() -> {
                    binding.wrapUsername.error = "Kamu harus masukan username"
                }
                binding.etPassword.text.isNullOrEmpty() -> {
                    binding.wrapPassword.error = "Kamu harus masukan password"
                }
                else -> {
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
                                val editorSp : SharedPreferences.Editor = preferences!!.edit()
                                editorSp.putString(USERNAME,binding.etUsername.text.toString())
                                editorSp.putString(PASSWORD,binding.etPassword.text.toString())
                                editorSp.apply()
                                Toast.makeText(context, "Selamat datang ${binding.etUsername.text}", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun closeKeyboard() {
        val activity = activity as MainActivity

        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}