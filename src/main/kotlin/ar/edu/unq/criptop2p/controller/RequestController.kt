package ar.edu.unq.criptop2p.controller


import ar.edu.unq.criptop2p.controller.dto.ListableRequestDTO
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

    @Operation(summary="API to post a request", description="This endpoint allow a registered user to post a request (secret_token needed).")
    @PostMapping("/save")
    fun save(
        @RequestBody request: RequestDTO,
        @RequestHeader("secret_token") secretToken: String
    ) {
        val user = userService.authenticate(secretToken)
        requestService.save(request, user)
    }

    @Operation(summary="API to list a active requests", description="This endpoint list all current active requests (secret_token needed).")
    @GetMapping("/{userId}")
    fun getActive(
            @PathVariable userId: Long,
            @RequestHeader("secret_token") secretToken: String
    ):List<ListableRequestDTO> {
        userService.authenticate(secretToken)
        val user = userService.findById(userId)
        return requestService.getActiveRequest(user)
    }

}