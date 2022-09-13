package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.persistance.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException




@Service
class UserService(
        @Autowired
        private val userRepository: UserRepository) {

    fun save(user: User){
        if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
            userRepository.save(user)
        }
        else {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Email already registered")
        }
    }

    fun get(email: String){
        userRepository.findByEmail(email)
    }

}