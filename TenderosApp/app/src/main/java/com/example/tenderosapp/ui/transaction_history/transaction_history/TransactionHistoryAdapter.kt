package com.app.calendarioliturgico.view.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tenderosapp.R
import com.example.tenderosapp.model.Transaction
import java.util.*
import kotlin.collections.ArrayList


class TransactionHistoryAdapter (val context : Context, list: ArrayList<Transaction>) : RecyclerView.Adapter<TransactionHistoryViewHolder>() {
    private var items: ArrayList<Transaction> = list

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_history_item, parent, false)
        return TransactionHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
        val transaction: Transaction = items[position]
        val date = Date(transaction.date).toString()
        val formatted_date = date.substring(4,16)

        holder.date_tv.text = formatted_date
        holder.saleTotal_tv.text = "Se pagó un total de $" + transaction.saleTotal.toString()
        holder.businessName_tv.text = transaction.businessName
        holder.itemView.setOnClickListener {
           Toast.makeText(context, "TODO: Implement transaction flow.", Toast.LENGTH_SHORT).show()
        }

    }
}