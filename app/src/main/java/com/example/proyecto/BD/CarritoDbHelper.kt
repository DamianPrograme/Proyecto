package com.example.proyecto.BD

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CarritoDbHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_CARRITO (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_NOMBRE TEXT NOT NULL," +
                    "$COL_TIPO TEXT," +
                    "$COL_PRECIO_CLP REAL," +
                    "$COL_CANTIDAD INTEGER NOT NULL" +
                    ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Por ahora tiramos la tabla y la recreamos
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CARRITO")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "proyecto.db"
        const val DATABASE_VERSION = 1

        const val TABLE_CARRITO = "carrito"
        const val COL_ID = "id"
        const val COL_NOMBRE = "nombre"
        const val COL_TIPO = "tipo"
        const val COL_PRECIO_CLP = "precio_clp"
        const val COL_CANTIDAD = "cantidad"
    }
}
