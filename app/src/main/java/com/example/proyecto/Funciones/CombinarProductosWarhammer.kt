package com.example.proyecto.Funciones

import com.example.proyecto.API.ProductoApi

class CombinarProductosWarhammer {

    // Lista ampliada de juegos Warhammer 40k
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
        "Warhammer 40,000: Storm of Vengeance",
        "Warhammer 40,000: Space Hulk Tactics",
        "Warhammer 40,000: Armageddon",
        "Warhammer 40,000: Deathwatch – Enhanced Edition",
        "Warhammer 40,000: Space Wolf",
        "Warhammer 40,000: Dakka Squadron",
        "Warhammer 40,000: Shootas, Blood & Teef",
        "Warhammer 40,000: Battle Sister",
        "Warhammer 40,000: Fire Warrior",
        "Warhammer 40,000: Battlesector",
        "Warhammer Age of Sigmar: Realms of Ruin",
        "Warhammer 40,000: Rogue Trader",
        "Warhammer 40,000: Speed Freeks",
        "Warhammer 40,000: Warpforge",
        "Warhammer 40,000: Dark Heresy",
        "Warhammer: Vermintide 2",
        "Warhammer: End Times - Vermintide",
        "Warhammer 40,000: Dakka Squadron - Flyboyz Edition",
        "Warhammer 40,000: Shootas, Blood & Teef"

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
