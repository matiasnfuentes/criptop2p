package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.controller.dto.ListableUserDTO
import ar.edu.unq.criptop2p.controller.dto.UserDTO
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

    fun save(userDto: UserDTO){
        if(userRepository.findByEmail(userDto.getEmail()) == null) {
            userRepository.save(userDTO2user(userDto))
        }
        else {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Email already registered")
        }
    }

    fun get(email: String):User?{
        return userRepository.findByEmail(email)
    }

    //@TECH-DEBT: [CRIP-21] - Agregar reputación y cantidad de operaciones a la lista de usuarios
    fun getUserList():List<ListableUserDTO> = userRepository.findAll().map { user2listableUserDTO(it) }

    fun userDTO2user(userDTO: UserDTO):User{
        return User(userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getEmail(),
                    passwordEncoder.encode(userDTO.getPassword()),
                    userDTO.getCvu(),
                    userDTO.getWalletAddress(),
                    userDTO.getAddress())
    }

    private fun user2listableUserDTO(user: User) = ListableUserDTO(user.getFirstName(), user.getLastName(), 0, 0)

}