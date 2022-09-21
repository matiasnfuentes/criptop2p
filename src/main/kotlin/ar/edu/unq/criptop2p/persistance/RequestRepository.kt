package ar.edu.unq.criptop2p.persistance

import ar.edu.unq.criptop2p.model.Request
import org.springframework.data.jpa.repository.JpaRepository

interface RequestRepository: JpaRepository<Request, Long>