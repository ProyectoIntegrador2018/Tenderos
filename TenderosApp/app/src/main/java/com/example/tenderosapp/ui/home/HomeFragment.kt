package com.example.tenderosapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.tenderosapp.MainActivity
import com.example.tenderosapp.R
import com.example.tenderosapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment(R.layout.home_fragment) {

    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        toolbar_main_tb.inflateMenu(R.menu.toolbar_items)
        toolbar_main_tb.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
        readqr_main_fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_display_transaction))

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show_id -> (context as MainActivity).navController.navigate(R.id.action_mainFragment_to_displayIdFragment)
            R.id.action_show_settings -> Toast.makeText(context, "Settings selected", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}

