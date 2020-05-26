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
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
import kotlinx.android.synthetic.main.fragment_cupon_history.*
import kotlinx.android.synthetic.main.home_fragment.provider_recycler_rv
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
            getFragmentNavController(R.id.nav_host_fragment)!!.navigate(R.id.fragment_login)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.queryGetPromorionList(auth.uid!!)
        //This is a Dummy List of Providers

        //setup lecturas
        val llLecturas = LinearLayoutManager(context)
        llLecturas.orientation = LinearLayoutManager.VERTICAL
        provider_recycler_rv.layoutManager = llLecturas

        viewModel.getPromotionList().observe(this, Observer {
            it?.let {
                Toast.makeText(context,"Desplegando información...", Toast.LENGTH_SHORT).show()
                provider_recycler_rv.adapter = CuponHistoryAdapter(context!!, it)
                (provider_recycler_rv.adapter as CuponHistoryAdapter).notifyDataSetChanged()
            }?:run{
                Toast.makeText(context,"Hubo un error obteniendo los datos... Inténtalo de nuevo", Toast.LENGTH_SHORT).show()
            }
        })


        promo_backbutton.setOnClickListener {
            getFragmentNavController(R.id.nav_host_fragment)!!.popBackStack()
        }
        //END --- This is a Dummy List of Providers
    }

    fun Fragment.getFragmentNavController(@IdRes id: Int) = activity?.let {
        return@let Navigation.findNavController(it,id)
    }
}

