package com.musnadil.challengechapter4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.musnadil.challengechapter4.adapter.ItemAdapter
import com.musnadil.challengechapter4.databinding.ActivityMainBinding
import com.musnadil.challengechapter4.fragment.AddListFragment
import com.musnadil.challengechapter4.fragment.LoginFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mDb: StoreDatabase? = null
    private lateinit var adapter: ItemAdapter
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logout()
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = true

        adapter = ItemAdapter()
        binding.rvList.adapter = adapter

        preferences = this.getSharedPreferences(LoginFragment.SPUSER, Context.MODE_PRIVATE)
        binding.tvWelcome.text = "Welcome ${preferences.getString(LoginFragment.USERNAME,null)}"

        mDb = StoreDatabase.getInstance(this)
        binding.rvList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        fetchData()

        binding.fabNewItem.setOnClickListener {
            val dialogFragment = AddListFragment()
            dialogFragment.show(supportFragmentManager, null)
        }
    }
    fun fetchData(){
        GlobalScope.launch {
            val listItem = mDb?.itemDao()?.getAllItem()

            runBlocking(Dispatchers.Main) {
                listItem?.let {
//                    val adapter = ItemAdapter(it as ArrayList<Item>)
//                    binding.rvList.adapter = adapter
                    adapter.setData(it)
                }
            }
        }
    }

    fun logout(){
        binding.tvLogout.setOnClickListener {
            val dialog_konfirmasi = AlertDialog.Builder(this)
            dialog_konfirmasi.apply{
                setTitle("Logout")
                setMessage("Apakah anda yakin ingin log out?")
                setNegativeButton("Batal"){dialog,which->
                    dialog.dismiss()
                }
                setPositiveButton("Logout"){dialog,which->
                    dialog.dismiss()
                    preferences.edit().clear().commit()
                    startActivity( Intent(this@MainActivity,LoginActivity::class.java))
                    finish()
                }
            }
            dialog_konfirmasi.show()
        }
    }
    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        StoreDatabase.destroyInstance()
    }
}