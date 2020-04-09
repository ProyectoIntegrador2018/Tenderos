package com.example.tenderosapp.ui.identification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.tenderosapp.MainActivity
import com.example.tenderosapp.R
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.RenderResult
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.github.sumimakito.awesomeqr.option.color.Color
import kotlinx.android.synthetic.main.fragment_display_store_id.*
import kotlinx.android.synthetic.main.item_list.view.*

class DisplayIdFragment : Fragment(R.layout.fragment_display_store_id) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                exitbutton_ib.setOnClickListener {
            (context as MainActivity).navController.popBackStack()
        }

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


        val result = AwesomeQrRenderer.renderAsync(renderOption, { result ->
            if (result.bitmap != null) {
                //Manipular lo desplegado en pantalla
                title_tv.text = "my name is jeb"
                qrcode_iv.setImageBitmap(result.bitmap)
                //val imageView = ImageView(this)
                //imageView.setImageBitmap(result.bitmap)
                //constraintLayout.addView(imageView)

            } else if (result.type == RenderResult.OutputType.GIF) {
                // En caso de tener un background Gif (El cual posiblemente agregemos para más estética)
            } else {
                //Regresar Error
            }
        }, {
                exception -> exception.printStackTrace()
        })



    }

}
