package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestType
import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.persistance.RequestDto
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
                     private val userRepository: UserRepository) {

    fun save(requestType: String, requestDto: RequestDto){
        try {
            val type:RequestType = RequestType.valueOf(requestType)
            if (requestDto.getPriceLimit() < 1) { throw Exception() }
            if (requestDto.getAmount() < 1) { throw Exception() }
            //TODO: validate CryptoCurrency
            //TODO: Recover user from database with id from JWT token
            val user : User? = userRepository.findByEmail("mock@domain.com")
            if (user != null) { requestRepository.save(requestDto2request(type, requestDto, user)) }
        }
        catch (e:Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    private fun requestDto2request(type:RequestType, requestDto: RequestDto, user: User): Request{
        return Request(requestDto.getCryptoCurrency(), requestDto.getPriceLimit(), requestDto.getAmount(), user, type);
    }

}