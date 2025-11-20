package com.example.proyecto.Funciones

import com.example.proyecto.API.ProductoApi

class CombinarProductosWarhammer {
    private val nombresWarhammer = listOf(
        "Warhammer 40,000: Dawn of War - Anniversary Edition",
        "Warhammer 40,000: Dawn of War - Definitive Edition",
        "Warhammer 40,000: Dawn of War II - Anniversary Edition",
        "Warhammer 40,000: Dawn of War III",
        "Warhammer 40,000: Dawn of War IV",
        "Warhammer 40,000: Space Marine",
        "Warhammer 40,000: Space Marine 2",
        "Warhammer 40,000: Boltgun",
        "Warhammer 40,000: Darktide",
        "Warhammer 40,000: Mechanicus",
        "Warhammer 40,000: Inquisitor - Martyr",
        "Warhammer 40,000: Chaos Gate - Daemonhunters",
        "Warhammer 40,000: Sanctus Reach",
        "Warhammer 40,000: Eternal Crusade",
        "Warhammer 40,000: Space Hulk - Deathwing",
        "Battlefleet Gothic: Armada",
        "Battlefleet Gothic: Armada II",
        "Warhammer 40,000: Regicide",
        "Warhammer 40,000: Gladius - Relics of War",
        "Warhammer 40,000: Storm of Vengeance"
    )
    fun combinar(apiProducts: List<ProductoApi>): List<ProductoApi> {
        val listaFinal = mutableListOf<ProductoApi>()
        val size = minOf(apiProducts.size, nombresWarhammer.size)

        for (i in 0 until size) {
            val prodApi = apiProducts[i]
            val juego = ProductoApi(
                id = prodApi.id,
                title = nombresWarhammer[i],
                price = prodApi.price   // USD
            )
            listaFinal.add(juego)
        }
        return listaFinal
    }
}
