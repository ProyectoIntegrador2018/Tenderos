package com.example.tenderosapp.data.client

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tenderosapp.model.Promo
import com.example.tenderosapp.model.Transaction
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
    var promotionlist: MutableLiveData<ArrayList<Promo>?>
    var transactionlist: MutableLiveData<ArrayList<Transaction>?>
    var isTransactionRegistered: MutableLiveData<Boolean?>


    var db: FirebaseFirestore
    private val settings : FirebaseFirestoreSettings

    init {
        isEmailRegistered = MutableLiveData()
        isPromoRegistered = MutableLiveData()
        promotionlist = MutableLiveData()
        isTransactionRegistered = MutableLiveData()
        transactionlist = MutableLiveData()
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
                instance = com.example.tenderosapp.data.client.AppClient()
            }
            return instance as AppClient
        }
    }

    fun getIsEmailRegistered(): LiveData<Boolean?> = isEmailRegistered
    fun getRegisterPromoSucccess(): LiveData<Boolean?> = isPromoRegistered
    fun getPromotionList() : LiveData<ArrayList<Promo>?> = promotionlist
    fun getRegisterTransactionSucccess(): LiveData<Boolean?> = isTransactionRegistered
    fun getTransactionList() : LiveData<ArrayList<Transaction>?> = transactionlist


    fun queryGetPromorionList(uid : String) {
        db.collection("profile")
            .document(uid)
            .collection("promotion_list")
            .get()
            .addOnCompleteListener { task ->
                val promotions : ArrayList<Promo> = ArrayList()

                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val myObject = document.toObject(Promo::class.java)
                        promotions.add(myObject)
                        Log.d("OBJECT", myObject.toString())
                    }
                } else {
                    Log.w("ERROR DATA", "Error getting documents.", task.getException())
                }
                Log.d("promo leng SIZE: ", promotions.size.toString())
                if (promotions.size >0){
                    promotionlist.postValue(promotions)
                }else promotionlist.postValue(null)
            }

    }

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

    fun queryGetTransactionList(uid : String) {
        db.collection("profile")
            .document(uid)
            .collection("transaction_list")
            .get()
            .addOnCompleteListener { task ->
                val transactions : ArrayList<Transaction> = ArrayList()

                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val myObject = document.toObject(Transaction::class.java)
                        transactions.add(myObject)
                        Log.d("OBJECT", myObject.toString())
                    }
                } else {
                    Log.w("ERROR DATA", "Error getting documents.", task.getException())
                }
                Log.d("transac leng SIZE: ", transactions.size.toString())
                if (transactions.size >0){
                    transactionlist.postValue(transactions)
                }else transactionlist.postValue(null)
            }

    }

    fun queryRegisterTransaction(transaction : Transaction, uid : String) {
        Log.d("Transaction => ", transaction.toString())

        db.collection("profile")
            .document(uid)
            .collection("transaction_list")
            .add(transaction)
            .addOnSuccessListener {
                Log.d("transaction_list", "DocumentSnapshot successfully written!")
                isTransactionRegistered.postValue(true)
            }
            .addOnFailureListener { e -> Log.w(
                "transaction_list", "Error writing document", e)
                isTransactionRegistered.postValue(false)
            }
    }

}
