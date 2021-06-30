package com.example.testmidtransapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOrder = findViewById<MaterialButton>(R.id.btn_open_order)
        btnOrder.setOnClickListener {
            val intent = Intent(this, MidtransActivity::class.java)
            startActivity(intent)
        }
    }
}