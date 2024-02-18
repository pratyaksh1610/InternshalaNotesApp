package com.internshala.pratyakshkhurana.notes

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.internshala.pratyakshkhurana.notes.Fragments.LoginFragment
import com.internshala.pratyakshkhurana.notes.Fragments.OnBackPressedListener

class MainActivity : AppCompatActivity(), OnBackPressedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        }
    }

    override fun onBackPressed() {
    }
}
