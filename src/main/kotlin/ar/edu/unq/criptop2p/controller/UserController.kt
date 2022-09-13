package ar.edu.unq.criptop2p.controller

import ar.edu.unq.criptop2p.persistance.UserDto
import ar.edu.unq.criptop2p.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(path = ["api/user"])
class UserController(
        @Autowired
        private val userService: UserService) {

    @PostMapping
    fun register(@Valid
                 @RequestBody
                 userDto: UserDto){
        userService.save(userDto)
    }

}