package ar.edu.unq.criptop2p.persistance

data class ListableUserDTO(
    val firstName: String,
    val lastName: String,
    val reputationScore: Int,
    val operationCount: Int
)