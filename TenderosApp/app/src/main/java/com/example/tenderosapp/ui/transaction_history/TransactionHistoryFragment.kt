package com.example.tenderosapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.calendarioliturgico.view.calendar.TransactionHistoryAdapter
import com.example.tenderosapp.R
import com.example.tenderosapp.data.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_transaction_history.*
import kotlinx.android.synthetic.main.home_fragment.provider_recycler_rv


class TransactionHistoryFragment : Fragment(R.layout.fragment_transaction_history) {
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
        viewModel.queryGetTransactionList(auth.uid!!)

        //This is a Dummy List of Providers

        //setup lecturas
        val llLecturas = LinearLayoutManager(context)
        llLecturas.orientation = LinearLayoutManager.VERTICAL
        provider_recycler_rv.layoutManager = llLecturas

        viewModel.getTransactionList().observe(this, Observer {
            it?.let {
                Toast.makeText(context,"Desplegando información...", Toast.LENGTH_SHORT).show()
                provider_recycler_rv.adapter = TransactionHistoryAdapter(context!!, it)
                (provider_recycler_rv.adapter as TransactionHistoryAdapter).notifyDataSetChanged()
            }?:run{
                Toast.makeText(context,"Hubo un error obteniendo los datos... Inténtalo de nuevo", Toast.LENGTH_SHORT).show()
            }
        })


        transaction_backbutton.setOnClickListener {
            getFragmentNavController(R.id.nav_host_fragment)!!.popBackStack()
        }
        //END --- This is a Dummy List of Providers
    }

    fun Fragment.getFragmentNavController(@IdRes id: Int) = activity?.let {
        return@let Navigation.findNavController(it,id)
    }
}

