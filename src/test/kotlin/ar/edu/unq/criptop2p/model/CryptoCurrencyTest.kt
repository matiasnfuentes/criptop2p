package ar.edu.unq.criptop2p.model

import ar.edu.unq.criptop2p.AbstractTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class CryptoCurrencyTest: AbstractTest() {

    @Test
    fun getPrice() {
        val aPrice = 10.0
        val aCryptoCurrency = factory.aCryptoCurrency(price = aPrice)

        assertEquals(aPrice, aCryptoCurrency.getPrice())
    }

    @Test
    fun getSymbol() {
        val aCryptoSymbol = "ALICEUSDT"
        val aCryptoCurrency = factory.aCryptoCurrency(symbol = aCryptoSymbol)

        assertEquals(aCryptoSymbol, aCryptoCurrency.getSymbol())
    }

    @Test
    fun getTimeStamp() {
        val aDate = Date()
        val aCryptoCurrency = factory.aCryptoCurrency(date = aDate)

        assertEquals(aDate, aCryptoCurrency.getTimestamp())
    }

    @Test
    fun checkingAValidSymbol() {
        val aValidCryptoSymbol = "ALICEUSDT"

        assert(CryptoCurrency.isAValidCryptoSymbol(aValidCryptoSymbol))
    }

    @Test
    fun chekingAnInvalidSymbol() {
        val anInvalidCryptoSymbol = "INVALIDUSDT"

        assertFalse( CryptoCurrency.isAValidCryptoSymbol(anInvalidCryptoSymbol))
    }
}