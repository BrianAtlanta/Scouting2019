package com.igniterobotics.scouting_2019.Models

data class TeleopResult (
    var hatchCount: Int,
    var cargoCount: Int,
    var intakeDrops: Int,
    var drops: Int,
    var died: Boolean,
    var broken: Boolean,
    var disabled: Boolean,
    var level2Ability: Boolean,
    var level3Ability: Boolean,
    var climbTime: Int,
    var totalDefenseTime: Int,
    var defensePeriods: mutableListOf<defensedPeriod>(),
    var hatchScoringTime: mutableListOf<Int> = null
)