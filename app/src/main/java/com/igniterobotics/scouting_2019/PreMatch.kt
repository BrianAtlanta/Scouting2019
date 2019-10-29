package com.igniterobotics.scouting_2019

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.igniterobotics.scouting_2019.Enums.Preload
import com.igniterobotics.scouting_2019.Enums.StartingPosition
import com.igniterobotics.scouting_2019.Models.*

class PreMatch : AppCompatActivity() {


    var startPositionOptions = arrayOf("--- NOT SET ---","Level 1 - Near", "Level 1 - Middle", "Level 1 - Far", "Level 2 - Near", "Level 2 - Far", "Level 3")
    var preloadOptions = arrayOf("--- NOT SET ---","Hatch", "Cargo")
    lateinit var _matchResult: MatchResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_match)

        var data = intent.extras
        _matchResult = data?.getParcelable<MatchResult>("MatchResult")!!


        var startAutonButton = findViewById<Button>(R.id.startAutonButton)
        var cancelMatchButton = findViewById<Button>(R.id.cancelPreMatchButton)

        var positionSpinner = findViewById<Spinner>(R.id.prematchPosition)
        val positionAdapter = ArrayAdapter(this, R.layout.ignite_spinner, startPositionOptions)
        positionAdapter.setDropDownViewResource(R.layout.ignite_spinner)
        positionSpinner.adapter = positionAdapter


        var preloadSpinner = findViewById<Spinner>(R.id.prematchPreload)
        val preloadAdapter = ArrayAdapter(this, R.layout.ignite_spinner, preloadOptions)
        preloadAdapter.setDropDownViewResource(R.layout.ignite_spinner)
        preloadSpinner.adapter = preloadAdapter

        positionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                updateMatchResults()
            }
        }

        preloadSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                updateMatchResults()
            }
        }

        setTitle("Team " + _matchResult.teamNumber.toString() + " - Pre-match")

        startAutonButton.setOnClickListener() {
            val intent = Intent(this, AutonScoring::class.java)
           intent.putExtra("MatchResult", _matchResult)
           startActivity(intent)
        }

        cancelMatchButton.setOnClickListener() {
            val intent = Intent(this, MatchSelection::class.java)
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        true

    }

    fun updateMatchResults(){
        var preload = findViewById<Spinner>(R.id.prematchPreload)
        var positionSpinner = findViewById<Spinner>(R.id.prematchPosition)

        when (positionSpinner.selectedItemPosition) {
            1 -> _matchResult.preMatchResult.startingPosision = StartingPosition.LevelOneNear
            2 -> _matchResult.preMatchResult.startingPosision = StartingPosition.LevelOneMiddle
            3 -> _matchResult.preMatchResult.startingPosision = StartingPosition.LevelOneFar
            4 -> _matchResult.preMatchResult.startingPosision = StartingPosition.LevelTwoNear
            5 -> _matchResult.preMatchResult.startingPosision = StartingPosition.LevelTwoFar
            6 -> _matchResult.preMatchResult.startingPosision = StartingPosition.LevelThree
        }

        when (preload.selectedItemPosition) {
            1 -> _matchResult.preMatchResult.preload = Preload.Cargo
            2 -> _matchResult.preMatchResult.preload = Preload.Hatch
        }
    }
}
