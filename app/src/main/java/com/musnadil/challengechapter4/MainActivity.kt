package com.musnadil.challengechapter4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.musnadil.challengechapter4.adapter.ItemAdapter
import com.musnadil.challengechapter4.databinding.ActivityMainBinding
import com.musnadil.challengechapter4.fragment.AddListFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mDb: StoreDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowInsetsControllerCompat(window, window.decorView)
            .isAppearanceLightStatusBars = true


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
            runBlocking {
                listItem?.let {
                    val adapter = ItemAdapter(it as ArrayList<Item>)
                    binding.rvList.adapter = adapter
                }
            }
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