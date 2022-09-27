package ar.edu.unq.criptop2p.controller.dto

import ar.edu.unq.criptop2p.model.CryptoCurrency
import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestType
import ar.edu.unq.criptop2p.model.User

data class RequestDTO(
    val cryptoCurrency: CryptoCurrency,
    val priceLimit: Double,
    val amount: Double
) {
    companion object {
        fun toRequest(type: RequestType, requestDto: RequestDTO, user: User): Request =
            Request(requestDto.cryptoCurrency, requestDto.priceLimit, requestDto.amount, user, type)

    }
}