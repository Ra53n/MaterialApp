package com.example.materialapp.ui.view.activity

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.materialapp.R
import com.example.materialapp.ui.view.fragment.KEY_THEME
import com.example.materialapp.ui.view.fragment.MainFragment
import com.example.materialapp.ui.view.fragment.MarsFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var savedTheme: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
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
                R.id.mars -> replaceScreen(MarsFragment())
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

    private fun showMenu() {
        val popupMenu = PopupMenu(this, findViewById(R.id.container), Gravity.TOP)
        popupMenu.inflate(R.menu.theme_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
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
        popupMenu.show()
    }

    private fun replaceScreen(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}