package ar.edu.unq.criptop2p.controller

import ar.edu.unq.criptop2p.controller.dto.ListableUserDTO
import ar.edu.unq.criptop2p.controller.dto.LoginDTO
import ar.edu.unq.criptop2p.controller.dto.UserDTO
import ar.edu.unq.criptop2p.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/user")
class UserController(
    @Autowired
    private val userService: UserService
) {

    @Operation(summary="API for user registration", description="This endpoint registers a new user on the platform.")
    @PostMapping("register")
    fun register(
        @Valid
        @RequestBody
        userDto: UserDTO
    ) {
        userService.save(userDto)
    }

    @Operation(summary="API for user login", description="This endpoint authenticate a user by email and password and returns a secret token.")
    @PostMapping("login")
    fun login(
        @Valid
        @RequestBody
        loginDTO: LoginDTO
    ): ResponseEntity<String> {
        return userService.login(loginDTO)
    }

    @Operation(summary="API to list users", description="This endpoint list all the registered users on the platform.")
    @GetMapping("/list")
    fun getUserList(): ResponseEntity<List<ListableUserDTO>> {
        return ResponseEntity.ok().body(userService.getUserList())
    }

}