package com.example.walking.model

data class LikeDTO (
    var startCount: Int,
    var stars: Map<String, Boolean> = HashMap()

)

