package ar.edu.unq.criptop2p.controller.dto

import ar.edu.unq.criptop2p.model.Address
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class UserDTO(
        @field:Size(min = 3, max = 30, message = "firstName length should be min 3 max 30")
        private var firstName: String,
        @field:Size(min = 3, max = 30, message = "lastName length should be min 3 max 30")
        private var lastName: String,
        @field:[Email(message = "email should be a valid address") NotEmpty(message = "email should not be empty")]
        private var email: String,
        @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$",
                message = "Password should be at lest 6 char long, with 1 uppercase, 1 lowercase, special character")
        private var password: String,
        @field:Size(min = 22, max = 22, message = "cvu length should be 22 characters long")
        private var cvu: String,
        @field:Size(min = 8, max = 8, message = "walletAddress length should be 8 characters long")
        private var walletAddress: String,
        @field:Valid
        private var address: Address
) {

    private var id: Long? = null

    fun getId(): Long?{
        return this.id;
    }

    fun getFirstName(): String{
        return this.firstName;
    }

    fun getLastName(): String{
        return this.lastName;
    }

    fun getEmail():String {
        return this.email
    }

    fun getPassword():String{
        return this.password;
    }

    fun getCvu():String{
        return this.cvu;
    }

    fun getWalletAddress():String{
        return this.walletAddress;
    }

    fun getAddress(): Address {
        return this.address;
    }

}