package ar.edu.unq.criptop2p.persistance

import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestStatus
import ar.edu.unq.criptop2p.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface RequestRepository: JpaRepository<Request, Long> {
    fun findByUserAndStatus(User: User, status:RequestStatus):List<Request>
    fun findByStatus(status:RequestStatus):List<Request>
}