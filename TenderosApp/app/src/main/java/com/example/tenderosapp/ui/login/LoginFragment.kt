package com.example.tenderosapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.tenderosapp.R
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment :  Fragment(R.layout.fragment_login) {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        login_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_mainFragment))

    }


}
