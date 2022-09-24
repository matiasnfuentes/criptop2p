package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestType
import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.controller.dto.RequestDTO
import ar.edu.unq.criptop2p.persistance.RequestRepository
import ar.edu.unq.criptop2p.persistance.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class RequestService(@Autowired
                     private val requestRepository: RequestRepository,
                     @Autowired
                     private val userRepository: UserRepository,
                     @Autowired
                     private val cryptoService: CryptoService) {

    fun save(requestType: String, requestDto: RequestDTO){
        try {
            var type: RequestType
            try {
                type = RequestType.valueOf(requestType)
            } catch (e: Exception) {
                throw Exception( "Invalid Parameter " + requestType )
            }

            if (requestDto.getPriceLimit() < 1) { throw Exception( "Price limit must be > 0" ) }
            if (requestDto.getAmount() < 1) { throw Exception( "Amount must be > 0") }
            if ( ! cryptoService.getSymbolList().contains(requestDto.getCryptoCurrency().getSymbol())) { throw Exception( "Invalid cryptocurrency symbol") }
            //TODO: validate CryptoCurrency
            //TODO: Recover user from database with id from JWT token - mock@domain
            val user : User? = userRepository.findByEmail("mock@domain.com")
            if (user != null) { requestRepository.save(requestDto2request(type, requestDto, user)) } else { throw Exception( "Invalid user") }
        }
        catch (e:Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    private fun requestDto2request(type:RequestType, requestDto: RequestDTO, user: User): Request{
        return Request(requestDto.getCryptoCurrency(), requestDto.getPriceLimit(), requestDto.getAmount(), user, type);
    }

}