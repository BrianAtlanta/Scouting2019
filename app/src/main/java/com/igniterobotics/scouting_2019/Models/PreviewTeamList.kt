package com.igniterobotics.scouting_2019.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PreviewTeamList (
    var red1: Int,
    var red2: Int,
    var red3: Int,
    var blue1: Int,
    var blue2: Int,
    var blue3: Int
): Parcelable