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
    private var address: Address,
    @Column
    private var reputation: Int = 0,
    @Column
    private var totalTransactions: Int = 0,
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
) {

    fun getTotalTransactions(): Int = this.totalTransactions
    fun getReputation(): Int = this.reputation
    fun getFirstName(): String = this.firstName
    fun getLastName(): String = this.lastName
    fun getEmail(): String = this.email
    fun getPassword(): String = this.password
    fun getCvu(): String = this.cvu
    fun getWalletAddress(): String = this.walletAddress
    fun getAddress(): Address = this.address
    fun getId(): Long? = this.id

    fun setId(id: Long) {
        this.id = id
    }

    fun decreaseReputation(amount: Int) {
        if (this.reputation < amount) {
            this.reputation = 0
        } else {
            this.reputation -= amount
        }
    }

    fun increaseReputation(amount: Int) {
        this.reputation += amount
    }

    fun increaseTotalTransactionsByOne() {
        this.totalTransactions += 1
    }

    fun comparePassword(password: String): Boolean = BCryptPasswordEncoder().matches(password, this.password)
    fun encodePassword() {
        this.password = BCryptPasswordEncoder().encode(this.password)
    }

}