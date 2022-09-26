package ar.edu.unq.criptop2p.controller.dto

import ar.edu.unq.criptop2p.model.CryptoCurrency

class RequestDTO(val cryptoCurrency: CryptoCurrency,
                 val priceLimit: Double,
                 val amount: Double)