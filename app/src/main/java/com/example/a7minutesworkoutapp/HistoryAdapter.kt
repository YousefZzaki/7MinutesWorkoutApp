package com.example.a7minutesworkoutapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkoutapp.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val list: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    class ViewHolder(binding: ItemHistoryRowBinding): RecyclerView.ViewHolder(binding.root){
        val llHistoryItemMain = binding.llItemHistoryRow
        val position = binding.position
        val date = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = list[position]
        holder.position.text = (position + 1).toString()
        holder.date.text = date

        if (position % 2 == 0){
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#EBEBEB"))
        }else
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }

    override fun getItemCount(): Int {
        return list.size
    }
}