package com.arakim.datastrucutrevisualization.domain.dataStructures.model

/*
usually I don't keep object associated with data layer likeJson, Responses itd.. in domain or ui.
Json should be mapped to domain object in data layer, but this is exception. We don't want implement
binary search tree or other data structure twice, also Mapping it from json to domain and from domain to
ui model would not easy to read, so data structure will care to recreate it self from json.
*/
data class DataStructure(
    val id: Int,
    val name: String,
    val type: DataStructureType,
    val dataStructureJson: String,
    val isFavorite: Boolean,
    val deletionDateUtc: Long?
)
