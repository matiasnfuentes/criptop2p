package ar.edu.unq.criptop2p.model

import org.hibernate.validator.constraints.Range
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table
class Address(
    @field:Size(min = 3, max = 30, message = "street length should be min 3 max 30")
    @Column
    private var street: String,
    @field:Range(min = 1, max = 999999, message = "number should not have more than 6 digits")
    @Column
    private var number: Int,
    @field:Size(min = 3, max = 30, message = "city length should be min 3 max 30")
    @Column
    private var city: String
) {

    @Id
    @SequenceGenerator(
        name = "Address_sequence",
        sequenceName = "Address_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "Address_sequence"
    )
    private var id: Long? = null

    fun getStreet(): String = this.street
    fun getNumber(): Int = this.number
    fun getCity(): String = this.city

    fun getId(): Long? = this.id

}