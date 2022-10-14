package ar.edu.unq.criptop2p.controller.dto

import java.util.*

class TotalTransactionReportDTO ( val totalUSD:Long,
                                  val totalARG:Long,
                                  val currencyDetail:List<CurrencyTransactionReportDTO>,
                                  val timestamp:Date = Date()
)