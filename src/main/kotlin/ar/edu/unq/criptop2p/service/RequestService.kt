package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.controller.dto.ListableRequestDTO
import ar.edu.unq.criptop2p.controller.dto.RequestDTO
import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestStatus
import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.persistance.RequestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class RequestService(
    @Autowired
    private val requestRepository: RequestRepository,
    @Autowired
    private val cryptoService: CryptoService,
) {

    fun save(requestDto: RequestDTO, user: User?) {
        try {
            if (user == null) throw Exception("Invalid user")

            checkIFZeroOrLess(requestDto.priceLimit, "Price limit must be > 0")
            checkIFZeroOrLess(requestDto.amount, "Amount must be > 0")
            cryptoService.checkCryptoSymbol(requestDto.symbol)

            requestRepository.save(RequestDTO.toRequest(requestDto, user))

        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    fun getUserAvailableRequests(user: Optional<User>):List<ListableRequestDTO>{

        if (!user.isPresent) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user")

        return requestRepository.findByUserAndStatus(user.get(), RequestStatus.AVAILABLE).map{ this.requestToListableRequestDTO(it) }
    }

    fun getAllAvailableRequests():List<ListableRequestDTO>{
        return requestRepository.findByStatus(RequestStatus.AVAILABLE).map{ this.requestToListableRequestDTO(it) }
    }

    private fun checkIFZeroOrLess(number: Double, exceptionMessage: String) {
        if (number <= 0) throw Exception(exceptionMessage)
    }

    private fun requestToListableRequestDTO(request: Request): ListableRequestDTO {

        return ListableRequestDTO(  request.getType(),
                                    request.getCryptoCurrency(),
                                    request.getAmount(),
                                    request.priceARG(),
                        request.getUser().getLastName() + "," + request.getUser().getFirstName(),
                                    request.getUser().totalTransactions(),
                                    request.getUser().reputation(),
                                    request.getTimeStamp(),
                                    request.getId())
    }

}