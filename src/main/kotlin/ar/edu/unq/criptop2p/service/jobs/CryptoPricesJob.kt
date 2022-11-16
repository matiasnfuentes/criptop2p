package ar.edu.unq.criptop2p.service.jobs

import ar.edu.unq.criptop2p.model.CryptoCurrency
import ar.edu.unq.criptop2p.service.CacheService
import ar.edu.unq.criptop2p.service.CryptoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
@Profile(value = ["dev"])
class CurrenciesQuotationJob(@Autowired
                             private val cryptoService: CryptoService,
                             @Autowired
                             private val cacheService: CacheService) {


    @Scheduled(fixedDelay = 600000)
    protected fun updatePrices() {

        val currencyList: List<CryptoCurrency> = cryptoService.getCryptoPrices()
            ?: throw Exception("Prices couldn't be retrieved")

        cacheService.storeCurrencyPrices(currencyList)
    }
}