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
import com.example.tenderosapp.model.Provider


class ProviderAdapter (val context : Context, list: ArrayList<Provider>) : RecyclerView.Adapter<ProviderViewHolder>() {
    private var items: ArrayList<Provider> = list

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.provider_item, parent, false)
        return ProviderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        val provider: Provider = items[position]
        holder.provider_name_tv.text = provider.name
        Glide.with(context).load(ColorDrawable(Color.parseColor(provider.colorRGB))).circleCrop().into(holder.provider_color_iv)
        holder.itemView.setOnClickListener {
           Toast.makeText(context, "TODO: Implement provider flow.", Toast.LENGTH_SHORT).show()
        }

    }
}