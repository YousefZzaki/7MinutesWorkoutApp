package com.example.a7minutesworkoutapp.room

import android.app.Application

class HistoryApp: Application() {
    val databaseApp by lazy{
        HistoryDatabase.getInstance(this)
    }
}