package ar.edu.unq.criptop2p.model

import java.time.LocalDate

abstract class Request(private var cryptoCurrency: CryptoCurrency,
                       private var priceLimit: Double,
                       private var amount: Int,
                       private var user: User) {

    private var status: RequestStatus
    private var timeStamp: LocalDate
    private var score: Int

    init {
        this.status = RequestStatus.AVAILABLE
        this.timeStamp = LocalDate.now()
        this.score = 0
    }

    fun priceARG(): Double{
        //TODO: implement api call get current ARG value
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

}