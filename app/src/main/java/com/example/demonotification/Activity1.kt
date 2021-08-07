package com.example.demonotification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_activity1.*

class Activity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity1)
        buttonStartActivity2.setOnClickListener {
            startActivity(Intent(this,Activity2::class.java))
        }
    }
}