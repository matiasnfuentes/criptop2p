package ar.edu.unq.criptop2p.persistance

import ar.edu.unq.criptop2p.model.CryptoCurrency
import ar.edu.unq.criptop2p.model.RequestType


class RequestDto(private val cryptoCurrency: CryptoCurrency,
                 private val priceLimit: Double,
                 private val amount: Int,
                 private val type: RequestType){

    fun getCryptoCurrency(): CryptoCurrency{
        return this.cryptoCurrency
    }

    fun getPriceLimit(): Double{
        return this.priceLimit
    }

    fun getAmount(): Int{
        return this.amount
    }

    fun getType(): RequestType{
        return this.type
    }

}