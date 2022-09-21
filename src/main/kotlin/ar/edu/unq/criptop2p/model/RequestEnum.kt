package ar.edu.unq.criptop2p.model

enum class RequestStatus {
    AVAILABLE, WAITING_CONFIRMATION, CONFIRMED, CANCELED
}

enum class RequestType {
    BUY, SELL
}