package com.example.tenderosapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionBarContextView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.tenderosapp.data.viewmodel.MainViewModel
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_login.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : MainViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navController = findNavController(R.id.nav_host_fragment)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            val value = result.contents
            if (value != null) {
                Log.d("Holi", value)
            } else {
                Log.d("holi", "failed")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
