package edu.ucne.apigoku.domain.usecase

import edu.ucne.apigoku.domain.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetsUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(
        page: Int = 1,
        limit: Int = 10,
        name: String? = null
    ) = repository.getPlanets(page, limit, name)
}