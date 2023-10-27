package com.cugocumhurgunay.foodmenucreatorsql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class YemekDatabase(context: Context?) :
    SQLiteOpenHelper(context, "Yemekmenu.db", null, 1 )
{
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("Create Table Kategoriler(ID Integer PRIMARY KEY AUTOINCREMENT, Aciklama TEXT);")
        db!!.execSQL("Create Table Yemekler(ID Integer PRIMARY KEY AUTOINCREMENT, KategoriID Integer, Adi Text, YAciklama Text, Fiyati Integer);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}