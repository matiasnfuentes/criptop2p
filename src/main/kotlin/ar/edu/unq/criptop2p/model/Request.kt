package ar.edu.unq.criptop2p.model

import java.util.*

class Request(private val cryptoCurrency: CryptoCurrency,
                       private val priceLimit: Double,
                       private val amount: Int,
                       private val user: User,
                       private val type: RequestType) {

    private var status: RequestStatus
    private val timeStamp: Date
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

    fun getAmount(): Int{
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