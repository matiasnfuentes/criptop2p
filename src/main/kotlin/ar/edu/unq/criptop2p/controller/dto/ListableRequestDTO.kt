package ar.edu.unq.criptop2p.controller.dto

import ar.edu.unq.criptop2p.model.CryptoCurrency
import ar.edu.unq.criptop2p.model.RequestType
import java.util.*

class ListableRequestDTO ( val type: RequestType,
                           val cryptoCurrency: CryptoCurrency,
                           val amount: Double,
                           val priceARG: Double,
                           val nameLastname: String,
                           val userOperations: Int,
                           val userReputation: String,
                           val timeStamp: Date,
                           val id: Long?)

