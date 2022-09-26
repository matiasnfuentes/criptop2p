package ar.edu.unq.criptop2p.model


import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "_User")
class User(
    @Column
    private var firstName: String,
    @Column
    private var lastName: String,
    @Column(unique = true)
    private var email: String,
    @Column
    @JsonIgnore
    private var password: String,
    @Column
    private var cvu: String,
    @Column
    private var walletAddress: String,
    @OneToOne(cascade = [CascadeType.ALL])
    private var address: Address
) {

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

    fun reputation(): Int {
        //TODO: create reputation query
        return 0
    }

    fun totalTransactions(): Int {
        //TODO: create transactions query
        return 0
    }

    fun getFirstName(): String {
        return this.firstName
    }

    fun getLastName(): String {
        return this.lastName
    }

    fun getEmail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }

    fun getCvu(): String {
        return this.cvu
    }

    fun getWalletAddress(): String {
        return this.walletAddress
    }

    fun getAddress(): Address {
        return this.address
    }

    fun getId(): Long? {
        return this.id
    }

    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }

}