package com.app.calendarioliturgico.view.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tenderosapp.R
import com.example.tenderosapp.model.Promo
import com.example.tenderosapp.model.Provider
import java.util.*
import kotlin.collections.ArrayList


class CuponHistoryAdapter (val context : Context, list: ArrayList<Promo>) : RecyclerView.Adapter<CuponHistoryViewHolder>() {
    private var items: ArrayList<Promo> = list

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuponHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cupon_history_item, parent, false)
        return CuponHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuponHistoryViewHolder, position: Int) {
        val provider: Promo = items[position]
        val date = Date(provider.ExpiryDate)
//        holder.provider_name_tv.text = provider.couponDiscount
        holder.date_tv.text = date.toString()
        holder.percentage_tv.text = provider.couponDiscount
        holder.product_name_tv.text = provider.description
//        Glide.with(context).load(ColorDrawable(Color.parseColor(provider.colorRGB))).circleCrop().into(holder.provider_color_iv)
        holder.itemView.setOnClickListener {
           Toast.makeText(context, "TODO: Implement provider flow.", Toast.LENGTH_SHORT).show()
        }

    }
}