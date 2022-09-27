package ar.edu.unq.criptop2p.model

import ar.edu.unq.criptop2p.AbstractTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TransactionTest : AbstractTest() {

    @Test
    fun getBuyRequest() {
        val aBuyRequest = factory.aBuyRequest()
        val aRequest = factory.aTransaction(buyRequest = aBuyRequest)

        assertEquals(aBuyRequest, aRequest.getBuyRequest())
    }

    @Test
    fun getSellRequest() {
        val aSellRequest = factory.aSellRequest()
        val aRequest = factory.aTransaction(sellRequest = aSellRequest)

        assertEquals(aSellRequest, aRequest.getSellRequest())
    }

    @Test
    fun getTimeStamp() {
        //TODO: Find a way to test it
    }
}