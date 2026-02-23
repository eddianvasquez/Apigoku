package edu.ucne.apigoku.data.repository

import edu.ucne.apigoku.data.remote.DragonBallApi
import edu.ucne.apigoku.domain.model.Planet
import edu.ucne.apigoku.domain.repository.PlanetRepository
import edu.ucne.apigoku.domain.utils.Resource
import javax.inject.Inject

class PlanetRepositoryImpl @Inject constructor(
    private val api: DragonBallApi
) : PlanetRepository {

    override suspend fun getPlanets(
        page: Int, limit: Int, name: String?
    ): Resource<List<Planet>> {
        return try {
            if (name.isNullOrBlank()) {

                val response = api.getPlanets(page, limit)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!.items.map { it.toDomain() })
                } else {
                    Resource.Error("Error: ${response.message()}")
                }
            } else {

                val response = api.getPlanetsByName(name)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!.map { it.toDomain() })
                } else {
                    Resource.Error("Error: ${response.message()}")
                }
            }
        } catch (e: Exception) {
            Resource.Error("Error de conexión: ${e.localizedMessage}")
        }
    }

    override suspend fun getPlanetDetail(id: Int): Resource<Planet> {
        return try {
            val response = api.getPlanetDetail(id)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.toDomain())
            } else {
                Resource.Error("Planeta no encontrado")
            }
        } catch (e: Exception) {
            Resource.Error("Error: ${e.localizedMessage}")
        }
    }
}