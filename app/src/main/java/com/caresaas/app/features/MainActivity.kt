package com.caresaas.app.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.caresaas.app.R
import com.caresaas.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun changeMiddleNavItem() {
        // Get the middle item by its id
        val menuItem = binding.navView.menu.findItem(R.id.searchFragment)

        // Change the icon and title
        menuItem.icon = ContextCompat.getDrawable(this, R.drawable.event)
        menuItem.title = "Event"
    }

    fun revertMiddleNavItem() {
        // Get the middle item by its id
        val menuItem = binding.navView.menu.findItem(R.id.searchFragment)

        // Change the icon and title
        menuItem.icon = ContextCompat.getDrawable(this, R.drawable.search_icon)
        menuItem.title = "Search"
    }
}