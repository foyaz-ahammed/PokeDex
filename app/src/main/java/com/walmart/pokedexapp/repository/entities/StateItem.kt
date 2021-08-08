package com.walmart.pokedexapp.repository.entities

import androidx.annotation.ColorRes

/**
 * Model class to define stat item
 */
class StateItem(val short_name: String, val base_stat: Int, val max: Int, @ColorRes val color: Int)