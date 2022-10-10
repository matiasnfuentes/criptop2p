package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.controller.dto.ListableUserDTO
import ar.edu.unq.criptop2p.controller.dto.LoginDTO
import ar.edu.unq.criptop2p.controller.dto.UserDTO
import ar.edu.unq.criptop2p.model.User
import ar.edu.unq.criptop2p.persistance.UserRepository
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
) {

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)
    fun findById(id: Long): Optional<User> = userRepository.findById(id)

    fun authenticate(jwt: String?): User? {
        if (jwt == null) {
            throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid JWT token"
            )
        }
        val key: Key = Keys.hmacShaKeyFor(
            "secret_key_that_must_be_changed_and_must_be_vey_long".toByteArray(StandardCharsets.UTF_8)
        )
        try {
            val issuer = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).body.issuer
            return this.findByEmail(issuer)
        } catch (e: ExpiredJwtException){
            throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid JWT token"
            )
        }
    }

    fun login(loginDTO: LoginDTO): ResponseEntity<String> {

        val user =
            this.findByEmail(loginDTO.email) ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User does not exist"
            )

        if (!user.comparePassword(loginDTO.password)) throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Password does not match"
        )

        val issuer = user.getEmail()
        val key: Key = Keys.hmacShaKeyFor(
            "secret_key_that_must_be_changed_and_must_be_vey_long".toByteArray(StandardCharsets.UTF_8)
        )
        val jwt = Jwts.builder().setIssuer(issuer).setExpiration(Date(System.currentTimeMillis() + 60 * 60 * 1000))
            .signWith(key, SignatureAlgorithm.HS256).compact()

        return ResponseEntity.ok(jwt)
    }

    fun save(userDto: UserDTO) {
        if (this.findByEmail(userDto.email) != null) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "Email already registered"
        )

        val user = UserDTO.toUser(userDto)
        user.encodePassword()

        userRepository.save(user)
    }

    // TODO: [CRIP-21] - Agregar reputación y cantidad de operaciones a la lista de usuarios
    fun getUserList(): List<ListableUserDTO> = userRepository.findAll().map { ListableUserDTO.fromUser(it) }

}