package com.example.tenderosapp.ui.login

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.example.tenderosapp.R
import com.example.tenderosapp.data.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        register_register_btn.setOnClickListener {view ->
           if( verifyAllfields()){
               viewModel.getIsEmailRegistered().observe(this, Observer {
                   it?.let {
                       if (it){
                           doSignUp(view)
                       }else{
                           Toast.makeText(context,"Error. Usuario no aceptado. Contacta a soporte.", Toast.LENGTH_SHORT).show()
                       }
                   }?: kotlin.run {
                       Toast.makeText(context,"Error. No tienes permisos. Contacta a soporte.", Toast.LENGTH_SHORT).show()
                   }
               });
           }

        }

        register_home_btn.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

    }

    //Crear Cuenta
    private fun doSignUp(view : View) {

        auth.createUserWithEmailAndPassword(register_email_tf.text.toString(), register_psswd_tf.text.toString())
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Exito, hacer login al nuevo usuario
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    Toast.makeText(this.context, "Usuario Creado",
                        Toast.LENGTH_LONG).show()
                    val user = auth.currentUser
                    if(user != null){
                        Navigation.findNavController(view).popBackStack()
                    } else {
                        Toast.makeText(this.context, "Error al Crear usuario", Toast.LENGTH_LONG).show()
                    }

                } else {
                    // Error al Crear usuario
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this.context, "Error al crear usuario.",
                        Toast.LENGTH_LONG).show()
                    Navigation.findNavController(view).popBackStack()
                }
            }

    }

    fun verifyAllfields() : Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(register_email_tf.text.toString()).matches()) {
            register_email_layout.error = "Ingresa un email válido"
            register_email_tf.requestFocus()
            return false
        }

        if (register_email_tf.text.toString().isEmpty()) {
            register_email_layout.error = "Ingresa un email"
            register_email_tf.requestFocus()
            return false
        }


        if (register_psswd_tf.text.toString().isEmpty()) {
            register_psswd_layout.error = "Ingresa una contraseña"
            register_psswd_layout.requestFocus()
            return false
        }

        if (register_psswd_tf.text.toString() != register_confPsswd_tf.text.toString()) {
            register_confPsswd_tf.error = "Verifica que las contraseñas coincidan"
            register_confPsswd_tf.requestFocus()
            return false
        }

        viewModel.queryIsEmailRegistered(register_email_tf.text.toString())
        return true
    }
}
