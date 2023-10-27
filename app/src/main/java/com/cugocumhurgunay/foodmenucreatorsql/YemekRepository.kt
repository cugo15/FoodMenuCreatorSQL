package com.cugocumhurgunay.foodmenucreatorsql

import android.content.ContentValues
import android.content.Context

class YemekRepository(c: Context)
{
    private var context:Context? = null
    private var yemekDB:YemekDatabase? = null

    init {
        context = c
        yemekDB = YemekDatabase(c)
    }

    fun YemekEkle(y:Yemek)
    {
        var db = yemekDB!!.writableDatabase

        var cv = ContentValues()
        cv.put("KategoriID", y.Kategori_ID)
        cv.put("Adi", y.Adi)
        cv.put("YAciklama", y.Y_Aciklama)
        cv.put("Fiyati", y.Fiyati)

        db.insert("Yemekler", null, cv)
        db.close()
    }

    fun getYemekler(): MutableList<Yemek> {

        val lst = mutableListOf<Yemek>()

        val db = yemekDB!!.readableDatabase

        val query = "SELECT * FROM Yemekler ORDER BY KategoriID"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            lst.add(
                Yemek(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4)
                )
            )
        }
        cursor.close()
        db.close()

        return lst
    }

    fun silYemek(foodId: Int) {
        val db = yemekDB!!.writableDatabase

        val selection = "ID = ?"
        val selectionArgs = arrayOf(foodId.toString())

        db.delete("Yemekler", selection, selectionArgs)

        db.close()
    }

    fun YemeklerByKategoriID(kid:Int) : MutableList<Yemek>
    {
        var lst = mutableListOf<Yemek>()

        var db = yemekDB!!.readableDatabase

        var cursor =  db.rawQuery("Select * from Yemekler Where KategoriID = ?", arrayOf(kid.toString()))

        while (cursor.moveToNext())
        {
            lst.add(
                Yemek(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4)
                )
            )
        }
        db.close()
        return lst
    }
}