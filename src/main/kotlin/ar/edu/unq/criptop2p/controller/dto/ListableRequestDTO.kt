package ar.edu.unq.criptop2p.controller.dto

import ar.edu.unq.criptop2p.model.CryptoCurrency
import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestType
import java.util.*

class ListableRequestDTO(
    val type: RequestType,
    val cryptoCurrency: CryptoCurrency,
    val amount: Double,
    val priceARG: Double,
    val lastnameName: String,
    val userOperations: Int,
    val userReputation: String,
    val timestamp: Date,
    val id: Long?
) {

    companion object {
        fun fromRequest(request: Request, dollarPrice: Double): ListableRequestDTO {
            val owner = request.getOwner()
            val totalTransactions = owner.getTotalTransactions()
            val userReputation =
                if (totalTransactions > 0) (owner.getReputation() / totalTransactions).toString() else "SIN OPERACIONES"
            val type = request.getType()


            return ListableRequestDTO(
                type,
                request.getCryptoCurrency(),
                request.getAmount(),
                request.getPriceARS(dollarPrice),
                owner.getLastName() + "," + owner.getFirstName(),
                totalTransactions,
                userReputation,
                request.getCreationTimeStamp(),
                request.getId()
            )
        }
    }
}

