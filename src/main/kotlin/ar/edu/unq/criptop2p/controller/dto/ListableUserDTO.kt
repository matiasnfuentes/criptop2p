package ar.edu.unq.criptop2p.controller.dto

import ar.edu.unq.criptop2p.model.User

data class ListableUserDTO(
    val firstName: String,
    val lastName: String,
    val reputationScore: Int,
    val operationCount: Int
) {
    companion object {
        // TODO: [CRIP-21] - Agregar reputación y cantidad de operaciones a la lista de usuarios
        fun fromUser(user: User): ListableUserDTO = ListableUserDTO(user.getFirstName(), user.getLastName(), 0, 0)
    }
}