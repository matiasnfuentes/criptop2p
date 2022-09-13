package ar.edu.unq.criptop2p.persistance

import ar.edu.unq.criptop2p.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String):User?

}