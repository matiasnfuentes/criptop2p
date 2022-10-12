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
    private val timeStamp: Date = Date()
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
    fun getTimeStamp(): Date = this.timeStamp
    fun getId(): Long? = this.id
    fun getCounterpart(): User? = this.counterpart
    fun getPriceARS(dollarPrice: Double): Double = this.amount * this.cryptoCurrency.getPrice() * dollarPrice

    fun setStatus(status : RequestStatus) { this.status = status}
    fun setCounterpart(counterpart : User) { this.counterpart = counterpart}

    fun updateStatus(nextStatus: RequestStatus, requester: User? = null, currentPrice: Double = 0.0) {
        this.status.updateStatus(this, nextStatus, requester, currentPrice)
    }

}