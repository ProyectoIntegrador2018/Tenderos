package com.example.tenderosapp.ui.transaction

import android.view.View
import com.example.tenderosapp.MainActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.tenderosapp.R
import com.example.tenderosapp.model.Transaction
import com.example.tenderosapp.model.TransactionProduct
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

        btn_test.setOnClickListener {
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setOrientationLocked(true);
            integrator.setPrompt(" Coloca el código de barras de tu recibo en el interior del rectángulo del visor para escanear.");
            //integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.initiateScan();

        }

        val products : MutableList<TransactionProduct> = ArrayList()

        products.add(TransactionProduct("gansito_123456789", "Gansitos", 6, 10, 500))
        products.add(TransactionProduct("pinguino_123455683", "Pinguinos", 30, 100, 200))


        val transaction =  Transaction("123","123456","Doña Chuchita","987654","Bimbo", System.currentTimeMillis(), 1500, 200, products)


        var gson = Gson()
        var jsonString = gson.toJson(transaction)
        Log.d("Json_product", jsonString)
        var json = "{\"actualBalance\":1000,\"businessId\":\"987654\",\"businessName\":\"Bimbo\",\"date\":1586309059286,\"products\":[{\"deliveredAmount\":10,\"productId\":\"gansito_123456789\",\"productName\":\"Gansitos\",\"returnedAmount\":0,\"total\":1000},{\"deliveredAmount\":5,\"productId\":\"Pinguino_123456789\",\"productName\":\"Pinguinos\",\"returnedAmount\":0,\"total\":500}],\"saleTotal\":1500,\"storeId\":\"123456\",\"storeName\":\"Doña Chuchita\",\"transactionId\":\"\"}"

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                49374 -> {
                    val scanResult =
                        IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
                    if (scanResult != null) {
                        Log.d("Holi2", scanResult.contents)
                        var gson = Gson()
                        try {
                            var convertedTransaction: Transaction =
                                gson.fromJson(scanResult.contents, Transaction::class.java)
                            Log.d("Holi2_prod_name", convertedTransaction.products[0].productName)
                        } catch (e: Exception) {
                            Log.d("Holi2_prod_name", "This is not a transaction")
                        }

                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitbutton_ib.setOnClickListener {
            (context as MainActivity).navController.popBackStack()
        }
    }

}
