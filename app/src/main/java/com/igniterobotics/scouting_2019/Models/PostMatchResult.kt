package com.igniterobotics.scouting_2019.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PostMatchResult(
    var attemptedLevel: Int,
    var scoredLevel: Int,
    var rocketLevel2: Boolean,
    var rocketLevel3: Boolean,
    var yellowCard: Boolean,
    var redCard: Boolean,
    var rocketRp: Boolean,
    var habRp: Boolean): Parcelable