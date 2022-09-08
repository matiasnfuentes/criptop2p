package ar.edu.unq.criptop2p.model

import java.time.LocalDate

class Transaction(private val buyRequest: BuyRequest,
                  private val sellRequest: SellRequest) {

    private val timeStamp: LocalDate

    init {
        this.timeStamp = LocalDate.now()
    }

}