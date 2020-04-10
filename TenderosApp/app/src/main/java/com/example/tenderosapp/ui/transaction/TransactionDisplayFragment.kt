package com.example.tenderosapp.ui.transaction

import android.view.View
import com.example.tenderosapp.MainActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tenderosapp.R
import com.example.tenderosapp.model.Transaction
import com.example.tenderosapp.model.TransactionProduct
import com.example.tenderosapp.model.customAdapter
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_display_transaction.*
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 */
class TransactionDisplayFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_display_transaction , container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = customAdapter(getProductos())
        }

        continue_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_fragmentTransactionDisplay_to_transactionFragment))
    }


    fun getProductos(): MutableList<TransactionProduct>  {
        var products : MutableList<TransactionProduct> = ArrayList()
        products.add(TransactionProduct("gansito_123456789", "Gansitos", 6, 10, 500))
        products.add(TransactionProduct("pinguino_123455683", "Pinguinos", 30, 100, 200))
        products.add(TransactionProduct("gansito_123456789", "Gansitos", 6, 10, 500))
        products.add(TransactionProduct("pinguino_123455683", "Pinguinos", 30, 100, 200))
        products.add(TransactionProduct("gansito_123456789", "Gansitos", 6, 10, 500))
        products.add(TransactionProduct("pinguino_123455683", "Pinguinos", 30, 100, 200))
        products.add(TransactionProduct("gansito_123456789", "Gansitos", 6, 10, 500))
        products.add(TransactionProduct("pinguino_123455683", "Pinguinos", 30, 100, 200))
        products.add(TransactionProduct("gansito_123456789", "Gansitos", 6, 10, 500))
        products.add(TransactionProduct("pinguino_123455683", "Pinguinos", 30, 100, 200))
        products.add(TransactionProduct("gansito_123456789", "Gansitos", 6, 10, 500))
        products.add(TransactionProduct("pinguino_123455683", "Pinguinos", 30, 100, 200))
        products.add(TransactionProduct("gansito_123456789", "Gansitos", 6, 10, 500))
        products.add(TransactionProduct("pinguino_123455683", "Pinguinos", 30, 100, 200))

        var transaction =  Transaction("123","123456","Doña Chuchita","987654","Bimbo", System.currentTimeMillis(), 1500, 200, products)
        return products
    }

}
