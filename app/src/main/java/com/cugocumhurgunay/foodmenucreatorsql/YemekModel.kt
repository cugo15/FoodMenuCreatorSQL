package com.cugocumhurgunay.foodmenucreatorsql

data class Kategori(var K_ID:Int = 0, var K_Aciklama:String = "")

data class Yemek(
    var Y_ID:Int = 0,
    var Kategori_ID:Int = 0,
    var Adi:String = "",
    var Y_Aciklama:String = "",
    var Fiyati:Double = 0.0
)
