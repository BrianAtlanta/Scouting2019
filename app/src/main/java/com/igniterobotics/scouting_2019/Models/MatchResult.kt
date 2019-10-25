package com.igniterobotics.scouting_2019.Models
import android.os.Parcelable
import com.igniterobotics.scouting_2019.Enums.Alliance
import com.igniterobotics.scouting_2019.Enums.StartingPosition
import com.igniterobotics.scouting_2019.Enums.Preload
import com.igniterobotics.scouting_2019.Enums.Movement
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MatchResult(
    var teamNumber: Int,
    var matchNumber: Int,
    var alliance: Alliance,
    var preMatchResult: PreMatchResult,
    var autonResult: AutonResult,
    var telopResult: TeleopResult,
    var postMatchResult: PostMatchResult
) : Parcelable