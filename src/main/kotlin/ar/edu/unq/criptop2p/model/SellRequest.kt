package ar.edu.unq.criptop2p.model

class SellRequest(cryptoCurrency: CryptoCurrency,
                  priceLimit: Double,
                  amount: Int,
                  user: User) : Request(cryptoCurrency, priceLimit, amount, user) {

}