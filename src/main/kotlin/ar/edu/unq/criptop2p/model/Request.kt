package ar.edu.unq.criptop2p.model

import java.time.LocalDate

abstract class Request(private var cryptoCurrency: CryptoCurrency,
                       private var limit: Double,
                       private var amount: Int,
                       private var user: User) {

    private var timeStamp: LocalDate
    private var score: Int

    init {
        this.timeStamp = LocalDate.now()
        this.score = 0
    }

    abstract fun status()

}