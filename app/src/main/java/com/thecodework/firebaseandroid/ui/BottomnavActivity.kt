package com.thecodework.firebaseandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.thecodework.firebaseandroid.R
import com.thecodework.firebaseandroid.databinding.ActivityBottomnavBinding
import com.thecodework.firebaseandroid.util.Utils

class BottomnavActivity : AppCompatActivity() {
    lateinit var binding: ActivityBottomnavBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomnavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initailizer()
        setlistener()
    }

    private fun setlistener() {
        binding.bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            var f: Fragment? = null
            when (item.itemId) {
                R.id.menu_student -> f = StudentFragment()
                R.id.menu_teacher -> f = TeacherFragment()

            }
            supportFragmentManager.beginTransaction().replace(R.id.main_Layout, f!!).commit()
            true
        })
    }

    private fun initailizer() {
        Utils.changeStatusBar(this, R.color.dark_color_shadow_light)
        supportFragmentManager.beginTransaction().replace(R.id.main_Layout, StudentFragment())
            .commit()
    }
}