package ar.edu.unq.criptop2p.model

import java.util.*

class Transaction(private val buyRequest: Request,
                  private val sellRequest: Request) {

    private val timeStamp: Date

    init {
        this.timeStamp = Date()
    }

}