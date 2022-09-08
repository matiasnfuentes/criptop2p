package ar.edu.unq.criptop2p.model

class User(private var firstName: String,
           private var lastName: String,
           private var email: String,
           private var password: String,
           private var cvu: String,
           private var walletAddress: String,
           private var address: Address) {

    fun reputation(): Int{
        //TODO: create reputation query
        return 0
    }

    fun totalTransactions(): Int{
        //TODO: create transactions query
        return 0
    }

}