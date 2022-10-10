package ar.edu.unq.criptop2p.controller.dto

data class DollarCurrency(val casa: Currency)

data class Currency(val compra: String, val venta: String, val nombre: String) {
    private fun formatPrice(price: String): Double {
        return try {
            price.replace(",", ".").toDouble()
        } catch (e: Exception) {
            0.0
        }
    }

    var sellPrice = formatPrice(venta)
    var buyPrice = formatPrice(compra)
    var name = nombre

}