package ar.edu.unq.criptop2p.controller

import ar.edu.unq.criptop2p.controller.dto.ListableUserDTO
import ar.edu.unq.criptop2p.controller.dto.LoginDTO
import ar.edu.unq.criptop2p.controller.dto.UserDTO
import ar.edu.unq.criptop2p.service.UserService
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

    @PostMapping("register")
    fun register(
        @Valid
        @RequestBody
        userDto: UserDTO
    ) {
        userService.save(userDto)
    }

    @PostMapping("login")
    fun login(
        @Valid
        @RequestBody
        loginDTO: LoginDTO
    ): ResponseEntity<String> {
        return userService.login(loginDTO)
    }

    @GetMapping("/list")
    fun getUserList(): ResponseEntity<List<ListableUserDTO>> {
        return ResponseEntity.ok().body(userService.getUserList())
    }

}