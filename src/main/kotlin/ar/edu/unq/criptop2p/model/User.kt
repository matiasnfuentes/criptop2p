package ar.edu.unq.criptop2p.model


import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name="_User")
class User(@field:Size(min = 3, max = 30, message = "firstName length should be min 3 max 30")
           @Column
           private var firstName: String,
           @field:Size(min = 3, max = 30, message = "lastName length should be min 3 max 30")
           @Column
           private var lastName: String,
           @field:[Email(message = "email should be a valid address") NotEmpty(message = "email should not be empty")]
           @Column
           private var email: String,
           @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$",
                          message = "Password should be at lest 6 char long, with 1 uppercase, 1 lowercase, special character")
           @Column
           private var password: String,
           @field:Size(min = 22, max = 22, message = "cvu length should be 22 characters long")
           @Column
           private var cvu: String,
           @field:Size(min = 8, max = 8, message = "walletAddress length should be 8 characters long")
           @Column
           private var walletAddress: String,
           @Valid
           @OneToOne(cascade=[CascadeType.ALL])
           private var address: Address) {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private var id: Long? = null

    fun reputation(): Int{
        //TODO: create reputation query
        return 0
    }

    fun totalTransactions(): Int{
        //TODO: create transactions query
        return 0
    }

    fun getEmail():String {
        return this.email
    }
}