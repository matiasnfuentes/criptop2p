package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.controller.dto.RequestDTO
import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestType
import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.persistance.RequestRepository
import ar.edu.unq.criptop2p.persistance.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class RequestService(
    @Autowired
    private val requestRepository: RequestRepository,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val cryptoService: CryptoService
) {

    fun save(requestType: String, requestDto: RequestDTO, user: User?) {
        try {
            if (user == null) throw Exception("Invalid user")

            val type: RequestType

            try {
                type = RequestType.valueOf(requestType.uppercase())
            } catch (e: Exception) {
                throw Exception("Invalid Parameter $requestType")
            }

            checkIFZeroOrLess(requestDto.priceLimit, "Price limit must be > 0")
            checkIFZeroOrLess(requestDto.amount, "Amount must be > 0")
            cryptoService.checkCryptoSymbol(requestDto.cryptoCurrency.getSymbol())
            requestRepository.save(requestDto2request(type, requestDto, user))

        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    private fun requestDto2request(type: RequestType, requestDto: RequestDTO, user: User): Request {
        return Request(requestDto.cryptoCurrency, requestDto.priceLimit, requestDto.amount, user, type)
    }

    private fun checkIFZeroOrLess(number: Double, exceptionMessage: String) {
        if (number <= 0) throw Exception(exceptionMessage)
    }

}