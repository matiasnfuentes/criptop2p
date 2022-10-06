package ar.edu.unq.criptop2p.model

import ar.edu.unq.criptop2p.AbstractTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RequestTest : AbstractTest() {

    @Test
    fun getCryptoCurrency() {
        val aCryptoCurrency = factory.aCryptoCurrency()
        val aRequest = factory.aRequest(cryptoCurrency = aCryptoCurrency)

        assertEquals(aCryptoCurrency, aRequest.getCryptoCurrency())
    }

    @Test
    fun getAmount() {
        val anAmount = 10.00
        val aRequest = factory.aRequest(amount = anAmount)

        assertEquals(anAmount, aRequest.getAmount())
    }

    @Test
    fun getUser() {
        val aUser = factory.aUser()
        val aRequest = factory.aRequest(user = aUser)

        assertEquals(aUser, aRequest.getUser())
    }

    @Test
    fun getType() {
        val aRequestType = RequestType.SELL
        val aRequest = factory.aRequest(type = aRequestType)

        assertEquals(aRequestType, aRequest.getType())

    }

    @Test
    fun getTimeStamp() {
        //TODO: Find a way to test it
    }

    @Test
    fun requestDefaultStatusIsAvailableByDefault() {
        val requestDefaultStatus = RequestStatus.AVAILABLE
        val aRequest = factory.aRequest()

        assertEquals(requestDefaultStatus, aRequest.getStatus())
    }

    @Test
    fun requestDefaultDateIsTheCurrentDateByDefault() {
        //TODO: Test default date
    }

    @Test
    fun requestDefaultScoreIsZeroByDefault() {
        val requestDefaultScore = 0
        val aRequest = factory.aRequest()

        assertEquals(requestDefaultScore, aRequest.getScore())
    }

    @Test
    fun priceARG() {
        //TODO: test once implemented
    }

    @Test
    fun waitForConfirmationChangesRequestStatus() {
        val waitingStatus = RequestStatus.WAITING_CONFIRMATION
        val aRequest = factory.aRequest()

        aRequest.waitForConfirmation()

        assertEquals(waitingStatus, aRequest.getStatus())
    }

    @Test
    fun cancelChangesRequestStatus() {
        val canceledStatus = RequestStatus.CANCELED
        val aRequest = factory.aRequest()

        aRequest.cancel()

        assertEquals(canceledStatus, aRequest.getStatus())
    }

    @Test
    fun confirmChangesRequestStatus() {
        val confirmedStatus = RequestStatus.CONFIRMED
        val aRequest = factory.aRequest()

        aRequest.confirm()

        assertEquals(confirmedStatus, aRequest.getStatus())
    }

}