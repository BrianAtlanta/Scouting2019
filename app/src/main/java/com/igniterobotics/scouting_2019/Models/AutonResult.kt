package com.igniterobotics.scouting_2019.Models

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.igniterobotics.scouting_2019.Enums.Movement
import com.igniterobotics.scouting_2019.Enums.Preload
import com.igniterobotics.scouting_2019.Enums.StartingPosition

@Parcelize
data class AutonResult(
    var hatchCount: Int,
    var cargoCount: Int,
    var intakeDrop: Int,
    var itemDrops: Int,
    var startingPosision: StartingPosition,
    var preload: Preload,
    var movement: Movement
): Parcelable
