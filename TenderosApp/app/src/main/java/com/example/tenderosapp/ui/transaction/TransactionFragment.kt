package com.example.tenderosapp.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tenderosapp.MainActivity

import com.example.tenderosapp.R
import kotlinx.android.synthetic.main.fragment_transaction.*

/**
 * A simple [Fragment] subclass.
 */
class TransactionFragment : Fragment(R.layout.fragment_transaction) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitbutton_ib.setOnClickListener {
            (context as MainActivity).navController.popBackStack()
        }
    }

}
