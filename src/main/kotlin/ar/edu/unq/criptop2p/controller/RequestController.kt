package ar.edu.unq.criptop2p.controller


import ar.edu.unq.criptop2p.persistance.RequestDto
import ar.edu.unq.criptop2p.service.RequestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("api/Request")
class RequestController(@Autowired
                        private val requestService: RequestService) {

    @PostMapping("/{requestType}")
    fun save(@PathVariable requestType:String, @RequestBody request: RequestDto){
        requestService.save(requestType, request)
    }

}