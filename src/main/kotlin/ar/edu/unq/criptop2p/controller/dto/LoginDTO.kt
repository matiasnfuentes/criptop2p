package ar.edu.unq.criptop2p.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class LoginDTO(
    @field:Schema(name="email", description="Must have a valid email format")
    @field:[Email(message = "email should be a valid address") NotEmpty(message = "email should not be empty")]
    val email: String,
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$",
        message = "Password should be at lest 6 char long, with 1 uppercase, 1 lowercase, special character"
    )
    val password: String
) {
    override fun toString(): String {
        return "LoginDTO(email=$email, password=XXXXXX)"
    }
}