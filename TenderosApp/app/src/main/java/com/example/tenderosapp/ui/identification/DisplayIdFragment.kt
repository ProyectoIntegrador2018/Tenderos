package com.example.tenderosapp.ui.identification

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.example.tenderosapp.MainActivity
import com.example.tenderosapp.R
import com.example.tenderosapp.util.Encrypter.Companion.encodeQRData
import com.example.tenderosapp.util.Encrypter.Companion.encodeTransactionData
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.RenderResult
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.github.sumimakito.awesomeqr.option.color.Color
import kotlinx.android.synthetic.main.fragment_display_store_id.*
import java.text.SimpleDateFormat
import java.util.*

class DisplayIdFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //Quitar onCreateView  y ver como se hacer en  HOmeFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_display_store_id , container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title_tv.text = "Doña Chuchita"

        var currentUsername  = "a01196412@gmail.com"
        var currentStoreName = "donachuchita"
        var currentTime = System.currentTimeMillis()
        var sdf = SimpleDateFormat("MMM dd,yyyy HH:mm")
        var timeStamp = Date(currentTime)
        var res = encodeQRData(currentUsername, currentStoreName, timeStamp.toString())
        //PROMOCION
      //var res =  encodeTransactionData("{\"couponCode\":\"BF3710\",\"couponDiscount\":\"8\",\"couponType\":\"Porciento\",\"description\":\"Consigue el 8% de descuento en tu primera compra en este establecimiento.\",\"businessId\":\"987654\",\"businessName\":\"Bimbo\",\"ExpiryDate\":1621884226000}")
        //TRANSACCION
       // var res = encodeTransactionData("{\"previousBalance\":\"-400\",\"actualBalance\":\"-1400\",\"payment\":\"500\",\"businessId\":\"987654\",\"businessName\":\"Bimbo\",\"date\":1586309519369,\"products\":[{\"deliveredAmount\":10,\"productId\":\"gansito_123456789\",\"productName\":\"Gansitos\",\"returnedAmount\":0,\"total\":1000},{\"deliveredAmount\":5,\"productId\":\"Pinguino_123456789\",\"productName\":\"Pinguinos\",\"returnedAmount\":0,\"total\":500}],\"saleTotal\":1500,\"storeId\":\"123456\",\"storeName\":\"Doña Chuchita\",\"transactionId\":\"1234\"}")
        //val constraintLayout = findViewById(R.id.constraintLayout) as ConstraintLayout
        //Opciones de Colores del QR
        val color = Color()
        color.light = 0xFFFFFFFF.toInt()
        color.dark = 0xFFFF4040.toInt()
        color.background = 0xFFFFFFFF.toInt()
        color.auto = false

        //Opciones de Renderizado del QR
        val renderOption = RenderOption()
        renderOption.content = res //Contenido del QR
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
            (context as MainActivity).navController.popBackStack()
        }
    }
}
