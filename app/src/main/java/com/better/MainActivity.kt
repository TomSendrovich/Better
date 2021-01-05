package com.better

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.better.model.Repository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Repository.demo()
    }
}
