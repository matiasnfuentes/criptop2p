package ar.edu.unq.criptop2p.controller


import ar.edu.unq.criptop2p.controller.dto.RequestDTO
import ar.edu.unq.criptop2p.service.RequestService
import ar.edu.unq.criptop2p.service.UserService
import io.swagger.v3.oas.annotations.Operation
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

    @Operation(summary="API to post a Request", description="This endpoint allow a registered user to post a Request. Parameters BUY | SELL. (secret_token needed)")
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