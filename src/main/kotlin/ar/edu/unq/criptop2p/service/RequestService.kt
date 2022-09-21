package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestType
import ar.edu.unq.criptop2p.persistance.RequestDto
import ar.edu.unq.criptop2p.persistance.RequestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class RequestService(@Autowired
                     private val requestRepository: RequestRepository) {

    fun save(requestType: String, requestDto: RequestDto){
        try {
            val enum:RequestType = RequestType.valueOf(requestType)
            //Recover user JWT
            //Validate dto
            //requestRepository.save(requestDto2request(enum, requestDto))
        }
        catch (e:Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameter")
        }
    }

//    private fun requestDto2request(requestDto: RequestDto): Request{
//
//        return ;
//    }

}