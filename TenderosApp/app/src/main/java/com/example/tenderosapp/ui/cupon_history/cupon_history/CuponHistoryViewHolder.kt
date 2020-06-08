package com.app.calendarioliturgico.view.calendar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tenderosapp.R
import com.google.android.material.card.MaterialCardView

class CuponHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var client_tv: TextView
    var product_name_tv : TextView
    var date_tv : TextView
    val provider_background_cv : MaterialCardView

    init {
        client_tv = itemView.findViewById(R.id.client)
        product_name_tv = itemView.findViewById(R.id.product_name)
        date_tv = itemView.findViewById(R.id.date)

        provider_background_cv = itemView.findViewById(R.id.provider_background_cv)
    }
}