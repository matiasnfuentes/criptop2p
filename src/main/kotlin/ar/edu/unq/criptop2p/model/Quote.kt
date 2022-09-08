package ar.edu.unq.criptop2p.model

import java.time.LocalDate

class Quote(private val price: Double,
            private val cryptoCurrency: CryptoCurrency) {

    private val timestamp: LocalDate

    init {
        this.timestamp = LocalDate.now()
    }

}