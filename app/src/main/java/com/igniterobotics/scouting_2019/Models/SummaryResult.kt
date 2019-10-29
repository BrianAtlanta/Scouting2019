package com.igniterobotics.scouting_2019.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SummaryResult(
    var matchesCompleted: Int,
    var maxCycles: Int,
    var minCycles: Int,
    var avgCycles: Double,
    var maxCargo: Int,
    var minCargo: Int,
    var avgCargo: Int,
    var maxHatch: Int,
    var minHatch: Int,
    var avgHatch: Double,
    var level3: Int,
    var level3Success: Int,
    var level2: Int,
    var level2Success: Int,
    var level1: Int,
    var level1Success: Int,
    var maxClimb: Double,
    var minClimb: Double,
    var avgClimb: Double,
    var playedDefense: Boolean
    ): Parcelable