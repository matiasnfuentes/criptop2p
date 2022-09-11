package ar.edu.unq.criptop2p.controller


import ar.edu.unq.criptop2p.model.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/user"])
class userController {

    @PostMapping
    fun register(@RequestBody
                 user: User){
        println(user)
    }

}