package com.example.paulina_stal_czw_9_30

data class CurrencyDetails(
    var tableName: String,
    var currencyCode: String,
    var rate: Double,
    var yesterdayRate: Double,
    var flag: Int = 0,
    ) {}
