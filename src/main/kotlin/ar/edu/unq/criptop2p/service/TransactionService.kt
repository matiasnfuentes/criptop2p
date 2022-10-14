package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.controller.dto.TotalTransactionReportDTO
import ar.edu.unq.criptop2p.model.RequestType
import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.persistance.RequestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class TransactionService (
        @Autowired
        private val requestRepository: RequestRepository
) {

    fun getUserTransaction(user: Optional<User>, dateFrom: Date, dateTo: Date): TotalTransactionReportDTO {
        if (!user.isPresent) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user")
        if (dateFrom > dateTo) {throw ResponseStatusException(HttpStatus.BAD_REQUEST, "dateFrom should be before dateTo")}

        val allUserRequest = requestRepository.findAllUserCompletedTransactions(user.get(), dateFrom, dateTo)
        val totalUSD = allUserRequest.filter{ it.getType() == RequestType.BUY }.sumOf{ it.getAmount() * it.getCryptoCurrency().getPrice() } -
                       allUserRequest.filter{ it.getType() == RequestType.SELL }.sumOf{ it.getAmount() * it.getCryptoCurrency().getPrice() }
        val totalARG = allUserRequest.filter{ it.getType() == RequestType.BUY }.sumOf{ it.getAmount() * it.getCryptoCurrency().getPrice() * it.getPriceArgAtCompletation() } -
                       allUserRequest.filter{ it.getType() == RequestType.SELL }.sumOf{ it.getAmount() * it.getCryptoCurrency().getPrice() * it.getPriceArgAtCompletation() }
        return TotalTransactionReportDTO(totalUSD.toLong(), totalARG.toLong(), listOf())
    }

}