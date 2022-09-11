package ar.edu.unq.criptop2p.model

import javax.persistence.*

@Entity
@Table(name="_User")
class User(@Column
           private var firstName: String,
           @Column
           private var lastName: String,
           @Column
           private var email: String,
           @Column
           private var password: String,
           @Column
           private var cvu: String,
           @Column
           private var walletAddress: String,
           @OneToOne
           private var address: Address) {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private var id: Long? = null

    fun reputation(): Int{
        //TODO: create reputation query
        return 0
    }

    fun totalTransactions(): Int{
        //TODO: create transactions query
        return 0
    }

}