package ar.edu.unq.criptop2p.model

class BuyRequest(cryptoCurrency: CryptoCurrency,
                 priceLimit: Double,
                 amount: Int,
                 user: User) : Request(cryptoCurrency, priceLimit, amount, user) {

}