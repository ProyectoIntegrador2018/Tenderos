package com.example.tenderosapp.ui.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View

import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.tenderosapp.R
import com.example.tenderosapp.data.repository.AppRepository
import com.example.tenderosapp.data.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment :  Fragment(R.layout.fragment_login) {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        requireActivity().onBackPressedDispatcher.addCallback {}

        login_register.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_fragment_login_to_registerFragment)
        }

        login_button.setOnClickListener{
            doLogin()
        }
    }
    public override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    //Actualizar la navegación si el usuario es iudentificado correctamente
    fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null) {
            Navigation.findNavController(login_button).popBackStack()
            Toast.makeText(this.context, "Bienvenido.",Toast.LENGTH_LONG).show()
        }
    }


    private fun doLogin() {
        Toast.makeText(this.context, email_tf.text.toString(),Toast.LENGTH_LONG).show()
        if (email_tf.text.toString().isEmpty()) {
            email_layout.error = "Please enter email"
            email_tf.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email_tf.text.toString()).matches()) {
            email_layout.error = "Please enter valid email"
            email_tf.requestFocus()
            return
        }

        if (psswd_tf.text.toString().isEmpty()) {
            psswd_layout.error = "Please enter password"
            psswd_layout.requestFocus()
            return
        }

        viewModel.queryIsEmailRegistered(email_tf.text.toString())
        viewModel.getIsEmailRegistered().observe(this, Observer {
            it?.let {
                if (it){
                    auth.signInWithEmailAndPassword(email_tf.text.toString(), psswd_tf.text.toString())
                        .addOnCompleteListener() { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                updateUI(user)
                            } else {
                                updateUI(null)
                            }
                        }
                }else{
                    Toast.makeText(context,"Error. Usuario no aceptado. Contacta a soporte.", Toast.LENGTH_SHORT).show()
                }
            }?: kotlin.run {
                Toast.makeText(context,"Error. No tienes permisos. Contacta a soporte.", Toast.LENGTH_SHORT).show()
            }
        });

    }
}
