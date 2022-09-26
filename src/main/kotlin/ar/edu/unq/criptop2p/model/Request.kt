package ar.edu.unq.criptop2p.model

import java.util.*
import javax.persistence.*

@Entity
class Request( @OneToOne(cascade=[CascadeType.ALL])
               private val cryptoCurrency: CryptoCurrency,
               @Column
               private val priceLimit: Double,
               @Column
               private val amount: Double,
               @ManyToOne
               private val user: User,
               @Column
               private val type: RequestType) {

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
    private var status: RequestStatus
    @Column
    private val timeStamp: Date
    @Column
    private var score: Int

    init {
        this.status = RequestStatus.AVAILABLE
        this.timeStamp = Date()
        this.score = 0
    }

    fun priceARG(): Double{
        //TODO: implement api call to get current ARG value
        return 0.0
    }

    fun setAvailable(){
        this.status = RequestStatus.AVAILABLE
    }

    fun waitForConfirmation(){
        this.status = RequestStatus.WAITING_CONFIRMATION
    }

    fun cancel(){
        this.status = RequestStatus.CANCELED
    }

    fun confirm(){
        this.status = RequestStatus.CONFIRMED
    }

    fun getCryptoCurrency(): CryptoCurrency{
        return this.cryptoCurrency
    }

    fun getPriceLimit(): Double{
        return this.priceLimit
    }

    fun getAmount(): Double{
        return this.amount
    }

    fun getUser(): User{
        return this.user
    }
    fun getType(): RequestType{
        return this.type
    }

    fun getStatus(): RequestStatus{
        return this.status
    }

    fun getTimeStamp(): Date{
        return this.timeStamp;
    }

    fun getScore(): Int{
        return this.score
    }

}