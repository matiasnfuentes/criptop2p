package ar.edu.unq.criptop2p.model

import ar.edu.unq.criptop2p.AbstractTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UserTest : AbstractTest() {
    @Test
    fun getFirstName() {
        val aName = "Random first name"
        val aUser = factory.aUser(firstName = aName)

        assertEquals(aName, aUser.getFirstName())

    }

    @Test
    fun getLastName() {
        val aName = "Random last name"
        val aUser = factory.aUser(lastName = aName)

        assertEquals(aName, aUser.getLastName())
    }

    @Test
    fun getEmail() {
        val anEmail = "random@email.com"
        val aUser = factory.aUser(email = anEmail)

        assertEquals(anEmail, aUser.getEmail())
    }

    @Test
    fun getPassword() {
        val aPassword = "random password"
        val aUser = factory.aUser(password = aPassword)

        assertEquals(aPassword, aUser.getPassword())
    }

    @Test
    fun getCvu() {
        val aCvu = "1234567890123456789012"
        val aUser = factory.aUser(cvu = aCvu)

        assertEquals(aCvu, aUser.getCvu())
    }

    @Test
    fun getWalletAddress() {
        val aWalletAddress = "12345678"
        val aUser = factory.aUser(walletAddress = aWalletAddress)

        assertEquals(aWalletAddress, aUser.getWalletAddress())
    }

    @Test
    fun getAddress() {
        val anAddress = factory.anAddress()
        val aUser = factory.aUser(address = anAddress)

        Assertions.assertEquals(anAddress, aUser.getAddress())
    }

    @Test
    fun getId() {
        val anId = 123L
        val aUser = factory.aUser()

        aUser.setId(anId)

        assertEquals(anId, aUser.getId())
    }

    @Test
    fun userPasswordChangesAfterEncondingIt() {
        val aPassword = "random password"
        val aUser = factory.aUser(password = aPassword)

        aUser.encodePassword()

        assertNotEquals(aPassword, aUser.getPassword())
    }

    @Test
    fun comparingTheSamePasswordReturnsTrue() {
        val aPassword = "random password"
        val aUser = factory.aUser(password = aPassword)

        aUser.encodePassword()

        assert(aUser.comparePassword(aPassword))
    }

    @Test
    fun comparingADifferentPasswordReturnsFalse() {
        val aPassword = "random password"
        val anotherPassword = "another password"
        val aUser = factory.aUser(password = aPassword)

        aUser.encodePassword()

        assertFalse(aUser.comparePassword(anotherPassword))
    }

    @Test
    fun whenAUserIsCreatedItHasNoReputation() {
        val aUser = factory.aUser()

        assertEquals(0, aUser.getReputation())
    }

    @Test
    fun userReputationCanBeIncreased() {
        val aUser = factory.aUser()
        val amountToIncrease = 10
        val expectedReputation = aUser.getReputation() + amountToIncrease

        aUser.increaseReputation(amountToIncrease)

        assertEquals(expectedReputation, aUser.getReputation())
    }

    @Test
    fun userReputationCanBeDecreased() {
        val aUser = factory.aUser(reputation = 20)
        val amountToDecrease = 10
        val expectedReputation = aUser.getReputation() - amountToDecrease

        aUser.decreaseReputation(amountToDecrease)

        assertEquals(expectedReputation, aUser.getReputation())
    }

    @Test
    fun userReputationCannotBeDecreasedBelowZero() {
        val aUser = factory.aUser(reputation = 10)
        val amountToDecrease = 20
        val expectedReputation = 0

        aUser.decreaseReputation(amountToDecrease)

        assertEquals(expectedReputation, aUser.getReputation())
    }

    @Test
    fun whenAUserIsCreatedItHasNoTransactions() {
        val aUser = factory.aUser()

        assertEquals(0, aUser.getTotalTransactions())
    }

    @Test
    fun userTotalTransanctionsCanBeIncreased() {
        val aUser = factory.aUser()

        aUser.increaseTotalTransactionsByOne()

        assertEquals(1, aUser.getTotalTransactions())
    }



}