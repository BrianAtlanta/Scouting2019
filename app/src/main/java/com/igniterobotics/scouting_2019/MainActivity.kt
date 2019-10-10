package com.igniterobotics.scouting_2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Chronometer
import android.os.SystemClock
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var timingDefense = false
        var timingClimb = false
        // Get buttons
        var minusCargoButton = findViewById<Button>(R.id.minusCargoButton)
        var addCargoButton = findViewById<Button>(R.id.addCargoButton)
        var minusHatchButton = findViewById<Button>(R.id.minusHatchButton)
        var addHatchButton = findViewById<Button>(R.id.addHatchButton)
        var addIntakeErrorButton = findViewById<Button>(R.id.addIntakeError)
        var substractIntakeErrorButton = findViewById<Button>(R.id.substractIntakeError)
        var addDropButton = findViewById<Button>(R.id.addDropButton)
        var minusDropButton = findViewById<Button>(R.id.minusDropButton)
        var defenseTimer = findViewById<Chronometer>(R.id.defenseTimer)
        var climbTimer = findViewById<Chronometer>(R.id.climbTimer)
        var defenseTimerButton = findViewById<Button>(R.id.defenseButton)
        var climbTimerButton = findViewById<Button>(R.id.climbTimerButton)
        //var defenseButton = findViewById<Button>(R.id.defenseTime)
        var isDefenseTimerOn = false
        var isClimbTimerOn = false
        var timeWhenClimbStopped: Long = 0
        var timeWhenDefenseStopped: Long = 0

        defenseTimerButton.setOnClickListener() {
            if (!isDefenseTimerOn)
            {
                defenseTimer.setBase(SystemClock.elapsedRealtime() + timeWhenDefenseStopped)
                defenseTimer.start();
                isDefenseTimerOn = true;
                defenseTimerButton.text = "Stop Defense"
            }
            else
            {
                timeWhenDefenseStopped = defenseTimer.getBase() - SystemClock.elapsedRealtime();
                defenseTimer.stop()
                isDefenseTimerOn = false
                defenseTimerButton.text = "Start Defense"
            }
        }

        climbTimerButton.setOnClickListener() {
            if (!isClimbTimerOn)
            {
                climbTimer.setBase(SystemClock.elapsedRealtime() + timeWhenClimbStopped)
                climbTimer.start();
                isClimbTimerOn = true;
                climbTimerButton.text = "Stop Climb"
            }
            else
            {
                timeWhenClimbStopped = climbTimer.getBase() - SystemClock.elapsedRealtime();
                climbTimer.stop()
                isClimbTimerOn = false
                climbTimerButton.text = "Start Defense"
            }
        }
        minusCargoButton.setOnClickListener() {

            var cargoCount =  findViewById(R.id.cargoCount) as TextView
            var cnt = cargoCount.text.toString().toInt()
            if (cnt > 0)
                cnt--
            else
                cnt = 0

            cargoCount.text = cnt.toString()
            true
        }

        addCargoButton.setOnClickListener() {
            var cargoCount =  findViewById(R.id.cargoCount) as TextView
            var cnt = cargoCount.text.toString().toInt()
            if (cnt <= 0)
                cnt = 1
            else
                cnt++
            cargoCount.text = cnt.toString()
            true
        }

        minusHatchButton.setOnClickListener() {
            var hatchCount = findViewById(R.id.hatchCount) as TextView
            var cnt = hatchCount.text.toString().toInt()
            if (cnt > 0)
                cnt--
            else
                cnt = 0
            hatchCount.text = cnt.toString()
            true
        }

        addHatchButton.setOnClickListener() {
            var hatchCount = findViewById(R.id.hatchCount) as TextView
            var cnt = hatchCount.text.toString().toInt()
            if (cnt <= 0)
                cnt = 1
            else
                cnt++
            hatchCount.text = cnt.toString()
            true
        }

        substractIntakeErrorButton.setOnClickListener() {
            var intakeErrorCount = findViewById(R.id.intakeError) as TextView
            var cnt = intakeErrorCount.text.toString().toInt()
            if (cnt > 0)
                cnt--
            else
                cnt = 0
            intakeErrorCount.text = cnt.toString()
            true
        }

        addIntakeErrorButton.setOnClickListener() {
            var intakeErrorCount = findViewById(R.id.intakeError) as TextView
            var cnt = intakeErrorCount.text.toString().toInt()
            if (cnt <= 0)
                cnt = 1
            else
                cnt++
            intakeErrorCount.text = cnt.toString()
            true
        }

        minusDropButton.setOnClickListener() {
            var dropCount = findViewById(R.id.dropCount) as TextView
            var cnt = dropCount.text.toString().toInt()
            if (cnt > 0)
                cnt--
            else
                cnt = 0
            dropCount.text = cnt.toString()
            true
        }

        addDropButton.setOnClickListener() {
            var dropCount = findViewById(R.id.dropCount) as TextView
            var cnt = dropCount.text.toString().toInt()
            if (cnt <= 0)
                cnt = 1
            else
                cnt++
            dropCount.text = cnt.toString()
            true
        }


    }
}
