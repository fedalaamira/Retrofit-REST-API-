package com.example.exo4_tdm2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val first = findViewById<TextView>(R.id.first)
        val third= findViewById<TextView>(R.id.third)
        first.text=intent.getStringExtra("titre")
        third.text=intent.getStringExtra("desc")
    }
}
