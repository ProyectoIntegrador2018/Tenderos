package com.example.tenderosapp.ui.transaction

import android.view.View
import com.example.tenderosapp.MainActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tenderosapp.R
import com.example.tenderosapp.model.Transaction
import com.example.tenderosapp.model.TransactionProduct
import com.example.tenderosapp.ui.home.HomeFragment
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_transaction.*
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 */
class TransactionFragment : Fragment(R.layout.fragment_transaction) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transactionData = arguments?.getString("transaction_data")

        exitbutton_ib.setOnClickListener {
            (context as MainActivity).navController.popBackStack(R.id.home_fragment, false)
        }
    }
}
