package ar.edu.unq.criptop2p.controller


import ar.edu.unq.criptop2p.controller.dto.RequestDTO
import ar.edu.unq.criptop2p.service.RequestService
import ar.edu.unq.criptop2p.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/request")
class RequestController(
    @Autowired
    private val requestService: RequestService,
    @Autowired
    private val userService: UserService
) {

    @PostMapping("/{requestType}")
    fun save(
        @PathVariable requestType: String,
        @RequestBody request: RequestDTO,
        @RequestHeader("secret_token") secretToken: String
    ) {
        val user = userService.authenticate(secretToken)
        requestService.save(requestType, request, user)
    }

}