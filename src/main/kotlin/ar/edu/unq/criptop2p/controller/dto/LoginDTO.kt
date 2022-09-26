package ar.edu.unq.criptop2p.controller.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class LoginDTO(
    @field:[Email(message = "email should be a valid address") NotEmpty(message = "email should not be empty")]
    val email: String,
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$",
        message = "Password should be at lest 6 char long, with 1 uppercase, 1 lowercase, special character"
    )
    val password: String
)