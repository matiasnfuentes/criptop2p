package ar.edu.unq.criptop2p.controller

import ar.edu.unq.criptop2p.controller.dto.DateIntervalDTO
import ar.edu.unq.criptop2p.controller.dto.TotalTransactionReportDTO
import ar.edu.unq.criptop2p.service.TransactionService
import ar.edu.unq.criptop2p.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("api/transaction")
class TransactionController(
         @Autowired
         private val transactionService: TransactionService,
         @Autowired
         private val userService: UserService
         ) {

    @Operation(summary="API to list a summary of a user transactions", description="This endpoint give a detail all the user transaction completed.")
    @GetMapping("/{userId}")
    fun getUserTransactions(
            @PathVariable userId: Long,
            @RequestHeader("secret_token") secretToken: String,
            @RequestBody body: DateIntervalDTO
    ): ResponseEntity<TotalTransactionReportDTO> {
        userService.authenticate(secretToken)
        val user = userService.findById(userId)
        return ResponseEntity.ok().body(transactionService.getUserTransactions(user, body.dateFrom, body.dateTo))
    }

}