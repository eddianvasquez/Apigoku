package edu.ucne.apigoku.domain.repository

import edu.ucne.apigoku.domain.model.Planet
import edu.ucne.apigoku.domain.utils.Resource

interface PlanetRepository {
    suspend fun getPlanets(
        page: Int = 1,
        limit: Int = 10,
        name: String? = null
    ): Resource<List<Planet>>

    suspend fun getPlanetDetail(id: Int): Resource<Planet>
}