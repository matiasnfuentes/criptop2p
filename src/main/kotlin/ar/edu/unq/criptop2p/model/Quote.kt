package ar.edu.unq.criptop2p.model

import java.time.LocalDate

class Quote(private var price: Double) {

    private var timestamp: LocalDate

    init {
        this.timestamp = LocalDate.now()
    }

}