package ar.edu.unq.criptop2p.model

import ar.edu.unq.criptop2p.AbstractTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class UserTest : AbstractTest() {

    @Test
    fun reputation() {
        //TODO: create reputation query
    }

    @Test
    fun totalTransactions() {
        //TODO: create transactions query
    }

    @Test
    fun getFirstName() {
        val aName = "Random first name"
        val aUser = factory.aUser(firstName = aName)

        Assertions.assertEquals(aName, aUser.getFirstName())

    }

    @Test
    fun getLastName() {
        val aName = "Random last name"
        val aUser = factory.aUser(lastName = aName)

        Assertions.assertEquals(aName, aUser.getLastName())
    }

    @Test
    fun getEmail() {
        val anEmail = "random@email.com"
        val aUser = factory.aUser(email = anEmail)

        Assertions.assertEquals(anEmail, aUser.getEmail())
    }

    @Test
    fun getPassword() {
        val aPassword = "random password"
        val aUser = factory.aUser(password = aPassword)

        Assertions.assertEquals(aPassword, aUser.getPassword())
    }

    @Test
    fun getCvu() {
        val aCvu = "1234567890123456789012"
        val aUser = factory.aUser(cvu = aCvu)

        Assertions.assertEquals(aCvu, aUser.getCvu())
    }

    @Test
    fun getWalletAddress() {
        val aWalletAddress = "12345678"
        val aUser = factory.aUser(walletAddress = aWalletAddress)

        Assertions.assertEquals(aWalletAddress, aUser.getWalletAddress())
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

        Assertions.assertEquals(anId, aUser.getId())
    }

    @Test
    fun userPasswordChangesAfterEncondingIt() {
        val aPassword = "random password"
        val aUser = factory.aUser(password = aPassword)

        aUser.encodePassword()

        Assertions.assertNotEquals(aPassword, aUser.getPassword())
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

        Assertions.assertFalse(aUser.comparePassword(anotherPassword))
    }


}