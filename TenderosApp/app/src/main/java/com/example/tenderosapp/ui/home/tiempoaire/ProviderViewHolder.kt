package com.app.calendarioliturgico.view.calendar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tenderosapp.R
import com.google.android.material.card.MaterialCardView

class ProviderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var provider_name_tv: TextView
    var provider_color_iv : ImageView
    val provider_background_cv : MaterialCardView

    init {
        provider_name_tv = itemView.findViewById(R.id.provider_name_tv)
        provider_color_iv = itemView.findViewById(R.id.provider_color_iv)
        provider_background_cv = itemView.findViewById(R.id.provider_background_cv)
    }
}