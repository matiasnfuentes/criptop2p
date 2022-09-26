package ar.edu.unq.criptop2p.controller

import ar.edu.unq.criptop2p.controller.dto.ListableUserDTO
import ar.edu.unq.criptop2p.controller.dto.UserDTO
import ar.edu.unq.criptop2p.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api/user")
class UserController(
        @Autowired
        private val userService: UserService) {

    @PostMapping
    fun register(@Valid
                 @RequestBody
                 userDto: UserDTO
    ){
        userService.save(userDto)
    }

    @GetMapping( "/list")
    fun getUserList(): ResponseEntity<List<ListableUserDTO>> {
        return ResponseEntity.ok().body(userService.getUserList())
    }

}