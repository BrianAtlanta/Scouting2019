package com.igniterobotics.scouting_2019.Models

import android.os.Parcelable
import com.igniterobotics.scouting_2019.Enums.Preload
import com.igniterobotics.scouting_2019.Enums.StartingPosition
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PreMatchResult (
    var startingPosision: StartingPosition,
    var preload: Preload
): Parcelable