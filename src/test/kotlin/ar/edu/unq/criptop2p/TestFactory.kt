package ar.edu.unq.criptop2p

import ar.edu.unq.criptop2p.controller.dto.UserDTO
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
        timestamp: Date = Date(),
        finishedTimeStamp: Date = Date()
    ) = Request(cryptoCurrency, amount, owner, type, status, counterpart, timestamp, finishedTimeStamp)

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
    ) = Request(
        cryptoCurrency,
        amount,
        aUser(id = 0),
        type,
        RequestStatus.WAITING_CONFIRMATION,
        aUser(id = 1),
        timestamp
    )

    fun aConfirmedRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        amount: Double = 10.0,
        owner: User = aUser(id = 0),
        type: RequestType = RequestType.BUY,
        counterpart: User = aUser(id = 1),
        timestamp: Date = Date(),
        finishedTimeStamp: Date = Date(),
        priceArgAtCompletation: Double? = null
    ) = Request(
        cryptoCurrency,
        amount,
        owner,
        type,
        RequestStatus.CONFIRMED,
        counterpart,
        timestamp,
        finishedTimeStamp,
        priceArgAtCompletation
    )

    fun aCanceledRequest(
        cryptoCurrency: CryptoCurrency = aCryptoCurrency(),
        amount: Double = 10.0,
        owner: User = aUser(id = 0),
        type: RequestType = RequestType.BUY,
        counterpart: User = aUser(id = 1),
        timestamp: Date = Date(),
        finishedTimeStamp: Date = Date(),
        priceArgAtCompletation: Double? = null
    ) = Request(
        cryptoCurrency,
        amount,
        owner,
        type,
        RequestStatus.CANCELED,
        counterpart,
        timestamp,
        finishedTimeStamp,
        priceArgAtCompletation
    )

    fun aUserDTO() = UserDTO(
        "testFirstName",
        "testLastName",
        "test123@email.com",
        "AsDtest456$",
        "1234567890123456789015",
        "12345678",
        Address(
            "Street",
            401,
            "Avellaneda"
        )
    )

}