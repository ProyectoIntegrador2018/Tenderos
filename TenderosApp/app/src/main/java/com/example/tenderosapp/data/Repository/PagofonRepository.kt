package com.example.tenderosapp.data.Repository

//Singleton
class PagofonRepository private constructor() {
    private val pagofonRepository: PagofonRepository

    init {
        pagofonRepository =
            getInstance()
    }

    companion object {
        private var instance: PagofonRepository? = null
        fun getInstance(): PagofonRepository {
            if (instance == null) {
                instance =
                    PagofonRepository()
            }
            return instance as PagofonRepository
        }
    }

}