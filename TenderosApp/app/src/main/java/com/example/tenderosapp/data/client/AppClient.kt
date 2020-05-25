package com.example.tenderosapp.data.client

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AppClient {
    var isEmailRegistered: MutableLiveData<Boolean?>
    var db: FirebaseFirestore
    private val settings : FirebaseFirestoreSettings

    init {
        isEmailRegistered = MutableLiveData()
        db = FirebaseFirestore.getInstance()
        settings =  FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings // Offline Mode

    }

    companion object {
        private var instance: AppClient? = null
        fun getInstance(): AppClient {
            if (instance == null) {
                instance = AppClient()
            }
            return instance as AppClient
        }
    }

    fun getIsEmailRegistered(): LiveData<Boolean?> = isEmailRegistered

    fun queryIsEmailRegistered(email : String) {
        db.collection("approved_mails")
            .whereEqualTo("mail", email)
            .get()
            .addOnSuccessListener { documents ->
                Log.d("QUERY SUCCESS => ", documents.isEmpty.toString())
                if (!documents.isEmpty){
                    isEmailRegistered.postValue(true)
                }else{
                    isEmailRegistered.postValue(false)

                }

            }
            .addOnFailureListener { exception ->
                Log.w("NO DOCUMENT FOUND", "Error getting documents: ", exception)
                isEmailRegistered.postValue(null)
            }
    }

}
