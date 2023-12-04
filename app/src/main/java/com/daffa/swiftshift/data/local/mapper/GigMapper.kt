package com.daffa.swiftshift.data.local.mapper

import com.daffa.swiftshift.data.local.entity.GigEntity
import com.daffa.swiftshift.data.remote.dto.GigDto
import com.daffa.swiftshift.domain.model.Gig

fun GigDto.toGigEntity(): GigEntity {
    return GigEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        description = description,
        tag = tag,
        gigProviderId = gigProviderId,
        gigProviderName = gigProviderName,
        maxApplier = maxApplier,
        currentApplier = currentApplier,
        deadlineDate = deadlineDate,
        salary = salary,
        longitude = longitude,
        latitude = latitude,
        timestamp = timestamp,
        distance = distance
    )
}

fun GigEntity.toGig(): Gig {
    return Gig(
        id = id,
        title = title,
        imageUrl = imageUrl,
        description = description,
        tag = tag,
        gigProviderId = gigProviderId,
        gigProviderName = gigProviderName,
        maxApplier = maxApplier,
        currentApplier = currentApplier,
        deadlineDate = deadlineDate,
        salary = salary,
        longitude = longitude,
        latitude = latitude,
        timestamp = timestamp,
        distance = distance
    )
}

fun GigDto.toGig(): Gig {
    return Gig(
        id = id,
        title = title,
        imageUrl = imageUrl,
        description = description,
        tag = tag,
        gigProviderId = gigProviderId,
        gigProviderName = gigProviderName,
        maxApplier = maxApplier,
        currentApplier = currentApplier,
        deadlineDate = deadlineDate,
        salary = salary,
        longitude = longitude,
        latitude = latitude,
        timestamp = timestamp,
        distance = distance
    )
}