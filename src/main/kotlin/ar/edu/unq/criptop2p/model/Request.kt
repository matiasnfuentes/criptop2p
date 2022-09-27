package ar.edu.unq.criptop2p.model

import java.util.*
import javax.persistence.*

@Entity
class Request(
    @OneToOne(cascade = [CascadeType.ALL])
    private val cryptoCurrency: CryptoCurrency,
    @Column
    private val priceLimit: Double,
    @Column
    private val amount: Double,
    @ManyToOne
    private val user: User,
    @Column
    private val type: RequestType
) {

    @Id
    @SequenceGenerator(
        name = "request_sequence",
        sequenceName = "request_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "request_sequence"
    )
    @Column
    private val id: Long? = null

    @Column
    private var status: RequestStatus = RequestStatus.AVAILABLE

    @Column
    private val timeStamp: Date = Date()

    @Column
    private var score: Int = 0

    fun getCryptoCurrency(): CryptoCurrency = this.cryptoCurrency
    fun getPriceLimit(): Double = this.priceLimit
    fun getAmount(): Double = this.amount
    fun getUser(): User = this.user
    fun getType(): RequestType = this.type
    fun getStatus(): RequestStatus = this.status
    fun getTimeStamp(): Date = this.timeStamp
    fun getScore(): Int = this.score

    //TODO: implement api call to get current ARG value
    fun priceARG(): Double = 0.0

    fun waitForConfirmation() {
        this.status = RequestStatus.WAITING_CONFIRMATION
    }

    fun cancel() {
        this.status = RequestStatus.CANCELED
    }

    fun confirm() {
        this.status = RequestStatus.CONFIRMED
    }

}