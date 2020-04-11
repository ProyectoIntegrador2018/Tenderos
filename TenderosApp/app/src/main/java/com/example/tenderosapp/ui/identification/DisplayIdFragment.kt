package com.example.tenderosapp.ui.identification

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.example.tenderosapp.MainActivity
import com.example.tenderosapp.R
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.RenderResult
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.github.sumimakito.awesomeqr.option.color.Color
import kotlinx.android.synthetic.main.fragment_display_store_id.*

class DisplayIdFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_display_store_id , container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title_tv.text = "Doña Chuchita"

        //val constraintLayout = findViewById(R.id.constraintLayout) as ConstraintLayout
        //Opciones de Colores del QR
        val color = Color()
        color.light = 0xFFFFFFFF.toInt()
        color.dark = 0xFFFF4040.toInt()
        color.background = 0xFFFFFFFF.toInt()
        color.auto = false

        //Opciones de Renderizado del QR
        val renderOption = RenderOption()
        renderOption.content = "Si estás viendo esto, todo salió correctamente" //Contenido del QR
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
