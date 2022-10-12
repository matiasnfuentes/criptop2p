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
        address: Address = anAddress(),
        reputation: Int = 0,
        totalTransactions: Int = 0,
        id: Long = 0
    ): User = User(firstName, lastName, email, password, cvu, walletAddress, address, reputation, totalTransactions, id)


    fun aCryptoCurrency(price: Double = 10.0, symbol: String = "", date: Date = Date()) =
        CryptoCurrency(price, symbol, date)

    fun aRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        amount: Double = 10.0,
        owner: User = aUser(id = 0),
        type: RequestType = RequestType.BUY,
        status: RequestStatus = RequestStatus.AVAILABLE,
        counterpart: User? = null,
        timestamp: Date = Date()
    ) = Request(cryptoCurrency, amount, owner, type, status, counterpart, timestamp)

    fun aBuyRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        amount: Double = 0.0,
        user: User = aUser(),
        status: RequestStatus = RequestStatus.AVAILABLE
    ) = aRequest(cryptoCurrency, amount, user, RequestType.BUY, status)

    fun aSellRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        amount: Double = 0.0,
        user: User = aUser(),
        status: RequestStatus = RequestStatus.AVAILABLE
    ) = aRequest(cryptoCurrency, amount, user, RequestType.SELL, status)

    fun anAcceptedRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        amount: Double = 10.0,
        type: RequestType = RequestType.BUY,
        timestamp: Date = Date()
    ) = Request(cryptoCurrency, amount, aUser(id = 0), type, RequestStatus.ACCEPTED, aUser(id = 1), timestamp)

    fun aWaitingRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        amount: Double = 10.0,
        type: RequestType = RequestType.BUY,
        timestamp: Date = Date()
    ) = Request(cryptoCurrency, amount, aUser(id = 0), type, RequestStatus.WAITING_CONFIRMATION, aUser(id = 1), timestamp)

    fun aConfirmedRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        amount: Double = 10.0,
        type: RequestType = RequestType.BUY,
        timestamp: Date = Date()
    ) = Request(cryptoCurrency, amount, aUser(id = 0), type, RequestStatus.CONFIRMED, aUser(id = 1), timestamp)

}