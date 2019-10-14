package com.igniterobotics.scouting_2019.Models
import com.igniterobotics.scouting_2019.Enums.Alliance
class MatchResult {
    var teamNumber = 0
    var matchNumber = 0
    var alliance = Alliance.Red

    var autonResult = AutonResult()
    var telopResult = TeleopResult()
    var postMatchResult = PostMatchResult()
}