package com.example.exammodul06.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exammodul06.R
import com.example.exammodul06.model.CardResp

class CardAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<CardResp> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CardViewHolder){
            holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CardViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(item: CardResp){
            val tv_card_number: TextView = view.findViewById(R.id.tv_card_number)
            val tv_holder_name: TextView = view.findViewById(R.id.tv_card_holder)
            val tv_month: TextView = view.findViewById(R.id.tv_month)
            val tv_year: TextView = view.findViewById(R.id.tv_year)

            tv_card_number.text = item.card_number
            tv_holder_name.text = item.holder_name
            tv_month.text = item.month
            tv_year.text = item.year
        }
    }

}