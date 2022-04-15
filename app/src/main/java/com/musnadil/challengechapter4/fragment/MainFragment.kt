package com.musnadil.challengechapter4.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.musnadil.challengechapter4.*
import com.musnadil.challengechapter4.adapter.ItemAdapter
import com.musnadil.challengechapter4.databinding.FragmentMainBinding
import kotlinx.coroutines.*


@DelicateCoroutinesApi
class MainFragment : Fragment() {

    private var mDb: StoreDatabase? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemAdapter
    private lateinit var preferences: SharedPreferences
    lateinit var itemRepository: ItemRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireContext().getSharedPreferences(LoginFragment.SPUSER, Context.MODE_PRIVATE)
        binding.tvWelcome.text = "Welcome ${preferences.getString(LoginFragment.USERNAME, null)}"

        mDb = StoreDatabase.getInstance(requireContext())
        itemRepository = ItemRepository(requireContext())

        adapter = ItemAdapter()
        binding.rvList.adapter = adapter

        binding.rvList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fetchData()
        logout()
        binding.fabNewItem.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addListFragment)
        }

        binding.swipeLayout.setOnRefreshListener {
            fetchData()
            binding.swipeLayout.isRefreshing = false
        }
    }

    fun fetchData() {
        GlobalScope.launch {
                val listItem = itemRepository?.getAllItem()
            runBlocking(Dispatchers.Main) {
                if (listItem.isNullOrEmpty()) {
                    binding.handlerLottie.visibility = View.VISIBLE
                    binding.tvHandler.visibility = View.VISIBLE
                }else{
                    binding.handlerLottie.visibility = View.GONE
                    binding.tvHandler.visibility = View.GONE
                }
                listItem?.let {
                    adapter.setData(it)
                }

            }
        }
    }

    fun logout() {
        binding.tvLogout.setOnClickListener {
            val dialogKonfirmasi = AlertDialog.Builder(requireContext())
            dialogKonfirmasi.apply {
                setTitle("Logout")
                setMessage("Apakah anda yakin ingin log out?")
                setNegativeButton("Batal") { dialog, which ->
                    dialog.dismiss()
                }
                setPositiveButton("Logout") { dialog, which ->
                    dialog.dismiss()

                    preferences.edit().clear().apply()
                    findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
                }
            }
            dialogKonfirmasi.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

}