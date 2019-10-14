package com.igniterobotics.scouting_2019

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.igniterobotics.scouting_2019.Models.AutonResult
import kotlinx.android.synthetic.main.activity_auton_scoring.*
import android.content.Intent
import com.igniterobotics.scouting_2019.Enums.Movement
import com.igniterobotics.scouting_2019.Enums.Preload
import com.igniterobotics.scouting_2019.Enums.StartingPosition

class AutonScoring : AppCompatActivity() {

    private var _aCargoCount = 0
    private var _aHatchCount = 0
    private var _aIntakeDrop = 0
    private var _aDropCount = 0

    var startPositionOptions = arrayOf("--- NOT SET ---","Level 1 - Near", "Level 1 - Middle", "Level 1 - Far", "Level 2 - Near", "Level 2 - Far", "Level 3")
    var preloadOptions = arrayOf("--- NOT SET ---","Hatch", "Cargo")
    var movementOptions = arrayOf("--- NOT SET ---","None", "Did Not Cross", "Crossed", "Too Far")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auton_scoring)
        setSupportActionBar(toolbar)

        var minusCargoButton = findViewById<Button>(R.id.aMinusCargoButton)
        var addCargoButton = findViewById<Button>(R.id.aAddCargoButton)
        var minusHatchButton = findViewById<Button>(R.id.aMinusHatchButton)
        var addHatchButton = findViewById<Button>(R.id.aAddHatchButton)
        var addIntakeErrorButton = findViewById<Button>(R.id.aAddIntakeError)
        var substractIntakeErrorButton = findViewById<Button>(R.id.aSubstractIntakeError)
        var addDropButton = findViewById<Button>(R.id.aDropButton)
        var minusDropButton = findViewById<Button>(R.id.aMinusDropButton)
        var startTelopButton = findViewById<Button>(R.id.StartTeleopButton)

        var cargoCount = findViewById<TextView>(R.id.aCargoCount)
        var hatchCount = findViewById<TextView>(R.id.aHatchCount)
        var intakeErrorCount = findViewById<TextView>(R.id.aIntakeError)
        var dropCount = findViewById<TextView>(R.id.aDropCount)


        var positionSpinner = findViewById<Spinner>(R.id.startingPosition)
        val positionAdapter = ArrayAdapter(this, R.layout.ignite_spinner, startPositionOptions)
        positionAdapter.setDropDownViewResource(R.layout.ignite_spinner)
        positionSpinner.adapter = positionAdapter


        var preloadSpinner = findViewById<Spinner>(R.id.preload)
        val preloadAdapter = ArrayAdapter(this, R.layout.ignite_spinner, preloadOptions)
        preloadAdapter.setDropDownViewResource(R.layout.ignite_spinner)
        preloadSpinner.adapter = preloadAdapter

        var movementSpinner = findViewById<Spinner>(R.id.movement)
        val movementAdapter = ArrayAdapter(this, R.layout.ignite_spinner, movementOptions)
        movementAdapter.setDropDownViewResource(R.layout.ignite_spinner)
        movementSpinner.adapter = movementAdapter

        if (savedInstanceState != null)
        {
            Log.d("TAG", "########## RESTORING STATE #############")
            _aCargoCount = savedInstanceState.run { getInt("_cargoCount") }
            cargoCount.text = _aCargoCount.toString()

            _aDropCount = savedInstanceState.run { getInt("_dropCount") }
            dropCount.text = _aDropCount.toString()

            _aIntakeDrop = savedInstanceState.run { getInt("_intakeDrop") }
            intakeErrorCount.text = _aIntakeDrop.toString()

            _aHatchCount = savedInstanceState.run { getInt("_hatchCount") }
            hatchCount.text = _aHatchCount.toString()

        }

        minusCargoButton.setOnClickListener() {
            if (_aCargoCount > 0)
                _aCargoCount--
            else
                _aCargoCount = 0

            cargoCount.text = _aCargoCount.toString()
            true
        }

        addCargoButton.setOnClickListener() {
            if (_aCargoCount <= 0)
                _aCargoCount = 1
            else
                _aCargoCount++
            cargoCount.text = _aCargoCount.toString()
            true
        }
        minusHatchButton.setOnClickListener() {

            if (_aHatchCount > 0)
                _aHatchCount--
            else
                _aHatchCount = 0
            hatchCount.text = _aHatchCount.toString()
            true
        }

        addHatchButton.setOnClickListener() {
            if (_aHatchCount <= 0)
                _aHatchCount = 1
            else
                _aHatchCount++
            hatchCount.text = _aHatchCount.toString()
            true
        }

        substractIntakeErrorButton.setOnClickListener() {
            if (_aIntakeDrop > 0)
                _aIntakeDrop--
            else
                _aIntakeDrop = 0
            intakeErrorCount.text = _aIntakeDrop.toString()
            true
        }

        addIntakeErrorButton.setOnClickListener() {
            if (_aIntakeDrop <= 0)
                _aIntakeDrop = 1
            else
                _aIntakeDrop++
            intakeErrorCount.text = _aIntakeDrop.toString()
            true
        }

        minusDropButton.setOnClickListener() {

            if (_aDropCount > 0)
                _aDropCount--
            else
                _aDropCount = 0
            dropCount.text = _aDropCount.toString()
            true
        }

        addDropButton.setOnClickListener() {
            if (_aDropCount <= 0)
                _aDropCount = 1
            else
                _aDropCount++
            dropCount.text = _aDropCount.toString()
            true
        }

        startTelopButton.setOnClickListener() {
            var autonResult =  GetAutonResult()
            val intent = Intent(this, MainActivity::class.java)
            Log.d("TAG","######### Intent created")
            intent.putExtra("AutonResult", autonResult)
            Log.d("TAG","######### auton saved in INTENT")
            startActivity(intent)
        }

    }

    fun GetAutonResult(): AutonResult {
        var autonResult = AutonResult()
        autonResult.cargoCount = _aCargoCount
        autonResult.hatchCount = _aHatchCount
        autonResult.intakeDrops = _aIntakeDrop
        autonResult.drops = _aDropCount

        autonResult.startingPosition = findViewById<Spinner>(R.id.startingPosition).selectedItemPosition
        autonResult.preload = findViewById<Spinner>(R.id.preload).selectedItemPosition
        autonResult.movement = findViewById<Spinner>(R.id.movement).selectedItemPosition

        return autonResult
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        Log.d("TAG", "########## SAVING DATA #############")


        outState?.putInt("_aDropCount", _aDropCount)
        outState?.putInt("_aIntakeDrop", _aIntakeDrop)
        outState?.putInt("_aCargoCount", _aCargoCount)
        outState?.putInt("_aHatchCount", _aHatchCount)

    }

}
