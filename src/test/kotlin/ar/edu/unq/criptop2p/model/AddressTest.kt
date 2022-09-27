package ar.edu.unq.criptop2p.model

import ar.edu.unq.criptop2p.AbstractTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AddressTest : AbstractTest() {

    @Test
    fun getStreet() {
        val aStreetName = "Random street"
        val aStreet = factory.anAddress(street = aStreetName)

        assertEquals(aStreetName, aStreet.getStreet())

    }

    @Test
    fun getNumber() {
        val aStreetNumber = 5
        val aStreet = factory.anAddress(number = aStreetNumber)

        assertEquals(aStreetNumber, aStreet.getNumber())
    }

    @Test
    fun getCity() {
        val aCityName = "Random city"
        val aStreet = factory.anAddress(city = aCityName)

        assertEquals(aCityName, aStreet.getCity())
    }
}