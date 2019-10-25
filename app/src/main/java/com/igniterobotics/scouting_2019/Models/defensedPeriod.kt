package com.igniterobotics.scouting_2019.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DefensedPeriod(var defenseStarted: Double, var defenseStopped: Double)  : Parcelable