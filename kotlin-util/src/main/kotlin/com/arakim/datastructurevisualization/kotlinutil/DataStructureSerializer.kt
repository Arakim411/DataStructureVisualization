package com.arakim.datastructurevisualization.kotlinutil

interface DataStructureSerializer {

    fun serializeToJson(): String

    fun createFromJson(json: String)
}
