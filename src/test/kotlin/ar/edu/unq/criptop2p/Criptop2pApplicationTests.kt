package ar.edu.unq.criptop2p

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles(profiles = ["test"])
class Criptop2pApplicationTests {

	@Test
	fun contextLoads() {
	}

}
