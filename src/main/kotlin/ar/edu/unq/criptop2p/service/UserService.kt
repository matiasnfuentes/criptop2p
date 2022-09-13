package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.persistance.UserDto
import ar.edu.unq.criptop2p.persistance.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
        @Autowired
        private val userRepository: UserRepository,
        @Autowired
        private val passwordEncoder: PasswordEncoder) {

    fun save(userDto: UserDto){
        if(userRepository.findByEmail(userDto.getEmail()) == null) {
            userRepository.save(userDto2user(userDto))
        }
        else {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Email already registered")
        }
    }

    fun get(email: String):User?{
        return userRepository.findByEmail(email)
    }

    fun userDto2user(userDto: UserDto):User{
        return User(userDto.getFirstName(),
                    userDto.getLastName(),
                    userDto.getEmail(),
                    passwordEncoder.encode(userDto.getPassword()),
                    userDto.getCvu(),
                    userDto.getWalletAddress(),
                    userDto.getAddress())
    }

}