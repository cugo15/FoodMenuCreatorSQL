package com.cugocumhurgunay.foodmenucreatorsql

import android.content.ContentValues
import android.content.Context

class KategoriRepository(c: Context)
{
    private var context:Context? = null
    private var yemekDB:YemekDatabase? = null

    init {
        context = c
        yemekDB = YemekDatabase(c)
    }

    fun KategorileriOlustur()
    {
        var db = yemekDB!!.writableDatabase

        var cur = db.rawQuery("Select * from Kategoriler", null)

        if (cur.count == 0) {
            var cv = ContentValues()
            cv.put("Aciklama", "Menü")
            db.insert("Kategoriler", null, cv)

            cv = ContentValues()
            cv.put("Aciklama", "Aperatifler")
            db.insert("Kategoriler", null, cv)

            cv = ContentValues()
            cv.put("Aciklama", "Ana Yemekler")
            db.insert("Kategoriler", null, cv)

            cv = ContentValues()
            cv.put("Aciklama", "Makarnalar")
            db.insert("Kategoriler", null, cv)

            cv = ContentValues()
            cv.put("Aciklama", "Salatalar")
            db.insert("Kategoriler", null, cv)

            cv = ContentValues()
            cv.put("Aciklama", "Sokak Lezzetleri")
            db.insert("Kategoriler", null, cv)

            cv = ContentValues()
            cv.put("Aciklama", "Tatlılar")
            db.insert("Kategoriler", null, cv)

            cv = ContentValues()
            cv.put("Aciklama", "İçecekler")
            db.insert("Kategoriler", null, cv)
        }

        db.close()
    }

    fun GetKategoriler() : MutableList<Kategori>
    {
        var db = yemekDB!!.readableDatabase

        var cur = db.rawQuery("Select * from Kategoriler", null)

        var lst = mutableListOf<Kategori>()

        while (cur.moveToNext())
        {
            lst.add(
                Kategori(
                    cur.getInt(0),
                    cur.getString(1)))
        }

        return lst
    }
}