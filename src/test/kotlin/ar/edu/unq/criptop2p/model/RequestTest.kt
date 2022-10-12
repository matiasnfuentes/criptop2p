package ar.edu.unq.criptop2p.model

import ar.edu.unq.criptop2p.AbstractTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

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
    fun getOwner() {
        val aUser = factory.aUser()
        val aRequest = factory.aRequest(owner = aUser)

        assertEquals(aUser, aRequest.getOwner())
    }

    @Test
    fun getType() {
        val aRequestType = RequestType.SELL
        val aRequest = factory.aRequest(type = aRequestType)

        assertEquals(aRequestType, aRequest.getType())

    }

    @Test
    fun `request default status is AVAILABLE`() {
        val requestDefaultStatus = RequestStatus.AVAILABLE
        val aRequest = factory.aRequest()

        assertEquals(requestDefaultStatus, aRequest.getStatus())
    }

    @Test
    fun `get price ARS  returns dollar price multiplied by the crypto currency amount and price`() {
        val aCryptoCurrency = factory.aCryptoCurrency()
        val aRequest = factory.aRequest(cryptoCurrency = factory.aCryptoCurrency())
        val dollarPrice = 300.0

        val expectedPrice = aCryptoCurrency.getPrice() * aRequest.getAmount() * dollarPrice

        assertEquals(expectedPrice, aRequest.getPriceARS(dollarPrice))
    }

    /* ------------------------- */
    /* REQUEST STATUS = AVAILABLE */
    /* ------------------------- */

    @Test
    fun `The request status is changed to ACCEPTED when it's accepted by a user`() {
        val counterpart = factory.aUser(id = 1)
        val aRequest = factory.aRequest()

        aRequest.updateStatus(RequestStatus.ACCEPTED, counterpart)

        assertEquals(RequestStatus.ACCEPTED, aRequest.getStatus())
    }

    @Test
    fun `The request counterpart is set when it's accepted by a user`() {
        val counterpart = factory.aUser(id = 1)
        val aRequest = factory.aRequest()

        aRequest.updateStatus(RequestStatus.ACCEPTED, counterpart)

        assertEquals(counterpart, aRequest.getCounterpart())
    }

    @Test
    fun `A request cannot be accepted by its owner`() {
        val aRequest = factory.aRequest()
        val owner = aRequest.getOwner()

        assertThrows<StatusException> { aRequest.updateStatus(RequestStatus.ACCEPTED, owner) }
    }

    @Test
    fun `It should be a counterpart to accept the request`() {
        val aRequest = factory.aRequest()

        assertThrows<StatusException> { aRequest.updateStatus(RequestStatus.ACCEPTED, null) }
    }

    @Test
    fun `An available request cannot be updated to WAITING_CONFIRMATION,CONFIRMED or AVAILABLE`() {
        val counterpart = factory.aUser(id = 1)
        val aRequest = factory.aRequest()
        val invalidStatuses = listOf(
            RequestStatus.WAITING_CONFIRMATION,
            RequestStatus.CONFIRMED,
            RequestStatus.AVAILABLE
        )

        invalidStatuses.forEach { assertThrows<StatusException> { aRequest.updateStatus(it, counterpart) } }
    }

    @Test
    fun `An available request can be canceled`() {
        val aRequest = factory.aRequest()

        aRequest.updateStatus(RequestStatus.CANCELED, null)

        assertEquals(RequestStatus.CANCELED, aRequest.getStatus())
    }


    /* ------------------------- */
    /* REQUEST STATUS = ACCEPTED */
    /* ------------------------- */

    @Test
    fun `An accepted request can be updated to WAITING_CONFIRMATION by its counterpart`() {
        val anAcceptedRequest = factory.anAcceptedRequest()
        val counterpart = anAcceptedRequest.getCounterpart()

        anAcceptedRequest.updateStatus(RequestStatus.WAITING_CONFIRMATION, counterpart)

        assertEquals(RequestStatus.WAITING_CONFIRMATION, anAcceptedRequest.getStatus())
    }

    @Test
    fun `An accepted request cannot be updated to WAITING_CONFIRMATION by its owner`() {
        val anAcceptedRequest = factory.anAcceptedRequest()
        val owner = anAcceptedRequest.getOwner()

        assertThrows<StatusException> { anAcceptedRequest.updateStatus(RequestStatus.WAITING_CONFIRMATION, owner) }
    }

    @Test
    fun `An accepted buy request is canceled when is trying to being updated to  WAITING_CONFIRMATION if the crypto current price is 5 percent above the price limit`() {
        val aCryptoCurrency = factory.aCryptoCurrency(10.0)
        val anAcceptedRequest = factory.anAcceptedRequest(type = RequestType.BUY, cryptoCurrency = aCryptoCurrency)
        val counterpart = anAcceptedRequest.getCounterpart()
        val currentPrice = 15.0

        anAcceptedRequest.updateStatus(RequestStatus.WAITING_CONFIRMATION, counterpart, currentPrice)

        assertEquals(RequestStatus.CANCELED, anAcceptedRequest.getStatus())
    }

    @Test
    fun `An accepted sell request is canceled when is trying to being updated to WAITING_CONFIRMATION if the crypto current price is 5 percent below the price limit`() {
        val aCryptoCurrency = factory.aCryptoCurrency(10.0)
        val anAcceptedRequest = factory.anAcceptedRequest(type = RequestType.SELL, cryptoCurrency = aCryptoCurrency)
        val counterpart = anAcceptedRequest.getCounterpart()
        val currentPrice = 8.0

        anAcceptedRequest.updateStatus(RequestStatus.WAITING_CONFIRMATION, counterpart, currentPrice)

        assertEquals(RequestStatus.CANCELED, anAcceptedRequest.getStatus())
    }

    @Test
    fun `An accepted request cannot be updated to ACCEPTED,CONFIRMED or AVAILABLE`() {
        val anAcceptedRequest = factory.anAcceptedRequest()
        val counterpart = anAcceptedRequest.getCounterpart()
        val invalidStatuses = listOf(
            RequestStatus.ACCEPTED,
            RequestStatus.CONFIRMED,
            RequestStatus.AVAILABLE
        )

        invalidStatuses.forEach { assertThrows<StatusException> { anAcceptedRequest.updateStatus(it, counterpart) } }
    }

    @Test
    fun `An accepted request can be canceled by the owner`() {
        val aRequest = factory.aRequest()

        aRequest.updateStatus(RequestStatus.CANCELED, aRequest.getOwner())

        assertEquals(RequestStatus.CANCELED, aRequest.getStatus())
    }

    @Test
    fun `An accepted request can be canceled by the counterpart`() {
        val aRequest = factory.aRequest()

        aRequest.updateStatus(RequestStatus.CANCELED, aRequest.getCounterpart())

        assertEquals(RequestStatus.CANCELED, aRequest.getStatus())
    }

    @Test
    fun `When a request is canceled by the owner its reputation is decreased by 20`() {
        val owner = factory.aUser(id = 0, reputation = 50)
        val aRequest = factory.aRequest(owner = owner)
        val expectedReputation = owner.getReputation() - 20

        aRequest.updateStatus(RequestStatus.CANCELED, owner)

        assertEquals(expectedReputation, owner.getReputation())
    }

    @Test
    fun `When a request is canceled by the counterpart its reputation is decreased by 20`() {
        val counterpart = factory.aUser(id = 0, reputation = 50)
        val aRequest = factory.aRequest(counterpart = counterpart)
        val expectedReputation = counterpart.getReputation() - 20

        aRequest.updateStatus(RequestStatus.CANCELED, counterpart)

        assertEquals(expectedReputation, counterpart.getReputation())
    }

    /* ------------------------- */
    /* REQUEST STATUS = WAITING_CONFIRMATION */
    /* ------------------------- */

    @Test
    fun `An request can be confirmed by its owner`() {
        val aWaitingRequest = factory.aWaitingRequest()
        val owner = aWaitingRequest.getOwner()

        aWaitingRequest.updateStatus(RequestStatus.CONFIRMED, owner)

        assertEquals(RequestStatus.CONFIRMED, aWaitingRequest.getStatus())
    }

    @Test
    fun `An request cannot be confirmed by its counterpart`() {
        val aWaitingRequest = factory.aWaitingRequest()
        val counterpart = aWaitingRequest.getCounterpart()

        assertThrows<StatusException> { aWaitingRequest.updateStatus(RequestStatus.CONFIRMED, counterpart) }
    }

    @Test
    fun `WAITING_CONFIRMATION status cannot be updated to ACCEPTED,WAITING_CONFIRMATION, CANCELED or AVAILABLE`() {
        val aWaitingRequest = factory.aWaitingRequest()
        val owner = aWaitingRequest.getOwner()
        val counterpart = aWaitingRequest.getCounterpart()

        val invalidStatuses = listOf(
            RequestStatus.ACCEPTED,
            RequestStatus.WAITING_CONFIRMATION,
            RequestStatus.AVAILABLE
        )

        invalidStatuses.forEach { assertThrows<StatusException> { aWaitingRequest.updateStatus(it, owner) } }
        invalidStatuses.forEach { assertThrows<StatusException> { aWaitingRequest.updateStatus(it, counterpart) } }
    }

    @Test
    fun `When a request its confirmed, the owner transactions are increased by one`() {
        val aWaitingRequest = factory.aWaitingRequest()
        val owner = aWaitingRequest.getOwner()

        aWaitingRequest.updateStatus(RequestStatus.CONFIRMED, owner)

        assertEquals(1, owner.getTotalTransactions())
    }

    @Test
    fun `When a request its confirmed in less than 30 minutes from its creation, the owner reputation is increased by ten`() {
        val aWaitingRequest = factory.aWaitingRequest()
        val owner = aWaitingRequest.getOwner()
        val expectedReputation = owner.getReputation() + 10

        aWaitingRequest.updateStatus(RequestStatus.CONFIRMED, owner)

        assertEquals(expectedReputation, owner.getReputation())
    }

    @Test
    fun `When a request its confirmed in more than 30 minutes from its creation, the owner reputation is increased by five`() {
        val currentDateMinusThirtyOneMinutes = Date(System.currentTimeMillis() - 31 * 60 * 1000)
        val aWaitingRequest = factory.aWaitingRequest(timestamp = currentDateMinusThirtyOneMinutes)
        val owner = aWaitingRequest.getOwner()
        val expectedReputation = owner.getReputation() + 5

        aWaitingRequest.updateStatus(RequestStatus.CONFIRMED, owner)

        assertEquals(expectedReputation, owner.getReputation())
    }

    /* ------------------------- */
    /* REQUEST STATUS = CONFIRMED */
    /* ------------------------- */

    @Test
    fun `A confirmed request status cannot be changed`() {
        val aConfirmedRequest = factory.aConfirmedRequest()
        val counterpart = aConfirmedRequest.getCounterpart()
        val owner = aConfirmedRequest.getOwner()
        val invalidStatuses = listOf(
            RequestStatus.ACCEPTED,
            RequestStatus.CANCELED,
            RequestStatus.CONFIRMED,
            RequestStatus.AVAILABLE
        )

        invalidStatuses.forEach { assertThrows<StatusException> { aConfirmedRequest.updateStatus(it, counterpart) } }
        invalidStatuses.forEach { assertThrows<StatusException> { aConfirmedRequest.updateStatus(it, owner) } }
    }




}