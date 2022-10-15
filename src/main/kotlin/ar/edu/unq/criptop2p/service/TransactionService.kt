package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.controller.dto.CurrencyTransactionReportDTO
import ar.edu.unq.criptop2p.controller.dto.TotalTransactionReportDTO
import ar.edu.unq.criptop2p.model.Request
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
        private val requestRepository: RequestRepository,
        @Autowired
        private val cryptoService: CryptoService,
        @Autowired
        private val requestService: RequestService
) {

    fun getUserTransaction(user: Optional<User>, dateFrom: Date, dateTo: Date): TotalTransactionReportDTO {
        if (!user.isPresent) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user")
        if (dateFrom > dateTo) {throw ResponseStatusException(HttpStatus.BAD_REQUEST, "dateFrom should be before dateTo")}

        val allUserRequest = requestRepository.findAllUserCompletedTransactions(user.get(), dateFrom, dateTo)
        val totalOperated = totalOperated(user.get(), allUserRequest)
        val transactionSummary = transactionSummary(user, allUserRequest)

        return TotalTransactionReportDTO( totalOperated.first,
                                          totalOperated.second,
                                          transactionSummary)
    }

    private fun transactionSummary(user: Optional<User>, allUserRequest: List<Request>): List<CurrencyTransactionReportDTO> {
        val result = arrayListOf<CurrencyTransactionReportDTO>()

        for (symbol in cryptoService.getSymbolList()){
            val currencyList = allUserRequest.filter{it.getCryptoCurrency().getSymbol() == symbol}

            if (currencyList.isEmpty()) continue

            val totalAmountOperated = totalAmountOperated(user.get(), currencyList)
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

    private fun totalOperated(user: User, requests: List<Request>): Pair<Double, Double> {
        var total = Pair(0.0, 0.0)
        for (r in requests) {
            val operated = operated(user, r)
            total = Pair(total.first + operated.first, total.second + operated.second)
        }
        return total
    }

    fun operated(user: User, request: Request): Pair<Double, Double> {
        var result = Pair(0.0, 0.0)
        if (request.getType() == RequestType.BUY) {
            if (request.getOwner().getId() == user.getId()) {
                val usdOperated = request.getAmount() * request.getCryptoCurrency().getPrice()
                result = Pair(usdOperated, usdOperated * request.getPriceArgAtCompletation())
            }
            else {
                var usdOperated = request.getAmount() * request.getCryptoCurrency().getPrice() * -1
                result = Pair(usdOperated, usdOperated * request.getPriceArgAtCompletation())
            }
        } else {
            if (request.getType() == RequestType.SELL) {
                if (request.getOwner().getId() == user.getId()) {
                    var usdOperated = request.getAmount() * request.getCryptoCurrency().getPrice() * -1
                    result = Pair(usdOperated, usdOperated * request.getPriceArgAtCompletation())

                } else {
                    var usdOperated = request.getAmount() * request.getCryptoCurrency().getPrice()
                    result = Pair(usdOperated, usdOperated * request.getPriceArgAtCompletation())
                }
            }
        }
       return result
    }

    private fun totalAmountOperated(user: User, requests: List<Request>): Double {
        var total = 0.0
        for (r in requests) {
            total += amountOperated(user, r)
        }
        return total
    }

    fun amountOperated(user: User, request: Request): Double {
        var result = 0.0
        if (request.getType() == RequestType.BUY) {
            if (request.getOwner().getId() == user.getId()) {
                result = request.getAmount()
            }
            else {
                result = request.getAmount() * -1
            }
        } else {
            if (request.getType() == RequestType.SELL) {
                if (request.getOwner().getId() == user.getId()) {
                    result = request.getAmount() * -1

                } else {
                    result = request.getAmount()
                }
            }
        }
        return result
    }

}