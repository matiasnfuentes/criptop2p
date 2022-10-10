package ar.edu.unq.criptop2p.controller


import ar.edu.unq.criptop2p.service.RequestService
import ar.edu.unq.criptop2p.service.UserService
import ar.edu.unq.criptop2p.controller.dto.ListableRequestDTO
import ar.edu.unq.criptop2p.controller.dto.RequestDTO
import ar.edu.unq.criptop2p.controller.dto.RequestStatusDTO
import ar.edu.unq.criptop2p.controller.dto.UpdateRequestDTO
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

    @Operation(
        summary = "API to post a request",
        description = "This endpoint allow a registered user to post a request (secret_token needed)."
    )
    @PostMapping("/")
    fun save(
        @RequestBody request: RequestDTO,
        @RequestHeader("secret_token") secretToken: String
    ): ListableRequestDTO {
        val user = userService.authenticate(secretToken)
        return requestService.save(request, user)
    }

    @Operation(
        summary = "API to list a user available requests",
        description = "This endpoint lists a user current available requests (secret_token needed)."
    )
    @GetMapping("/{userId}")
    fun getUserAvailable(
        @PathVariable userId: Long,
        @RequestHeader("secret_token") secretToken: String
    ): List<ListableRequestDTO> {
        userService.authenticate(secretToken)
        val user = userService.findById(userId)
        return requestService.getUserAvailableRequests(user)
    }

    @Operation(
        summary = "API to list all available requests",
        description = "This endpoint lists all current available requests (secret_token needed)."
    )
    @GetMapping("/")
    fun getAllAvailable(
        @RequestHeader("secret_token") secretToken: String
    ): List<ListableRequestDTO> {
        userService.authenticate(secretToken)
        return requestService.getAllAvailableRequests()
    }

    @Operation(
        summary = "API to update a request status",
        description = "This endpoint allow a registered user to update the request status (secret_token needed)."
    )
    @PatchMapping("/{requestId}")
    fun updateStatus(
        @PathVariable requestId: Long,
        @RequestBody body: RequestStatusDTO,
        @RequestHeader("secret_token") secretToken: String
    ): UpdateRequestDTO {
        val user = userService.authenticate(secretToken)
        return requestService.updateStatus(requestId, body.status, user)
    }

}