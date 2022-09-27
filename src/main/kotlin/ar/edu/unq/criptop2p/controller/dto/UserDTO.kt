package ar.edu.unq.criptop2p.controller.dto

import ar.edu.unq.criptop2p.model.Address
import ar.edu.unq.criptop2p.model.User
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class UserDTO(
    @field:Size(min = 3, max = 30, message = "firstName length should be min 3 max 30")
    val firstName: String,
    @field:Size(min = 3, max = 30, message = "lastName length should be min 3 max 30")
    val lastName: String,
    @field:[Email(message = "email should be a valid address") NotEmpty(message = "email should not be empty")]
    val email: String,
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$",
        message = "Password should be at lest 6 char long, with 1 uppercase, 1 lowercase, special character"
    )
    val password: String,
    @field:Size(min = 22, max = 22, message = "cvu length should be 22 characters long")
    val cvu: String,
    @field:Size(min = 8, max = 8, message = "walletAddress length should be 8 characters long")
    val walletAddress: String,
    @field:Valid
    val address: Address
) {

    val id: Long? = null

    companion object {
        fun toUser(userDTO: UserDTO): User = User(
            userDTO.firstName,
            userDTO.lastName,
            userDTO.email,
            userDTO.password,
            userDTO.cvu,
            userDTO.walletAddress,
            userDTO.address
        )

    }


}