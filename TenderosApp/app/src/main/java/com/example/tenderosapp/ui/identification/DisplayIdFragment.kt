package com.example.tenderosapp.ui.identification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.tenderosapp.MainActivity
import com.example.tenderosapp.R
import kotlinx.android.synthetic.main.fragment_display_store_id.*

class DisplayIdFragment : Fragment(R.layout.fragment_display_store_id) {

    fun onActivityCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitbutton_ib.setOnClickListener {
            (context as MainActivity).navController.popBackStack()
        }
    }

}
