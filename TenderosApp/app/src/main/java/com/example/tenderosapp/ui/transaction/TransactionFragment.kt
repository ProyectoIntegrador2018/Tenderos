package com.example.tenderosapp.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tenderosapp.R
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_login.*


/**
 * A simple [Fragment] subclass.
 */
class TransactionFragment : Fragment(R.layout.fragment_transaction) {

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_transaction, container, false)
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tittle_tv.text = "Hello"

    }

}
