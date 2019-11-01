package com.igniterobotics.scouting_2019

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.beust.klaxon.Klaxon
import com.google.firebase.firestore.FirebaseFirestore
import com.igniterobotics.scouting_2019.Enums.Alliance
import com.igniterobotics.scouting_2019.Enums.Movement
import com.igniterobotics.scouting_2019.Enums.StartingPosition
import com.igniterobotics.scouting_2019.Models.MatchResult
import com.igniterobotics.scouting_2019.Models.PreviewTeamList
import com.igniterobotics.scouting_2019.Models.SummaryResult
import kotlin.math.absoluteValue
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter

class MatchPreview : AppCompatActivity() {


    lateinit var _red1: SummaryResult
    lateinit var _red2: SummaryResult
    lateinit var _red3: SummaryResult
    lateinit var _blue1: SummaryResult
    lateinit var _blue2: SummaryResult
    lateinit var _blue3: SummaryResult
    var redScore = 0.0
    var blueScore = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_preview)

        var data = intent.extras
        var teams = data?.getParcelable<PreviewTeamList>("teams")!!

        populateSummaryResult(teams.red1, 1, Alliance.Red, true)
        populateSummaryResult(teams.red2, 2, Alliance.Red, true)
        populateSummaryResult(teams.red3, 3, Alliance.Red, true)

        populateSummaryResult(teams.blue1, 1, Alliance.Blue, false)
        populateSummaryResult(teams.blue2, 2, Alliance.Blue, false)
        populateSummaryResult(teams.blue3, 3, Alliance.Blue, false)

        findViewById<TextView>(R.id.blueAvgScore).setTextColor(Color.BLUE)
        findViewById<TextView>(R.id.redAvgScore).setTextColor(Color.RED)
        setTeamColor(Alliance.Red)

        findViewById<TextView>(R.id.redAvgScore).setOnClickListener {
            populateRobot1(_red1)
            populateRobot2(_red2)
            populateRobot3(_red3)
            setTeamColor(Alliance.Red)
        }

        findViewById<TextView>(R.id.blueAvgScore).setOnClickListener {
            populateRobot1(_blue1)
            populateRobot2(_blue2)
            populateRobot3(_blue3)
            setTeamColor(Alliance.Blue)

        }
    }

    private fun setTeamColor(alliance: Alliance)
    {
        if (alliance == Alliance.Red) {
            findViewById<TextView>(R.id.red1Label).setTextColor(Color.RED)
            findViewById<TextView>(R.id.red2Label).setTextColor(Color.RED)
            findViewById<TextView>(R.id.red3Label).setTextColor(Color.RED)
        } else {
            findViewById<TextView>(R.id.red1Label).setTextColor(Color.BLUE)
            findViewById<TextView>(R.id.red2Label).setTextColor(Color.BLUE)
            findViewById<TextView>(R.id.red3Label).setTextColor(Color.BLUE)
        }

    }
    private fun populateRobot1(summaryResult: SummaryResult){

        findViewById<TextView>(R.id.red1Label).text = if (summaryResult.teamNumber != 0) summaryResult.teamNumber.toString() else "----"

        findViewById<TextView>(R.id.r1Matches).text = if (summaryResult.matchesCompleted > 0) summaryResult.matchesCompleted.toString() else "0"

        findViewById<TextView>(R.id.r1AvgCycles).text = if (summaryResult.avgCycles > 0) summaryResult.avgCycles.toString() else "0"
        findViewById<TextView>(R.id.r1MaxCycles).text = summaryResult.maxCycles.toString()
        findViewById<TextView>(R.id.r1MinCycles).text = if (summaryResult.minCycles < 99) summaryResult.minCycles.toString() else "0"

        findViewById<TextView>(R.id.r1AvgCargo).text = if (summaryResult.avgCargo > 0) summaryResult.avgCargo.toString() else "0"
        findViewById<TextView>(R.id.r1MaxCargo).text = summaryResult.maxCargo.toString()

        findViewById<TextView>(R.id.r1AvgHatches).text = if (summaryResult.avgHatch > 0) summaryResult.avgHatch.toString() else "0"
        findViewById<TextView>(R.id.r1MaxHatches).text = summaryResult.maxHatch.toString()

        findViewById<TextView>(R.id.r1AvgScore).text = if (summaryResult.avgScore > 0) summaryResult.avgScore.toString() else "0"
        findViewById<TextView>(R.id.r1MaxScore).text = summaryResult.maxScore.toString()
        findViewById<TextView>(R.id.r1MinScore).text = if (summaryResult.minScore < 99) summaryResult.minScore.toString() else "0"

        findViewById<TextView>(R.id.r1Hab1).text = summaryResult.level1.toString()
        findViewById<TextView>(R.id.r1Hab2).text = summaryResult.level2.toString()
        findViewById<TextView>(R.id.r1Hab3).text = summaryResult.level3.toString()

        findViewById<TextView>(R.id.r1AvgClimb).text = summaryResult.avgClimb.toString()
        findViewById<TextView>(R.id.r1MaxClimb).text = summaryResult.maxClimb.toString()
        findViewById<TextView>(R.id.r1MinClimb).text = if (summaryResult.minClimb < 50) summaryResult.minClimb.toString() else "0"



    }

    private fun populateRobot2(summaryResult: SummaryResult){

        findViewById<TextView>(R.id.red2Label).text = if (summaryResult.teamNumber != 0) summaryResult.teamNumber.toString() else "----"
        findViewById<TextView>(R.id.r2Matches).text = if (summaryResult.matchesCompleted > 0) summaryResult.matchesCompleted.toString() else "0"
        findViewById<TextView>(R.id.r2AvgCycles).text = if (summaryResult.avgCycles > 0) summaryResult.avgCycles.toString() else "0"
        findViewById<TextView>(R.id.r2MaxCycles).text = summaryResult.maxCycles.toString()
        findViewById<TextView>(R.id.r2MinCycles).text = if (summaryResult.minCycles < 99) summaryResult.minCycles.toString() else "0"

        findViewById<TextView>(R.id.r2AvgCargo).text = if (summaryResult.avgCargo > 0) summaryResult.avgCargo.toString() else "0"
        findViewById<TextView>(R.id.r2MaxCargo).text = summaryResult.maxCargo.toString()

        findViewById<TextView>(R.id.r2AvgHatches).text = if (summaryResult.avgHatch > 0) summaryResult.avgHatch.toString() else "0"
        findViewById<TextView>(R.id.r2MaxHatches).text = summaryResult.maxHatch.toString()

        findViewById<TextView>(R.id.r2AvgScore).text = if (summaryResult.avgScore > 0) summaryResult.avgScore.toString() else "0"
        findViewById<TextView>(R.id.r2MaxScore).text = summaryResult.maxScore.toString()
        findViewById<TextView>(R.id.r2MinScore).text = if (summaryResult.minScore < 99) summaryResult.minScore.toString() else "0"

        findViewById<TextView>(R.id.r2Hab1).text = summaryResult.level1.toString()
        findViewById<TextView>(R.id.r2Hab2).text = summaryResult.level2.toString()
        findViewById<TextView>(R.id.r2Hab3).text = summaryResult.level3.toString()

        findViewById<TextView>(R.id.r2AvgClimb).text = summaryResult.avgClimb.toString()
        findViewById<TextView>(R.id.r1MaxClimb2).text = summaryResult.maxClimb.toString()
        findViewById<TextView>(R.id.r2MinClimb).text = if (summaryResult.minClimb < 50) summaryResult.minClimb.toString() else "0"
    }

    private fun populateRobot3(summaryResult: SummaryResult){

        findViewById<TextView>(R.id.red3Label).text = if (summaryResult.teamNumber != 0) summaryResult.teamNumber.toString() else "----"
        findViewById<TextView>(R.id.r3Matches).text = if (summaryResult.matchesCompleted > 0) summaryResult.matchesCompleted.toString() else "0"
        findViewById<TextView>(R.id.r3AvgCycles).text = if (summaryResult.avgCycles > 0) summaryResult.avgCycles.toString() else "0"
        findViewById<TextView>(R.id.r3MaxCycles).text = summaryResult.maxCycles.toString()
        findViewById<TextView>(R.id.r3MinCycles).text = if (summaryResult.minCycles < 99) summaryResult.minCycles.toString() else "0"

        findViewById<TextView>(R.id.r3AvgCargo).text = if (summaryResult.avgCargo > 0) summaryResult.avgCargo.toString() else "0"
        findViewById<TextView>(R.id.r3MaxCargo).text = summaryResult.maxCargo.toString()

        findViewById<TextView>(R.id.r3AvgHatches).text = if (summaryResult.avgHatch > 0) summaryResult.avgHatch.toString() else "0"
        findViewById<TextView>(R.id.r3MaxHatches).text = summaryResult.maxHatch.toString()

        findViewById<TextView>(R.id.r3AvgScore).text = if (summaryResult.avgScore > 0) summaryResult.avgScore.toString() else "0"
        findViewById<TextView>(R.id.r3MaxScore).text = summaryResult.maxScore.toString()
        findViewById<TextView>(R.id.r3MinScore).text = if (summaryResult.minScore < 99) summaryResult.minScore.toString() else "0"

        findViewById<TextView>(R.id.r3Hab1).text = summaryResult.level1.toString()
        findViewById<TextView>(R.id.r3Hab2).text = summaryResult.level2.toString()
        findViewById<TextView>(R.id.r3Hab3).text = summaryResult.level3.toString()

        findViewById<TextView>(R.id.r3AvgClimb).text = summaryResult.avgClimb.toString()
        findViewById<TextView>(R.id.r1MaxClimb3).text = summaryResult.maxClimb.toString()
        findViewById<TextView>(R.id.r3MinClimb).text = if (summaryResult.minClimb < 50) summaryResult.minClimb.toString() else "0"
    }



    private fun populateSummaryResult(teamNumber: Int, position: Int, alliance: Alliance, display: Boolean) {
        val db = FirebaseFirestore.getInstance()

        var totalCycles = 0
        var maxCycles = 0
        var minCycles = 99
        var totalCargo = 0
        var totalHatches = 0
        var matchCnt = 0
        var maxScore = 0
        var minScore = 99
        var totalScore = 0
        var hab1 = 0
        var hab2 = 0
        var hab3 = 0
        var maxCargo = 0
        var maxHatch = 0
        var cargoMatchCnt = 0
        var hatchMatchCnt = 0
        var playedDefense = 0
        var minClimbTime = 100
        var maxClimbTime = 0
        var totalClimbTime = 0
        var climbTimeCount = 0
        var summaryResult = SummaryResult(teamNumber,0,0,0,0.0,0,0.0,0,0.0,0.0,0,0,0,0,0,0,0,0.0,0)
        db.collection("matches")
            .whereEqualTo("team", teamNumber)
            .get()
            .addOnSuccessListener { documents ->
                Log.w("TAG", "-------------------- Retreived data.")
                for (document in documents) {
                    Log.w("TAG", "---------------- PROCESSING  PROCESSING  PROCESSING ")
                    var matchResultJson = document.data.getValue("data")
                    var pastMatch = Klaxon().parse<MatchResult>(matchResultJson.toString())
                    var mCargo = pastMatch?.autonResult?.cargoCount!! + pastMatch.telopResult.cargoCount!!
                    var mHatch = pastMatch?.autonResult?.hatchCount!! + pastMatch.telopResult.hatchCount!!
                    Log.w("TAG", "AutonCargo ${pastMatch?.autonResult?.cargoCount!!}  TeleopCargo ${pastMatch.telopResult.cargoCount!!}  mCargo ${mCargo}")
                    Log.w("TAG", "AutonHatch ${pastMatch?.autonResult?.hatchCount!!}  TeleopHatch ${pastMatch.telopResult.hatchCount!!}  mHatch ${mHatch}")
                    matchCnt++
                    if (minCycles > mCargo + mHatch)
                        minCycles = mCargo + mHatch

                    if (maxCycles < mCargo + mHatch)
                        maxCycles = mCargo + mHatch

                    if (maxCargo < mCargo)
                        maxCargo = mCargo

                    if (maxHatch < mHatch)
                        maxHatch = mHatch

                    if (pastMatch.telopResult.defensePeriods.count() > 1)
                        playedDefense++

                    var endGameScore = 0
                    when(pastMatch.postMatchResult.scoredLevel){
                        1 -> {
                            endGameScore = 3
                            hab1++
                        }
                        2 -> {
                            endGameScore = 6
                            hab2++
                        }

                        3 -> {
                            endGameScore = 12
                            hab3++
                            if (pastMatch.telopResult.climbTime.absoluteValue > 0) {
                                climbTimeCount++

                                if (minClimbTime > pastMatch.telopResult.climbTime.absoluteValue)
                                    minClimbTime = pastMatch.telopResult.climbTime.absoluteValue

                                if (maxClimbTime < pastMatch.telopResult.climbTime.absoluteValue)
                                    maxClimbTime = pastMatch.telopResult.climbTime.absoluteValue

                                totalClimbTime += pastMatch.telopResult.climbTime.absoluteValue
                            }
                        }
                    }

                    var autonScore = 0
                    if (pastMatch.autonResult.movement == Movement.Crossed)
                    {
                        when (pastMatch.preMatchResult.startingPosision)
                        {
                            StartingPosition.LevelOneFar, StartingPosition.LevelOneMiddle, StartingPosition.LevelOneNear -> autonScore = 3
                            StartingPosition.LevelTwoFar, StartingPosition.LevelTwoNear -> autonScore = 6
                        }
                    }
                    var mScore = autonScore + (mCargo * 3) + (mHatch * 2) + endGameScore

                    totalScore += mScore
                    if (maxScore < mScore)
                        maxScore = mScore

                    if (minScore > mScore)
                        minScore = mScore

                    totalCycles += mCargo + mHatch
                    if (pastMatch.telopResult.cargoCount > 0) {
                        totalCargo += mCargo
                        cargoMatchCnt++
                    }

                    if (pastMatch.telopResult.hatchCount > 0) {
                        totalHatches += mHatch
                        hatchMatchCnt++
                    }

                }
                //%.1f".format( totalCargo / (matchCount * 1.0)).toDouble
                var avgCargo = "%.1f".format(totalCargo / (cargoMatchCnt * 1.0)).toDouble()
                var avgHatch = "%.1f".format(totalHatches / (hatchMatchCnt * 1.0)).toDouble()
                var avgCycles = "%.1f".format(totalCycles / (matchCnt * 1.0)).toDouble()
                var avgScore = "%.1f".format(totalScore / (matchCnt * 1.0)).toDouble()
                var avgClimbTime = 0.0
                if (climbTimeCount > 0)
                    avgClimbTime = totalClimbTime / (climbTimeCount * 1.0)
                Log.w("TAG", "==================== Cargo ${totalCargo}  Hatches ${totalHatches}")
                summaryResult = SummaryResult(teamNumber, matchCnt,maxCycles,minCycles,avgCycles,maxCargo,avgCargo,maxHatch,avgHatch,avgScore,maxScore,minScore,hab3,hab2,hab1,maxClimbTime,minClimbTime, avgClimbTime, playedDefense)

                if (alliance == Alliance.Blue)
                {
                    if (summaryResult.avgScore > 0) blueScore += summaryResult.avgScore
                    when(position){
                        1 -> _blue1 = summaryResult
                        2 -> _blue2 = summaryResult
                        else -> _blue3 = summaryResult

                    }
                }
                else
                {
                    if (summaryResult.avgScore > 0) redScore += summaryResult.avgScore
                    when(position) {
                        1 -> _red1 = summaryResult
                        2 -> _red2 = summaryResult
                        else -> _red3 = summaryResult
                    }
                }

                if (display) {
                    when (position) {
                        1 -> populateRobot1(summaryResult)
                        2 -> populateRobot2(summaryResult)
                        else -> populateRobot3(summaryResult)
                    }
                }

                findViewById<TextView>(R.id.blueAvgScore).text = "%.1f".format(blueScore).toDouble().toString()
                findViewById<TextView>(R.id.redAvgScore).text = "%.1f".format(redScore).toDouble().toString()
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "ERR ERR ERR ERR ERR --------  Error getting documents: ", exception)
            }
    }
}
