package ar.edu.unq.criptop2p.controller

import ar.edu.unq.criptop2p.AbstractTest
import ar.edu.unq.criptop2p.controller.dto.LoginDTO
import ar.edu.unq.criptop2p.controller.dto.UserDTO
import ar.edu.unq.criptop2p.model.Address
import ar.edu.unq.criptop2p.persistance.UserRepository
import ar.edu.unq.criptop2p.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles(profiles = ["test"])
internal class UserControllerTest(
    @Autowired
    private val mvc: MockMvc,
    @Autowired
    private val userController: UserController,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val userService: UserService
) : AbstractTest() {

    private val aUserDTO = this.factory.aUserDTO()

    private val aLoginDTO = LoginDTO(aUserDTO.email, aUserDTO.password)

    @Test
    fun contextLoads() {
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    fun register() {
        val mvcPerform = mvc.perform(
            MockMvcRequestBuilders
                .post("/api/user/register")
                .content(ObjectMapper().writeValueAsString(aUserDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        val aSavedUser = userRepository.findByEmail(aUserDTO.email)

        mvcPerform.andExpect(status().isOk)
        assert(aSavedUser != null)
        assert(aSavedUser!!.getId() != null)
        assert(aSavedUser.getFirstName() == aUserDTO.firstName)
        assert(aSavedUser.getLastName() == aUserDTO.lastName)
        assert(aSavedUser.getCvu() == aUserDTO.cvu)
        assert(aSavedUser.getWalletAddress() == aUserDTO.walletAddress)
        assert(aSavedUser.getAddress().getId() != null)
        assert(aSavedUser.getAddress().getStreet() == aUserDTO.address.getStreet())
        assert(aSavedUser.getAddress().getNumber() == aUserDTO.address.getNumber())
        assert(aSavedUser.getAddress().getCity() == aUserDTO.address.getCity())

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    fun login(){
        userController.register(aUserDTO)

        val mvcPerform = mvc.perform( MockMvcRequestBuilders
                .post("/api/user/login")
                .content( ObjectMapper().writeValueAsString(aLoginDTO) )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andReturn()

        assertDoesNotThrow { userService.authenticate(mvcPerform.response.contentAsString) }
        assert(userService.authenticate(mvcPerform.response.contentAsString)!!.getEmail() == aLoginDTO.email)

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    fun getUserList(){
        userController.register(aUserDTO)

        mvc.perform( MockMvcRequestBuilders
            .get("/api/user/list")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[*].firstName").value(Matchers.hasItem(aUserDTO.firstName)))
            .andExpect(jsonPath("$[*].lastName").value(Matchers.hasItem(aUserDTO.lastName)))
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    fun `Cannot register the same email twice`(){
        userController.register(aUserDTO)

         mvc.perform(MockMvcRequestBuilders
            .post("/api/user/register")
            .content( ObjectMapper().writeValueAsString(aUserDTO) )
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError)
            .andExpect(status().reason(Matchers.containsString("Email already registered")))

    }

    @Test
    fun `Login - User do not exist`(){
        mvc.perform(MockMvcRequestBuilders
                .post("/api/user/login")
                .content( ObjectMapper().writeValueAsString(LoginDTO("unregisteredemail@fakedomain.ar", "Pass$12345")) )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
                .andExpect(status().reason(Matchers.containsString("User does not exist")))

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    fun `Login - Bad password`(){
        userController.register(aUserDTO)

        mvc.perform(MockMvcRequestBuilders
                .post("/api/user/login")
                .content( ObjectMapper().writeValueAsString(LoginDTO(aUserDTO.email, aUserDTO.password + "$$")) )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
                .andExpect(status().reason(Matchers.containsString("Password does not match")))

    }

}