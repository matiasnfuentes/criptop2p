package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.persistance.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
        @Autowired
        private val userRepository: UserRepository
)  {

    fun save(user: User){
        userRepository.save(user)
    }

}