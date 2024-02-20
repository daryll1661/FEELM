package com.example.spl.Activites

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.spl.R

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val buttondot = findViewById<Button>(R.id.buttondot)
        buttondot.setOnClickListener {
            startActivity(
                Intent(
                    this@splashActivity,
                    MainActivity::class.java
                )
            )
        }
    }

    companion object {
        private const val SPLASH_DELAY = 15000
    }
}
