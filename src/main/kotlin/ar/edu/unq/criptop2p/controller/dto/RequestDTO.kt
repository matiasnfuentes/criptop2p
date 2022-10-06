package ar.edu.unq.criptop2p.controller.dto

import ar.edu.unq.criptop2p.model.CryptoCurrency
import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestType
import ar.edu.unq.criptop2p.model.User

data class RequestDTO(
    val symbol: String,
    val priceLimit: Double,
    val amount: Double,
    val type: RequestType,
    val id: Long? = null
) {

    companion object {
        fun toRequest(requestDto: RequestDTO, user: User): Request =
            Request(CryptoCurrency(requestDto.priceLimit, requestDto.symbol), requestDto.amount, user, requestDto.type)

    }
}