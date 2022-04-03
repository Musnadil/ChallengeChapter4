package com.musnadil.challengechapter4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.musnadil.challengechapter4.databinding.ActivityMainBinding
import com.musnadil.challengechapter4.fragment.AddListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this,R.color.white)
        WindowInsetsControllerCompat(window,window.decorView)
            .isAppearanceLightStatusBars = true
        binding.fabNewItem.setOnClickListener{
            val dialogFragment = AddListFragment()
            dialogFragment.show(supportFragmentManager,null)
        }
    }
}