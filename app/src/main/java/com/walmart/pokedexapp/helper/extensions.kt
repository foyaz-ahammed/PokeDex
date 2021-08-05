package com.walmart.pokedexapp.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.view.isVisible
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.walmart.pokedexapp.R
import com.walmart.pokedexapp.repository.entities.Response
import com.walmart.pokedexapp.repository.entities.StateItem

fun Int.prettyPrintNo(): String = String.format("#%03d", this)
fun String.capitalizeFirst(): String = replaceFirstChar { it.uppercase() }
fun String.getNo(): Int {
    return dropLast(1).takeLastWhile { it.isDigit() }.toInt()
}
fun List<Response.PokeItem>.mapIndexAdded() = map { Response.PokeItem(it.url.getNo(), it.name, it.url) }

fun Int.formatHeight(): String = String.format("%d cm", this*10)
fun Int.formatWeight(): String = String.format("%d.%d kg", this/10, this%10)

fun Response.PokeTypeData.typeName(): String = this.type.name
fun Response.PokeDetailData.imageUrl(): String = this.sprites.back_default

@ColorRes
fun Response.PokeTypeData.getTypeColor() =
    when(typeName()) {
        "fighting" -> R.color.fighting
        "flying" -> R.color.flying
        "poison" -> R.color.poison
        "ground" -> R.color.ground
        "rock" -> R.color.rock
        "bug" -> R.color.bug
        "ghost" -> R.color.ghost
        "steel" -> R.color.steel
        "fire" -> R.color.fire
        "water" -> R.color.water
        "grass" -> R.color.grass
        "electric" -> R.color.electric
        "psychic" -> R.color.psychic
        "ice" -> R.color.ice
        "dragon" -> R.color.dragon
        "fairy" -> R.color.fairy
        "dark" -> R.color.dark
        else -> R.color.gray_21
    }

fun Response.State.convertToStateItem(): StateItem {
    val max: Int = 300
    val shortName: String
    val color: Int
    when(stat.name) {
        "hp" -> {
            shortName = "HP"
            color = R.color.colorPrimary
        }
        "attack" -> {
            shortName = "ATK"
            color = R.color.md_orange_100
        }
        "defense" -> {
            shortName = "DEF"
            color = R.color.md_blue_100
        }
        "special-attack" -> {
            shortName = "SP-ATK"
            color = R.color.md_orange_200
        }
        "special-defense" -> {
            shortName = "SP-DEF"
            color = R.color.md_blue_200
        }
        else -> {
            shortName = "SPD"
            color = R.color.flying
        }
    }

    return StateItem(shortName, base_stat, max, color)
}

@SuppressLint("CheckResult")
fun <T> RequestBuilder<T>.onEndLoading(onLoadingEnd: () -> Unit) = apply {
    listener(object : RequestListener<T> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<T>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingEnd()
            return false
        }

        override fun onResourceReady(
            resource: T?,
            model: Any?,
            target: Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingEnd()
            return false
        }
    })
}

fun swapVisibility(duration: Long, visibleView: View, vararg invisibleViews: View) {
    visibleView.alpha = 0f
    visibleView.visibility = View.VISIBLE

    val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
    valueAnimator.addUpdateListener {
        visibleView.alpha = it.animatedValue as Float
        invisibleViews.forEach { view ->
            if (view.isVisible) view.alpha = 1f - (it.animatedValue as Float)
        }
    }
    valueAnimator.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            invisibleViews.forEach { it.visibility = View.GONE}
        }
    })
    valueAnimator.duration = duration
    valueAnimator.start()
}