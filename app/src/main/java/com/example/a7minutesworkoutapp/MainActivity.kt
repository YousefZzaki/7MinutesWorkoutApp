package com.example.a7minutesworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a7minutesworkoutapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

       // val startBtn: FrameLayout = findViewById(R.id.main_frame_layout)
        binding?.mainFrameLayout?.setOnClickListener{
            startActivity(Intent(this, ExerciseActivity::class.java))
        }

        binding?.flBMI?.setOnClickListener{
            startActivity(Intent(this, BMIActivity::class.java))
        }

        binding?.flHistory?.setOnClickListener{
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}