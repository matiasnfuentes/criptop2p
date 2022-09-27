package ar.edu.unq.criptop2p

import ar.edu.unq.criptop2p.model.*
import java.util.*

class TestFactory {

    fun anAddress(street: String = "", number: Int = 0, city: String = ""): Address = Address(street, number, city)

    fun aUser(
        firstName: String = "",
        lastName: String = "",
        email: String = "",
        password: String = "",
        cvu: String = "",
        walletAddress: String = "",
        address: Address = anAddress()
    ): User = User(firstName, lastName, email, password, cvu, walletAddress, address)


    fun aCryptoCurrency(price: Double = 0.0, symbol: String = "", date: Date = Date()) =
        CryptoCurrency(price, symbol, date)

    fun aRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        priceLimit: Double = 0.0,
        amount: Double = 0.0,
        user: User = aUser(),
        type: RequestType = RequestType.BUY
    ) = Request(cryptoCurrency, priceLimit, amount, user, type)

    fun aBuyRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        priceLimit: Double = 0.0,
        amount: Double = 0.0,
        user: User = aUser(),
    ) = aRequest(cryptoCurrency, priceLimit, amount, user, RequestType.BUY)

    fun aSellRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        priceLimit: Double = 0.0,
        amount: Double = 0.0,
        user: User = aUser(),
    ) = aRequest(cryptoCurrency, priceLimit, amount, user, RequestType.SELL)

    fun aTransaction(
        buyRequest: Request = aBuyRequest(),
        sellRequest: Request = aSellRequest()
    ) = Transaction(buyRequest, sellRequest)
}