package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.controller.dto.DollarCurrency
import ar.edu.unq.criptop2p.controller.dto.ListableRequestDTO
import ar.edu.unq.criptop2p.controller.dto.RequestDTO
import ar.edu.unq.criptop2p.controller.dto.UpdateRequestDTO
import ar.edu.unq.criptop2p.model.RequestStatus
import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.persistance.RequestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class RequestService(
    @Autowired
    private val requestRepository: RequestRepository,
    @Autowired
    private val cryptoService: CryptoService
) {

    fun save(requestDto: RequestDTO, user: User?): ListableRequestDTO {
        try {
            if (user == null) throw Exception("Invalid user")

            checkIFZeroOrLess(requestDto.priceLimit, "Price limit must be > 0")
            checkIFZeroOrLess(requestDto.amount, "Amount must be > 0")
            cryptoService.checkCryptoSymbol(requestDto.symbol)

            val savedRequest = requestRepository.save(RequestDTO.toRequest(requestDto, user))

            return ListableRequestDTO.fromRequest(savedRequest, this.getDollarPrice())

        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    fun getUserAvailableRequests(user: Optional<User>): List<ListableRequestDTO> {

        if (!user.isPresent) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user")

        val dollarPrice = this.getDollarPrice()

        return requestRepository.findByOwnerAndStatus(user.get(), RequestStatus.AVAILABLE)
            .map { ListableRequestDTO.fromRequest(it, dollarPrice) }
    }

    fun getAllAvailableRequests(): List<ListableRequestDTO> {
        val dollarPrice = this.getDollarPrice()
        return requestRepository.findByStatus(RequestStatus.AVAILABLE)
            .map { ListableRequestDTO.fromRequest(it, dollarPrice) }
    }

    fun updateStatus(requestId: Long, newStatus: RequestStatus, requester: User?): UpdateRequestDTO {
        val request = requestRepository.getReferenceById(requestId)
        val currentPrice: Double = if (newStatus == RequestStatus.WAITING_CONFIRMATION) cryptoService.getPrice(
            request.getCryptoCurrency().getSymbol()
        ) else 0.0
        request.updateStatus(newStatus, requester, currentPrice)
        if (request.getStatus() == RequestStatus.CONFIRMED) {
            val currentArgPrice = this.getDollarPrice()
            if (currentArgPrice == 0.0) throw Exception("Could not get current ARG value.")
            request.setPriceArgAtCompletation(currentArgPrice)
        }
        val updatedRequest = requestRepository.save(request)
        return UpdateRequestDTO.fromRequest(updatedRequest, this.getDollarPrice())
    }

    fun getDollarPrice(): Double {
        val prices = RestTemplate().getForObject(
            "https://dolarsi.com/api/api.php?type=valoresprincipales",
            Array<DollarCurrency>::class.java
        )

        return prices?.find { it.casa.nombre == "Dolar Blue" }?.casa?.sellPrice
            ?: throw Exception("Could not get current dollar price.")
    }

    private fun checkIFZeroOrLess(number: Double, exceptionMessage: String) {
        if (number <= 0) throw Exception(exceptionMessage)
    }


}