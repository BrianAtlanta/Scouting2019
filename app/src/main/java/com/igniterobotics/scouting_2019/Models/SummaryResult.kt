package com.igniterobotics.scouting_2019.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SummaryResult(
    var teamNumber: Int,
    var matchesCompleted: Int,
    var maxCycles: Int,
    var minCycles: Int,
    var avgCycles: Double,
    var maxCargo: Int,
    var avgCargo: Double,
    var maxHatch: Int,
    var avgHatch: Double,
    var avgScore: Double,
    var maxScore: Int,
    var minScore: Int,
    var level3: Int,
    var level2: Int,
    var level1: Int,
    var maxClimb: Int,
    var minClimb: Int,
    var avgClimb: Double,
    var playedDefense: Int
    ): Parcelable