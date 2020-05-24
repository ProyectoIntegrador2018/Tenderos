package com.example.tenderosapp.ui.transaction

import android.view.View
import com.example.tenderosapp.MainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tenderosapp.R
import com.example.tenderosapp.model.Transaction
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.RenderResult
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.github.sumimakito.awesomeqr.option.color.Color
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_transaction.*
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 */
class DisplayPromoFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    //Quitar onCreateView  y ver como se hacer en  HOmeFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_transaction , container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transactionData = arguments?.getString("transaction_data")
        val gson = Gson()
        //val transaction: Transaction = gson.fromJson(transactionData, Transaction::class.java)

        //val transactionAccepted = "{\"transactionId\":${transaction.transactionId}, \"status\":\"accepted\"}"

        //Opciones de Colores del QR
        val color = Color()
        color.light = 0xFFFFFFFF.toInt()
        color.dark = 0xFFFF4040.toInt()
        color.background = 0xFFFFFFFF.toInt()
        color.auto = false

        //Opciones de Renderizado del QR
        val renderOption = RenderOption()
        if(transactionData != null)
            renderOption.content = transactionData //Contenido del QR
        renderOption.size = 800 //Tamaño del QR
        renderOption.borderWidth = 20 //Espaciado del marco del QR
        renderOption.patternScale = 0.75f //Tamaño del Patron
        renderOption.clearBorder = true //Color de fondo
        renderOption.color = color //Colores definidos anteriormente
        //renderOption.background = background //Tener un fondo
        // renderOption.logo = logo //Usar un logo en el codigo QR
        try {
            val result = AwesomeQrRenderer.render(renderOption)
            if (result.bitmap != null) {
                qrcode_iv.setImageBitmap(result.bitmap)
                // play with the bitmap
            } else if (result.type == RenderResult.OutputType.GIF) {
                // If your Background is a GifBackground, the image
                // will be saved to the output file set in GifBackground
                // instead of being returned here. As a result, the
                // result.bitmap will be null.
            } else {
                // Oops, something gone wrong.
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Oops, something gone wrong.
        }

        exitbutton_ib.setOnClickListener {
            (context as MainActivity).navController.popBackStack(R.id.home_fragment, false)
        }
    }
}
