package com.example.materialapp.ui.view.activity

import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.materialapp.R
import com.example.materialapp.ui.view.fragment.MainFragment
import com.example.materialapp.ui.view.fragment.MarsPagerFragment
import com.example.materialapp.ui.view.fragment.NotesFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

const val KEY_THEME = "KEY_THEME"

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var savedTheme: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        savedInstanceState?.getInt(KEY_THEME, R.style.Theme_EARTH)
            ?.let {
                savedTheme = it
                setTheme(it)
                setStatusBarColor()
            }
        super.onCreate(savedInstanceState)
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.space -> replaceScreen(MainFragment())
                R.id.mars -> replaceScreen(MarsPagerFragment())
                R.id.notes -> replaceScreen(NotesFragment())
            }
            true
        }

        findViewById<MaterialToolbar>(R.id.toolbar).setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.earth -> {
                    savedTheme = R.style.Theme_EARTH
                }
                R.id.mars -> {
                    savedTheme = R.style.Theme_MARS
                }
                R.id.moon -> {
                    savedTheme = R.style.Theme_MOON
                }
            }
            recreate()
            true
        }

        bottomNavView.selectedItemId = R.id.space
    }

    private fun setStatusBarColor() {
        val typedValue = TypedValue()
        this.theme?.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        window.statusBarColor = typedValue.data
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedTheme?.let {
            outState.putInt(KEY_THEME, it)
        }
    }

    private fun replaceScreen(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}