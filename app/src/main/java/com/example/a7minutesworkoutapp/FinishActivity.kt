package com.example.a7minutesworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkoutapp.databinding.ActivityFinishBinding
import com.example.a7minutesworkoutapp.room.HistoryApp
import com.example.a7minutesworkoutapp.room.HistoryDao
import com.example.a7minutesworkoutapp.room.HistoryEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class FinishActivity : AppCompatActivity() {

    var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnFinish?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
//            onBackPressed()
        }

        setSupportActionBar(binding?.finishToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.finishToolbar?.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val dao = (application as HistoryApp).databaseApp.historyDao()
        addDateToDatabase(dao)

    }

    private fun addDateToDatabase(historyDao: HistoryDao){

        val calendar = Calendar.getInstance()
        val dateTime = calendar.time

        Log.e("Date: ",""+ dateTime)

        val sdf = SimpleDateFormat("dd MMM yyy HH:mm", Locale.getDefault())
     //   Log.e("sdf: ", "" + sdf)
        val date = sdf.format(dateTime)

        Log.e("Formatted date: ", "" + date)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e("Date ", "Added..")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}