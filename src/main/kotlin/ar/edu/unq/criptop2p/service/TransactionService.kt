package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.controller.dto.CurrencyTransactionReportDTO
import ar.edu.unq.criptop2p.controller.dto.TotalTransactionReportDTO
import ar.edu.unq.criptop2p.model.Request
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
        private val requestRepository: RequestRepository,
        @Autowired
        private val cryptoService: CryptoService,
        @Autowired
        private val requestService: RequestService
) {

    fun getUserTransactions(user: Optional<User>, dateFrom: Date, dateTo: Date): TotalTransactionReportDTO {
        if (!user.isPresent) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user")
        if (dateFrom > dateTo) {throw ResponseStatusException(HttpStatus.BAD_REQUEST, "dateFrom should be before dateTo")}

        val allUserRequest = requestRepository.findAllUserCompletedTransactions(user.get(), dateFrom, dateTo)
        val totalPriceOperated = totalPriceOperated(allUserRequest)
        val transactionSummary = transactionSummary(allUserRequest)

        return TotalTransactionReportDTO( totalPriceOperated.first,
                                          totalPriceOperated.second,
                                          transactionSummary)
    }

    private fun transactionSummary(allUserRequest: List<Request>): List<CurrencyTransactionReportDTO> {
        val result = arrayListOf<CurrencyTransactionReportDTO>()

        for (symbol in cryptoService.getSymbolList()){
            val currencyList = allUserRequest.filter{it.getCryptoCurrency().getSymbol() == symbol}

            if (currencyList.isEmpty()) continue

            val totalAmountOperated = totalAmountOperated(currencyList)
            val currentUSDPrice = cryptoService.getPrice(symbol)
            val currentARGPrice = requestService.getDollarPrice()
            result.add(CurrencyTransactionReportDTO( symbol,
                                                    totalAmountOperated,
                                                    currentUSDPrice,
                                                    currentUSDPrice * currentARGPrice)
            )
        }
        return result
    }

    private fun totalPriceOperated(requests: List<Request>): Pair<Double, Double> =
            requests.map { it.priceOperated() } .fold(Pair(0.0, 0.0)) { acc, next -> Pair(acc.first + next.first, acc.second + next.second) }
    private fun totalAmountOperated(requests: List<Request>): Double = requests.fold(0.0) { acc, next -> acc + next.getAmount() }

}