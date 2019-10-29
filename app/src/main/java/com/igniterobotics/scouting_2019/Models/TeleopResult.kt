package com.igniterobotics.scouting_2019.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.igniterobotics.scouting_2019.Models.DefensedPeriod

@Parcelize
data class TeleopResult (
    var hatchCount: Int,
    var cargoCount: Int,
    var intakeDrops: Int,
    var drops: Int,
    var climbTime: Int,
    var totalDefenseTime: Int,
    var hatchTimestamps: ArrayList<Double>,
    var cargoTimestamps: ArrayList<Double>,
    var intakeDropTimestamps: ArrayList<Double>,
    var dropTimestamps: ArrayList<Double>,
    var defensePeriods: ArrayList<DefensedPeriod>
): Parcelable