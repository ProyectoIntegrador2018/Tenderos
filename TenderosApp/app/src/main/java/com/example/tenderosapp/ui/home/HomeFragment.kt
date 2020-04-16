package com.example.tenderosapp.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import android.widget.GridView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.tenderosapp.MainActivity
import com.example.tenderosapp.R
import com.example.tenderosapp.model.Transaction
import com.example.tenderosapp.ui.transaction.TransactionFragment
import com.example.tenderosapp.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.home_fragment.*
import java.lang.Exception


class HomeFragment : Fragment(R.layout.home_fragment) {

    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        toolbar_main_tb.inflateMenu(R.menu.toolbar_items)
        toolbar_main_tb.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        readqr_main_fab.setOnClickListener {
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setOrientationLocked(true)
            integrator.setPrompt(" Coloca el código de barras de tu recibo en el interior del rectángulo del visor para escanear.")
            integrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                49374 -> {
                    val scanResult =
                        IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
                    if (scanResult != null) {
                        Log.d("Holi2", scanResult.contents)
                        val gson = Gson()
                        try {
                            val convertedTransaction: Transaction =
                                gson.fromJson(scanResult.contents, Transaction::class.java)
                            Log.d("ErrorTransactionQR", convertedTransaction.transactionId)
                            if (convertedTransaction == null) {
                                throw Exception()
                            }
                            val bundle = bundleOf("transaction_data" to scanResult.contents)
                            (context as MainActivity).navController.navigate(R.id.action_mainFragment_to_display_transaction, bundle)
                        } catch (e: Exception) {
                            Toast.makeText(activity, "El código QR no es válido", Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show_id -> (context as MainActivity).navController.navigate(R.id.action_mainFragment_to_displayIdFragment)
            R.id.action_show_settings -> Toast.makeText(context, "Settings selected", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}

