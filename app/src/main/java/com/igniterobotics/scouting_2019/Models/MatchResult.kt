package com.igniterobotics.scouting_2019.Models
import com.igniterobotics.scouting_2019.Enums.Alliance
import com.igniterobotics.scouting_2019.Enums.StartingPosition
import com.igniterobotics.scouting_2019.Enums.Preload
import com.igniterobotics.scouting_2019.Enums.Movement
class MatchResult {
    var teamNumber = 0
    var matchNumber = 0
    var alliance = Alliance.Red

    var autonResult = AutonResult(0,0,0,0,StartingPosition.NotSet, Preload.NotSet, Movement.NotSet)
    var telopResult = TeleopResult(0,0,0,0,false,false,false,false,false,0,0)
    var postMatchResult = PostMatchResult(0,0)
}