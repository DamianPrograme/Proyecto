package com.example.proyecto.Funciones

import android.content.ContentValues
import android.content.Context
import com.example.proyecto.BD.CarritoDbHelper

class CarritoDao(context: Context) {

    private val dbHelper = CarritoDbHelper(context.applicationContext)

    fun insertar(item: CarritoItem): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(CarritoDbHelper.COL_NOMBRE, item.nombre)
            put(CarritoDbHelper.COL_TIPO, item.tipo)
            put(CarritoDbHelper.COL_PRECIO_CLP, item.precioClp)
            put(CarritoDbHelper.COL_CANTIDAD, item.cantidad)
        }

        val id = db.insert(CarritoDbHelper.TABLE_CARRITO, null, values)
        db.close()
        return id
    }

    fun obtenerTodos(): List<CarritoItem> {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<CarritoItem>()

        val cursor = db.query(
            CarritoDbHelper.TABLE_CARRITO,
            null,          // todas las columnas
            null, null,    // sin WHERE
            null, null,
            "${CarritoDbHelper.COL_ID} DESC"
        )

        cursor.use { c ->
            if (c.moveToFirst()) {
                do {
                    val id = c.getLong(c.getColumnIndexOrThrow(CarritoDbHelper.COL_ID))
                    val nombre = c.getString(c.getColumnIndexOrThrow(CarritoDbHelper.COL_NOMBRE))
                    val tipo = c.getString(c.getColumnIndexOrThrow(CarritoDbHelper.COL_TIPO))
                    val precio = c.getDouble(c.getColumnIndexOrThrow(CarritoDbHelper.COL_PRECIO_CLP))
                    val cantidad = c.getInt(c.getColumnIndexOrThrow(CarritoDbHelper.COL_CANTIDAD))

                    lista.add(
                        CarritoItem(
                            nombre = nombre,
                            tipo = tipo,
                            precioClp = precio,
                            cantidad = cantidad,
                            id = id
                        )
                    )
                } while (c.moveToNext())
            }
        }

        db.close()
        return lista
    }

    fun vaciar() {
        val db = dbHelper.writableDatabase
        db.delete(CarritoDbHelper.TABLE_CARRITO, null, null)
        db.close()
    }
}
