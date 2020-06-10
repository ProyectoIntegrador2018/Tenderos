package com.example.tenderosapp.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.app.calendarioliturgico.view.calendar.ProviderAdapter
import com.example.tenderosapp.MainActivity
import com.example.tenderosapp.R
import com.example.tenderosapp.model.Transaction
import com.example.tenderosapp.model.Promo
import com.example.tenderosapp.data.viewmodel.MainViewModel
import com.example.tenderosapp.model.Provider
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.readqr_main_fab
import kotlinx.android.synthetic.main.home_fragment.toolbar_main_tb
import com.example.tenderosapp.util.Encrypter.Companion.decyptData
import java.lang.Exception


class HomeFragment : Fragment(R.layout.home_fragment) {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel
    private var isTransaction = false
    public override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null){
            Navigation.findNavController(view).navigate(R.id.action_home_fragment_to_fragment_login)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        toolbar_main_tb.inflateMenu(R.menu.toolbar_items)
        toolbar_main_tb.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        readqr_main_fab.setOnClickListener {
            getFragmentNavController(R.id.nav_host_fragment)!!.navigate(R.id.action_mainFragment_to_displayIdFragment)
        }

        //This is a Dummy List of Providers
        val providrList = ArrayList<Provider>()
        providrList.add(Provider("Telcel","#002598"))
        providrList.add(Provider("Movistar","#78B415"))
        providrList.add(Provider("AT&T","#019FDB"))
        providrList.add(Provider("Unefon","#FCCB03"))
        providrList.add(Provider("Netxel","#E25E19"))
        providrList.add(Provider("Virgin Mobile","#E0173C"))

        provider_recycler_rv.layoutManager = GridLayoutManager(context,2)
        provider_recycler_rv.adapter = ProviderAdapter(context!!, providrList)
        //END --- This is a Dummy List of Providers
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                49374 -> {
                    val scanResult =
                        IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
                    var decryptedResult = decyptData(scanResult.contents)

                    if (decryptedResult != null) {
                        val gson = Gson()
                        if(isTransaction) {
                            try {
                                val convertedTransaction: Transaction =
                                    gson.fromJson(decryptedResult, Transaction::class.java)
                                Log.d("ErrorTransactionQR", convertedTransaction.transactionId)
                                if (convertedTransaction == null) {
                                    throw Exception()
                                }

                                val bundle = bundleOf("transaction_data" to decryptedResult)
                                (context as MainActivity).navController.navigate(
                                    R.id.action_mainFragment_to_display_transaction,
                                    bundle
                                )
                            } catch (e: Exception) {

                                Toast.makeText(activity, decryptedResult, Toast.LENGTH_LONG).show()
                            }
                        } else {
                            try {
                                val convertedPromo: Promo =
                                    gson.fromJson(decryptedResult, Promo::class.java)
                                Log.d("ErrorTransactionQR", convertedPromo.couponCode)
                                if (convertedPromo == null) {
                                    throw Exception()
                                }

                                val bundle = bundleOf("promo_data" to decryptedResult)
                                (context as MainActivity).navController.navigate(
                                    R.id.action_mainFragment_to_display_promo,
                                    bundle
                                )
                            } catch (e: Exception) {

                                Toast.makeText(activity, "Error Leyendo Promo..", Toast.LENGTH_LONG).show()
                            }
                        }

                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sale_id->{
                val integrator = IntentIntegrator.forSupportFragment(this)
                isTransaction = false
                integrator.setOrientationLocked(true)
                integrator.setPrompt(" Coloca el código QR de tu recibo en el interior del rectángulo del visor para escanear.")
                integrator.initiateScan()
               // Toast.makeText(context, "Accion para leer QR de Promoción...", Toast.LENGTH_SHORT).show()
            }
            R.id.action_read_qr_id -> {
                val integrator = IntentIntegrator.forSupportFragment(this)
                isTransaction = true
                integrator.setOrientationLocked(true)
                integrator.setPrompt(" Coloca el código QR de tu recibo en el interior del rectángulo del visor para escanear.")
                integrator.initiateScan()
            }
            R.id.action_show_logout ->{
                Toast.makeText(context, "Cerando Sesión", Toast.LENGTH_SHORT).show()
                auth.signOut()
                getFragmentNavController(R.id.nav_host_fragment)!!.navigate(R.id.action_home_fragment_to_fragment_login)
                //viewModel.queryGetBalance()
            }
            R.id.action_show_cupon_history -> (context as MainActivity).navController.navigate(R.id.action_home_fragment_to_fragment_cupon_history)
            R.id.action_show_transaction_history -> (context as MainActivity).navController.navigate(R.id.action_home_fragment_to_fragment_transaction_history)
        }
        return super.onOptionsItemSelected(item)
    }
    fun Fragment.getFragmentNavController(@IdRes id: Int) = activity?.let {
        return@let Navigation.findNavController(it,id)
    }

}

