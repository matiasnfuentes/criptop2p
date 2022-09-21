package ar.edu.unq.criptop2p.controller

import ar.edu.unq.criptop2p.model.CryptoCurrency
import ar.edu.unq.criptop2p.service.CryptoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/crypto")
class CryptoController (
    @Autowired
    private val cryptoService: CryptoService){

    @GetMapping("/prices")
    fun prices(): ResponseEntity<List<CryptoCurrency>> {
        return ResponseEntity.ok().body(this.cryptoService.getCryptoPrices())
    }

    @GetMapping("/price/{symbol}")
    fun last24HsPrices(@PathVariable symbol:String): ResponseEntity<List<CryptoCurrency>> {
        return ResponseEntity.ok().body(this.cryptoService.getLast24HsPrices(symbol))
    }

}