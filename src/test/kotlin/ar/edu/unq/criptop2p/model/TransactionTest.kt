package ar.edu.unq.criptop2p.model

import ar.edu.unq.criptop2p.AbstractTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TransactionTest : AbstractTest() {

    @Test
    fun amountOperated() {
        val aUserOwner = factory.aUser(id=1)
        val aUserCounterpart = factory.aUser(id=2)
        val confirmedRequestBuy = factory.aConfirmedRequest(amount = 50.0,
                                                            owner = aUserOwner,
                                                            counterpart = aUserCounterpart)
        val confirmedRequestSell = factory.aConfirmedRequest(amount = 10.0,
                                                             type = RequestType.SELL,
                                                             owner = aUserOwner,
                                                             counterpart = aUserCounterpart)

        assertEquals(50.0 , confirmedRequestBuy.getAmount())
        assertEquals(10.0, confirmedRequestSell.getAmount())

    }

    @Test
    fun priceOperated() {
        val aUserOwner = factory.aUser(id=1)
        val aUserCounterpart = factory.aUser(id=2)
        val aCryptoCurrency = factory.aCryptoCurrency(price = 10.0)
        val confirmedRequestBuy = factory.aConfirmedRequest(cryptoCurrency = aCryptoCurrency,
                                                            amount = 50.0,
                                                            owner = aUserOwner,
                                                            counterpart = aUserCounterpart,
                                                            priceArgAtCompletation = 200.0)
        val confirmedRequestSell = factory.aConfirmedRequest(cryptoCurrency = aCryptoCurrency,
                                                             amount = 10.0,
                                                             type = RequestType.SELL,
                                                             owner = aUserOwner,
                                                             counterpart = aUserCounterpart,
                                                             priceArgAtCompletation = 200.0)


        assertEquals(Pair(500.0, 100000.0), confirmedRequestBuy.priceOperated())
        assertEquals(Pair(100.0, 20000.0), confirmedRequestSell.priceOperated())
    }

}