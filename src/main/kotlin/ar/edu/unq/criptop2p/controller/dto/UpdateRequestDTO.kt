package ar.edu.unq.criptop2p.controller.dto

import ar.edu.unq.criptop2p.model.CryptoCurrency
import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestType
import java.util.*

class UpdateRequestDTO(
    val type: RequestType,
    val cryptoCurrency: CryptoCurrency,
    val amount: Double,
    val priceARG: Double,
    val lastnameName: String,
    val userOperations: Int,
    val userReputation: String,
    val timeStamp: Date,
    val id: Long? = null,
    val deliveryAddress : String
) {

    companion object {
        fun fromRequest(request: Request, dollarPrice: Double): UpdateRequestDTO {
            val listableRequestDTO = ListableRequestDTO.fromRequest(request, dollarPrice)
            val owner = request.getOwner()
            val deliveryAddress = if (request.getType() == RequestType.SELL) owner.getCvu() else owner.getWalletAddress()


            return UpdateRequestDTO(
                listableRequestDTO.type,
                listableRequestDTO.cryptoCurrency,
                listableRequestDTO.amount,
                listableRequestDTO.priceARG,
                listableRequestDTO.lastnameName,
                listableRequestDTO.userOperations,
                listableRequestDTO.userReputation,
                listableRequestDTO.timeStamp,
                listableRequestDTO.id,
                deliveryAddress
            )
        }
    }

}