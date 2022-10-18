package ar.edu.unq.criptop2p.model

import java.util.*
import javax.persistence.*

@Entity
class Request(
    @OneToOne(cascade = [CascadeType.ALL])
    private val cryptoCurrency: CryptoCurrency,
    @Column
    private val amount: Double,
    @ManyToOne(cascade = [CascadeType.REFRESH, CascadeType.MERGE])
    private val owner: User,
    @Column
    private val type: RequestType,
    @Column
    private var status: RequestStatus = RequestStatus.AVAILABLE,
    @OneToOne
    private var counterpart: User? = null,
    @Column
    private val creationTimeStamp: Date = Date(),
    @Column
    private var finishedTimeStamp: Date? = null,
    @Column
    private var priceArgAtCompletation: Double? = null
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


    fun getCryptoCurrency(): CryptoCurrency = this.cryptoCurrency
    fun getAmount(): Double = this.amount
    fun getOwner(): User = this.owner
    fun getType(): RequestType = this.type
    fun getStatus(): RequestStatus = this.status
    fun getCreationTimeStamp(): Date = this.creationTimeStamp
    fun getFinishedTimeStamp(): Date? = this.finishedTimeStamp
    fun getPriceArgAtCompletation(): Double = this.priceArgAtCompletation ?: 0.0
    fun getId(): Long? = this.id
    fun getCounterpart(): User? = this.counterpart
    fun getPriceARS(dollarPrice: Double): Double = this.amount * this.cryptoCurrency.getPrice() * dollarPrice

    fun setPriceArgAtCompletation(priceArg: Double) { if (this.priceArgAtCompletation == null) { this.priceArgAtCompletation = priceArg } }
    fun setStatus(status : RequestStatus) { this.status = status }
    fun setCounterpart(counterpart : User) { this.counterpart = counterpart }
    fun setFinishedTimeStamp() { if (this.finishedTimeStamp == null) { this.finishedTimeStamp = Date() } }

    fun updateStatus(nextStatus: RequestStatus, requester: User? = null, currentPrice: Double = 0.0) {
        this.status.updateStatus(this, nextStatus, requester, currentPrice)
    }

    fun amountOperated(user: User): Double = this.getType().amountOperated(this, user)
    fun priceOperated(user: User): Pair<Double, Double> = this.getType().priceOperated(this, user)

}