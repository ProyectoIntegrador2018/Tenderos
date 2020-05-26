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
import com.example.tenderosapp.model.Promo
import com.example.tenderosapp.model.customAdapter
import com.google.android.play.core.internal.s
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_display_promo.*


class promoDisplayFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
    }

    //Quitar onCreateView  y ver como se hacer en  HOmeFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_display_promo , container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        auth = FirebaseAuth.getInstance()

        val promoData = arguments?.getString("promo_data")
        val gson = Gson()
        val promo: Promo = gson.fromJson(promoData, Promo::class.java)
        coupon_tv.text = promo.couponCode



        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
        val date = java.util.Date(promo.ExpiryDate)

        store_tv.text = promo.businessName
        description_tv.text = promo.description
        date_tv.text = "Valido Hasta: " + sdf.format(date)

        continue_button.setOnClickListener {
            continue_button.isEnabled = false
            viewModel.queryRegisterPromo(promo,auth.uid!!)
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

        }
    }
    fun Fragment.getFragmentNavController(@IdRes id: Int) = activity?.let {
        return@let Navigation.findNavController(it,id)
    }
}
