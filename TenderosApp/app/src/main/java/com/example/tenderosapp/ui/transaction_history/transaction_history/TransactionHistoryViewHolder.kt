package com.app.calendarioliturgico.view.calendar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tenderosapp.R
import com.google.android.material.card.MaterialCardView

class TransactionHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var date_tv : TextView
    var businessName_tv: TextView
    var saleTotal_tv : TextView
    val provider_background_cv : MaterialCardView

    init {
        saleTotal_tv = itemView.findViewById(R.id.sale_total)
        businessName_tv = itemView.findViewById(R.id.business_name)
        date_tv = itemView.findViewById(R.id.date)

        provider_background_cv = itemView.findViewById(R.id.provider_background_cv)
    }
}