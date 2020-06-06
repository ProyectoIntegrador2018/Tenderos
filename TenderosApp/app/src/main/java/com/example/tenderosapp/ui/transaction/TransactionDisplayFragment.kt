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
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tenderosapp.R
import com.example.tenderosapp.data.viewmodel.MainViewModel
import com.example.tenderosapp.model.Transaction
import com.example.tenderosapp.model.TransactionProduct
import com.example.tenderosapp.model.customAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_display_promo.*
import kotlinx.android.synthetic.main.fragment_display_transaction.*
import kotlinx.android.synthetic.main.fragment_display_transaction.continue_button
import kotlinx.android.synthetic.main.fragment_display_transaction.store_tv
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 */
class TransactionDisplayFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
    }

    //Quitar onCreateView  y ver como se hacer en  HOmeFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_display_transaction , container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        val transactionData = arguments?.getString("transaction_data")
        val gson = Gson()
        val transaction: Transaction = gson.fromJson(transactionData, Transaction::class.java)

        business_tv.text = transaction.businessName
        store_tv.text = transaction.storeName
        saldo_anterior_qty.text = transaction.previousBalance.toString()
        total_qty.text = transaction.saleTotal.toString()
        pago_qty.text = transaction.payment.toString()
        saldo_nuevo_qty.text = transaction.actualBalance.toString()

        recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)

            adapter = customAdapter(transaction.products)
        }

        continue_button.setOnClickListener {
            val bundle = bundleOf("transaction_data" to transactionData)
            viewModel.queryRegisterTransaction(transaction,auth.uid!!)
            viewModel.getRegisterPromoSucccess().observe(this, Observer {
                it?.let {
                    if(it){
                        Toast.makeText(activity, "La promoción se registró de manera exitosa!", Toast.LENGTH_LONG).show()
                        getFragmentNavController(R.id.nav_host_fragment)!!.navigate(R.id.action_fragmentPromoDisplay_to_HomeFragment)
                    }else {
                        Toast.makeText(activity, "Hubo un error aplicando la Promoción...", Toast.LENGTH_LONG).show()
                        continue_button.isEnabled = true
                    }

                }?: kotlin.run {
                    continue_button.isEnabled = true
                    Toast.makeText(activity, "Hubo un error aplicando la Promoción...", Toast.LENGTH_LONG).show()
                }
            })
            (context as MainActivity).navController.navigate(R.id.action_fragmentTransactionDisplay_to_transactionFragment, bundle)
            //Navigation.createNavigateOnClickListener(R.id.action_fragmentTransactionDisplay_to_transactionFragment)
        }
    }
    fun Fragment.getFragmentNavController(@IdRes id: Int) = activity?.let {
        return@let Navigation.findNavController(it,id)
    }
}
