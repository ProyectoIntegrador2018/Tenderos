package com.example.tenderosapp.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tenderosapp.R
import kotlinx.android.synthetic.main.product_list.*
import kotlinx.android.synthetic.main.product_list.view.*

class customAdapter (val productList: MutableList<TransactionProduct>) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int = productList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater, parent)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val product : TransactionProduct = productList[position]
        holder.bind(product)

    }

}

class CustomViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.product_list, parent, false)) {
    private var textViewDelivered: TextView? = null
    private var textViewReturned: TextView? = null
    private var textViewProductName: TextView? = null

    init {
        textViewDelivered = itemView.findViewById(R.id.delivered_tv)
        textViewReturned = itemView.findViewById(R.id.returned_tv)
        textViewProductName = itemView.findViewById(R.id.product_name)
    }

    fun bind(product: TransactionProduct){
        textViewDelivered?.text = product.deliveredAmount.toString()
        textViewReturned?.text = product.returnedAmount.toString()
        textViewProductName?.text = product.productName
    }

}