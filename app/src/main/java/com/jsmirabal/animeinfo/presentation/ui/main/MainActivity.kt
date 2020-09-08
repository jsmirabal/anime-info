package com.jsmirabal.animeinfo.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jsmirabal.animeinfo.R
import com.jsmirabal.animeinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.bottomAppBar)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(this, "Fav menu item is clicked!", Toast.LENGTH_SHORT)
                .show()
            R.id.app_bar_search -> Toast.makeText(this, "Search menu item is clicked!", Toast.LENGTH_SHORT)
                .show()
        }
        return true
    }
}
