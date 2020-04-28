package com.example.tenderosapp.ui.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns

import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation

import com.example.tenderosapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment :  Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        login_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_mainFragment))
        //login_button.setOnClickListener{

            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_mainFragment)
            //doSignUp()
        //}
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        updateUI(currentUser)
    }
    fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null) {
            Toast.makeText(this.context, "Bienvenido.",Toast.LENGTH_LONG).show()
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_mainFragment)
        } else {
            Toast.makeText( this.context, "Usuario no identificado.",Toast.LENGTH_LONG).show()
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

        auth.signInWithEmailAndPassword(email_tf.toString(), psswd_layout.toString())
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }

    }

    private fun doSignUp() {
        auth.createUserWithEmailAndPassword(email_tf.text.toString(), psswd_tf.text.toString())
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(this.context, "User Created",
                        Toast.LENGTH_LONG).show()
                    val user = auth.currentUser
                    if(user != null){
                        updateUI(user)
                    } else {
                        Toast.makeText(this.context, "error al crear usuario: usuario nulo",
                            Toast.LENGTH_LONG).show()
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this.context, "Authentication failed.",
                        Toast.LENGTH_LONG).show()
                    updateUI(null)
                }
            }

    }

}
