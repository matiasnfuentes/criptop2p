package ar.edu.unq.criptop2p.controller.dto

import java.util.*

class TotalTransactionReportDTO ( val totalUSD:Double,
                                  val totalARG:Double,
                                  val currencyDetail:List<CurrencyTransactionReportDTO>,
                                  val timestamp:Date = Date()
)