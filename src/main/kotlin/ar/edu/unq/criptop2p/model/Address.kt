package ar.edu.unq.criptop2p.model

import javax.persistence.*

@Entity
@Table
class Address(@Column
              private var street: String,
              @Column
              private var number: Int,
              @Column
              private var city: String){

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

}