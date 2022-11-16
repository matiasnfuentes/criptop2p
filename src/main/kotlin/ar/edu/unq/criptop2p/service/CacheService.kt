package ar.edu.unq.criptop2p.service
import ar.edu.unq.criptop2p.model.CryptoCurrency
import ar.edu.unq.criptop2p.service.properties.RedisProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

@Service
class CacheService(@Autowired
                   private val cryptoService: CryptoService,
                   @Autowired
                   private var redisProperties: RedisProperties) {

    private var pool: JedisPool? = null
    private var jedis: Jedis? = null

    fun getConnection(): Jedis? {
        pool = JedisPool(JedisPoolConfig(), redisProperties.host, redisProperties.port)
        jedis = pool!!.resource
        jedis!!.auth(redisProperties.auth)
        return jedis
    }

    fun destroyPool() {
            jedis?.close()
            pool?.destroy()
    }

    fun getCurrency(symbol: String): CryptoCurrency? {
        jedis = getConnection()
        val currency = jedis!![symbol]
        destroyPool()
        val objectMapper = ObjectMapper()
        return if (currency != null) {
            objectMapper.readValue(currency, CryptoCurrency::class.java)
        } else{
            null
        }
    }

    fun getCryptoPrices(): List<CryptoCurrency> = cryptoService.getSymbolList().mapNotNull { this.getCurrency(it) }

    fun storeCurrencyPrices(currencies: List<CryptoCurrency>) {
        jedis = getConnection()
        val mapper = ObjectMapper()
        currencies.forEach {
            jedis!![it.getSymbol()] = mapper.writeValueAsString(it)
        }
        destroyPool()
    }

    fun flushDB(){
        jedis = getConnection()
        jedis!!.flushDB();
        destroyPool()
    }
}