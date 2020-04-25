package com.example.tenderosapp.data.client

class PagoFonClient {

    companion object {
        private var instance: PagoFonClient? = null
        fun getInstance(): PagoFonClient {
            if (instance == null) {
                instance = PagoFonClient()
            }
            return instance as PagoFonClient
        }
    }

}
