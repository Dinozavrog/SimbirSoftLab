package com.example.simbirsoft.data.utils

interface ResourceProvider {

    fun getString(id: Int): String

    fun getColor(id: Int): Int
}