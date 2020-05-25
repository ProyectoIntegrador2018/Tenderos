package com.example.tenderosapp.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.app.calendarioliturgico.view.calendar.CuponHistoryAdapter
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


class CuponHistoryFragment : Fragment(R.layout.fragment_cupon_history) {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel
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

        //This is a Dummy List of Providers
        val promotionList = ArrayList<Promo>()
        promotionList.add(Promo("123", "20%", "Virtual", "Gansitos deliciosos","123456","Doña Chuchita", 1590382514))


        provider_recycler_rv.layoutManager = GridLayoutManager(context,2)
        provider_recycler_rv.adapter = CuponHistoryAdapter(context!!, promotionList)
        //END --- This is a Dummy List of Providers
    }
}

