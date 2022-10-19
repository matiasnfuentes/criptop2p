package ar.edu.unq.criptop2p.model

import java.util.*

enum class RequestStatus {
    AVAILABLE {
        override fun processUpdate(
            request: Request,
            newStatus: RequestStatus,
            requester: User?,
            currentPrice: Double
        ) {
            if (request.getOwner()
                    .getId() == requester?.getId()
            ) throw StatusException("The owner cannot pick its own Request")
            if (newStatus != ACCEPTED) throw StatusException("The request must be accepted to change its status")
            if (requester == null) throw StatusException("The requester does not exist")
            request.setCounterpart(requester)
        }
    },
    ACCEPTED {
        override fun processUpdate(
            request: Request,
            newStatus: RequestStatus,
            requester: User?,
            currentPrice: Double
        ) {
            if(request.getType().exceedsPriceGap(currentPrice, request)){
                throw PriceException()
            }

            if (request.getCounterpart()
                    ?.getId() != requester?.getId() || newStatus != WAITING_CONFIRMATION
            ) throw StatusException("The request status is ACCEPTED, it can only be changed to WAITING_CONFIRMATION by the counterpart ")
        }

        override fun updateStatus(
            request: Request,
            newStatus: RequestStatus,
            requester: User?,
            currentPrice: Double
        ){
            try {
                super.updateStatus(request, newStatus, requester, currentPrice)
            } catch (e : PriceException){
                super.updateStatus(request, CANCELED, null, 0.0)
            }

        }
    },
    WAITING_CONFIRMATION {
        override fun processUpdate(
            request: Request,
            newStatus: RequestStatus,
            requester: User?,
            currentPrice: Double
        ) {
            val owner = request.getOwner()
            if (owner.getId() != requester?.getId() || newStatus != CONFIRMED
            ) throw StatusException("The request status is WAITING_CONFIRMATION, and it can only be changed to CONFIRMED by the owner ")

            request.setFinishedTimeStamp()
            owner.increaseTotalTransactionsByOne()
            val timeElapsedInMinutes = (Date().time - request.getCreationTimeStamp().time) / 1000 / 60
            owner.increaseReputation(if (timeElapsedInMinutes < 30) 10 else 5)
        }
    },
    CONFIRMED {
        override fun processUpdate(
            request: Request,
            newStatus: RequestStatus,
            requester: User?,
            currentPrice: Double
        ) {
            throw StatusException("The request is CONFIRMED. It's status cannot be changed.")
        }
    },
    CANCELED {
        override fun processUpdate(
            request: Request,
            newStatus: RequestStatus,
            requester: User?,
            currentPrice: Double

        ) {
            throw StatusException("The request is CANCELED. It's status cannot be changed.")
        }
    };

    abstract fun processUpdate(
        request: Request,
        newStatus: RequestStatus,
        requester: User? = null,
        currentPrice: Double = 0.0
    )

    private fun cancel(
        request: Request,
        requester: User?
    ) {
        if (this == AVAILABLE || this == ACCEPTED) {
            request.setStatus(CANCELED)
            request.setFinishedTimeStamp()
            requester?.decreaseReputation(20)
        } else {
            throw StatusException("The request cannot be canceled at this point")
        }
    }

    open fun updateStatus(
        request: Request,
        newStatus: RequestStatus,
        requester: User? = null,
        currentPrice: Double = 0.0
    ) {
        if (newStatus == CANCELED) return this.cancel(request, requester)

        this.processUpdate(request, newStatus, requester, currentPrice)
        request.setStatus(newStatus)
    }

}

enum class RequestType {
    BUY {
        override fun exceedsPriceGap(currentPrice: Double, request: Request) : Boolean =
            currentPrice > (request.getCryptoCurrency().getPrice() * 1.05)

    },
    SELL {
        override fun exceedsPriceGap(currentPrice: Double, request: Request) : Boolean =
            currentPrice < (request.getCryptoCurrency().getPrice() * 0.95)

        override fun getAmount(request: Request): Double = - request.getAmount()

    };

    abstract fun exceedsPriceGap(currentPrice: Double, request: Request) : Boolean

    private fun getFor(user:User, request:Request, value:Double) =
            if (user.getId() == request.getOwner().getId()) value else - value

    open fun getAmount(request: Request): Double = request.getAmount()
    fun amountOperated(request: Request, user: User) = getFor(user, request, getAmount(request))

    private fun usdOperated(request: Request) = getAmount(request) * request.getCryptoCurrency().getPrice()
    fun priceOperated(request: Request, user: User): Pair<Double, Double> {
        val usdOperated = getFor(user, request, usdOperated(request))
        return Pair(usdOperated, usdOperated * request.getPriceArgAtCompletation())
    }

}