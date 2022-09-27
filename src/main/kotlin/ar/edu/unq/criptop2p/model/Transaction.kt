package ar.edu.unq.criptop2p.model

import java.util.*

class Transaction(
    private val buyRequest: Request,
    private val sellRequest: Request
) {

    private val timeStamp: Date = Date()

    fun getBuyRequest(): Request = this.buyRequest
    fun getSellRequest(): Request = this.sellRequest
    fun getTimeStamp(): Date = this.timeStamp

}