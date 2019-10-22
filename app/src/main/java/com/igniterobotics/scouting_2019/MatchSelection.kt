package com.igniterobotics.scouting_2019

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter

import android.widget.Spinner

import android.widget.AdapterView
import android.view.View
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.igniterobotics.scouting_2019.Enums.Alliance
import com.igniterobotics.scouting_2019.Enums.Movement
import com.igniterobotics.scouting_2019.Enums.Preload
import com.igniterobotics.scouting_2019.Enums.StartingPosition
import com.igniterobotics.scouting_2019.Models.*
import kotlinx.android.synthetic.main.activity_match_selection.*


class MatchSelection : AppCompatActivity() {

    var matchSchedule = arrayListOf<Match>()
    var scoutPositions = arrayOf("  Red 1  ", "  Red 2  ", "  Red 3  ", "  Blue 1  ", "  Blue 2  ", "  Blue 3  ")
    var autonResult = AutonResult(0,0,0,0,StartingPosition.NotSet,Preload.NotSet,Movement.NotSet)
    var teleopResult = TeleopResult(0,0,0,0,false,false,false,false,false,0,0)
    var postMatchResult = PostMatchResult(0,0,false,false,0,0,0,0,0,false,false,false)
    var _matchResult = MatchResult(0,0,Alliance.Red, autonResult, teleopResult,postMatchResult)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_match_selection)

        matchSchedule.add(Match(1, 832, 1102, 2974, 4910, 1746, 4026))
        matchSchedule.add(Match(2, 4188, 971, 254, 1481, 330, 271))
        matchSchedule.add(Match(3, 1678, 148, 118, 1619, 1114, 33))

        var startPreMatch = findViewById<Button>(R.id.startPreMatch)
        var scoutNumberSpinner = findViewById<Spinner>(R.id.scoutNumber)
        val movementAdapter = ArrayAdapter(this, R.layout.ignite_spinner, scoutPositions)
        movementAdapter.setDropDownViewResource(R.layout.ignite_spinner)
        scoutNumberSpinner.adapter = movementAdapter

        var matches = mutableListOf<String>()
        for (match in matchSchedule) {
            matches.add("   Quals #" + match.number.toString() + "   ")
        }

        var matchSpinner = findViewById<Spinner>(R.id.matches)
        val matchAdapter = ArrayAdapter(this, R.layout.ignite_spinner, matches)
        matchAdapter.setDropDownViewResource(R.layout.ignite_spinner)
        matchSpinner.adapter = matchAdapter

        matchSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("TAG", "#############   11111111111111111")
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                UpdateTeamNumber()
            }
        }

        scoutNumberSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("TAG", "#############   11111111111111111")
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                UpdateTeamNumber()
            }
        }

        var data = intent.extras
        var nextMatch = (if (data != null) data.get("NextMatch") else 1) as Int
        if (nextMatch > 0 && nextMatch <= matches.count())
        {
            matchSpinner.setSelection( nextMatch - 1)
        }

        startPreMatch.setOnClickListener() {
            val intent = Intent(this, PreMatch::class.java)
            intent.putExtra("MatchResult", _matchResult)
            startActivity(intent)
        }


    }
    fun UpdateTeamNumber(){

        var matchSpinner = findViewById<Spinner>(R.id.matches)
        var scoutNumberSpinner = findViewById<Spinner>(R.id.scoutNumber)
        var match = matchSchedule[matchSpinner.selectedItemPosition]

        _matchResult.matchNumber = matchSpinner.selectedItemPosition +1;
        when (scoutNumberSpinner.selectedItemPosition) {
            0 -> {
                _matchResult.teamNumber = match.red1
                _matchResult.alliance = Alliance.Red
                botToScout.text = match.red1.toString()
                botToScout.setTextColor(Color.RED)
            }
            1 -> {
                _matchResult.teamNumber = match.red2
                _matchResult.alliance = Alliance.Red
                botToScout.text = match.red2.toString()
                botToScout.setTextColor(Color.RED)
            }
            2 -> {
                _matchResult.teamNumber = match.red3
                _matchResult.alliance = Alliance.Red
                botToScout.text = match.red3.toString()
                botToScout.setTextColor(Color.RED)
            }
            3 -> {
                _matchResult.teamNumber = match.blue1
                _matchResult.alliance = Alliance.Blue
                botToScout.text = _matchResult.teamNumber.toString()
                botToScout.setTextColor(Color.BLUE)
            }
            4 -> {
                _matchResult.teamNumber = match.blue2
                _matchResult.alliance = Alliance.Blue
                botToScout.text = _matchResult.teamNumber.toString()
                botToScout.setTextColor(Color.BLUE)
            }
            5 -> {
                _matchResult.teamNumber = match.blue3
                _matchResult.alliance = Alliance.Blue
                botToScout.text = match.blue3.toString()
                botToScout.setTextColor(Color.BLUE)
            }
        }

    }

}
