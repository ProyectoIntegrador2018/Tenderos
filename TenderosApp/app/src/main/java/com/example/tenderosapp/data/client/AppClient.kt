package com.example.tenderosapp.data.client

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tenderosapp.model.Promo
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
    var isPromoRegistered: MutableLiveData<Boolean?>

    var db: FirebaseFirestore
    private val settings : FirebaseFirestoreSettings

    init {
        isEmailRegistered = MutableLiveData()
        isPromoRegistered = MutableLiveData()
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
    fun getRegisterPromoSucccess(): LiveData<Boolean?> = isPromoRegistered

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

    fun queryRegisterPromo(promotion : Promo, uid : String) {
        Log.d("Promo => ", promotion.toString())

        db.collection("profile")
            .document(uid)
            .collection("promotion_list")
            .add(promotion)
            .addOnSuccessListener {
                Log.d("promotion_list", "DocumentSnapshot successfully written!")
                isPromoRegistered.postValue(true)
            }
            .addOnFailureListener { e -> Log.w(
                "promotion_list", "Error writing document", e)
                isPromoRegistered.postValue(false)
            }
    }

}
