package com.shashank.xmlcyberboard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.shashank.xmlcyberboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWhiteCanvas()
    }


    fun setWhiteCanvas() {
        binding.clearBtn.setOnClickListener {
            binding.myCanvas.clearCanvas()
        }
        binding.eraseBtn.setOnClickListener {
            binding.myCanvas.setEraseMode(true)
        }
        binding.penBtn.setOnClickListener {
            binding.myCanvas.setEraseMode(false)
        }
    }
}