package ar.edu.unq.criptop2p.persistance

import ar.edu.unq.criptop2p.model.Request
import ar.edu.unq.criptop2p.model.RequestStatus
import ar.edu.unq.criptop2p.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface RequestRepository: JpaRepository<Request, Long> {
    fun findByOwnerAndStatus(owner: User, status:RequestStatus):List<Request>
    fun findByStatus(status:RequestStatus):List<Request>

    @Query("SELECT " +
            "   * " +
            "FROM " +
            "   request " +
            "WHERE " +
            ":#{#user.getId()} IN (COUNTERPART_ID, OWNER_ID)" +
            "AND FINISHED_TIME_STAMP > :#{#dateFrom} " +
            "AND FINISHED_TIME_STAMP < :#{#dateTo} " +
            "AND STATUS = 3", nativeQuery = true) //3 = ACCEPTED
    fun findAllUserCompletedTransactions(user:User, dateFrom: Date, dateTo: Date):List<Request>
}