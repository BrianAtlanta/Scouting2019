package com.igniterobotics.scouting_2019

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.Chronometer
import android.os.SystemClock
import android.util.Log
import com.igniterobotics.scouting_2019.Models.AutonResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isDefenseTimerOn = false
    private var isClimbTimerOn = false
    private var timeWhenClimbStopped: Long = 0
    private var timeWhenDefenseStopped: Long = 0
    private var _cargoCount = 0
    private var _hatchCount = 0
    private var _intakeDrop = 0
    private var _dropCount = 0
    private var _timingDefense = false
    private var _timingClimb = false
    private var _startOfTeleop: Long = 0
    private var _dStart: Long = 0
    private var _dStop: Long = 0
    private var _autonResult = AutonResult()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        var endTelopButton = findViewById<Button>(R.id.endTelopButton)

        var cargoCount = findViewById<TextView>(R.id.cargoCount)
        var hatchCount = findViewById<TextView>(R.id.hatchCount)
        var intakeErrorCount = findViewById<TextView>(R.id.intakeError)
        var dropCount = findViewById<TextView>(R.id.dropCount)
        //var defenseButton = findViewById<Button>(R.id.defenseTime)
        Log.d("TAG", "########## CREATING #############")
        if (savedInstanceState != null)
        {
            Log.d("TAG", "########## RESTORING STATE #############")
            _cargoCount = savedInstanceState.run { getInt("_cargoCount") }
            cargoCount.text = _cargoCount.toString()

            _dropCount = savedInstanceState.run { getInt("_dropCount") }
            dropCount.text = _dropCount.toString()

            _intakeDrop = savedInstanceState.run { getInt("_intakeDrop") }
            intakeErrorCount.text = _intakeDrop.toString()

            _hatchCount = savedInstanceState.run { getInt("_hatchCount") }
            hatchCount.text = _hatchCount.toString()

            timeWhenClimbStopped = savedInstanceState.run { getLong("timeWhenClimbStopped") }
            var climbBase = savedInstanceState.run { getLong("climbTimer")}
            climbTimer.setBase(savedInstanceState.run { SystemClock.elapsedRealtime() + timeWhenClimbStopped })

            timeWhenDefenseStopped = savedInstanceState.run { getLong("timeWhenDefenseStopped") }
            var defenseBase = savedInstanceState.run { getLong("defenseTimer") }
            defenseTimer.setBase(SystemClock.elapsedRealtime() + timeWhenDefenseStopped)
        }
        else {
            Log.d("TAG","######### Getting data from the Intent")
            this._autonResult = (intent.getParcelableExtra("AutonResult") as? AutonResult)!!
            _cargoCount = _autonResult.cargoCount
            _hatchCount = _autonResult.hatchCount
            _dropCount = _autonResult.drops
            _intakeDrop = _autonResult.intakeDrops

            cargoCount.text = _cargoCount.toString()
            dropCount.text = _dropCount.toString()
            intakeErrorCount.text = _intakeDrop.toString()
            hatchCount.text = _hatchCount.toString()
            _startOfTeleop = System.currentTimeMillis()
        }
        defenseTimerButton.setOnClickListener() {
            if (!isDefenseTimerOn)
            {
                _dStart = System.currentTimeMillis()

                defenseTimer.setBase(SystemClock.elapsedRealtime() + timeWhenDefenseStopped)
                defenseTimer.start();
                isDefenseTimerOn = true;
                defenseTimerButton.text = "Start " + ((_dStart - _startOfTeleop)).toString()
            }
            else
            {
                _dStop = System.currentTimeMillis()
                timeWhenDefenseStopped = defenseTimer.getBase() - SystemClock.elapsedRealtime();
                defenseTimer.stop()
                isDefenseTimerOn = false
                defenseTimerButton.text = "Stop " + ((_dStop - _startOfTeleop)).toString()
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
                climbTimerButton.text = "Start Climb"
            }
        }

        minusCargoButton.setOnClickListener() {
            if (_cargoCount > 0)
                _cargoCount--
            else
                _cargoCount = 0

            cargoCount.text = _cargoCount.toString()
            true
        }

        addCargoButton.setOnClickListener() {
            if (_cargoCount <= 0)
                _cargoCount = 1
            else
                _cargoCount++
            cargoCount.text = _cargoCount.toString()
            true
        }

        minusHatchButton.setOnClickListener() {

            if (_hatchCount > 0)
                _hatchCount--
            else
                _hatchCount = 0
            hatchCount.text = _hatchCount.toString()
            true
        }

        addHatchButton.setOnClickListener() {
            if (_hatchCount <= 0)
                _hatchCount = 1
            else
                _hatchCount++
            hatchCount.text = _hatchCount.toString()
            true
        }

        substractIntakeErrorButton.setOnClickListener() {
            if (_intakeDrop > 0)
                _intakeDrop--
            else
                _intakeDrop = 0
            intakeErrorCount.text = _intakeDrop.toString()
            true
        }

        addIntakeErrorButton.setOnClickListener() {
            if (_intakeDrop <= 0)
                _intakeDrop = 1
            else
                _intakeDrop++
            intakeErrorCount.text = _intakeDrop.toString()
            true
        }

        minusDropButton.setOnClickListener() {

            if (_dropCount > 0)
                _dropCount--
            else
                _dropCount = 0
            dropCount.text = _dropCount.toString()
            true
        }

        addDropButton.setOnClickListener() {
            if (_dropCount <= 0)
                _dropCount = 1
            else
                _dropCount++
            dropCount.text = _dropCount.toString()
            true
        }

        endTelopButton.setOnClickListener(){

            val intent = Intent(this, PostMatchScoring::class.java)
            startActivity(intent)
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        Log.d("TAG", "########## SAVING DATA #############")

        var climb = climbTimer.getBase()
        Log.d("TAG", "#################    climb time : " + climb.toString())
        outState?.putInt("_dropCount", _dropCount)
        outState?.putInt("_intakeDrop", _intakeDrop)
        outState?.putInt("_cargoCount", _cargoCount)
        outState?.putInt("_hatchCount", _hatchCount)
        outState?.putLong("climbTime", climbTimer.getBase())
        outState?.putLong("defenseTime", defenseTimer.getBase())
        outState?.putLong("timeWhenDefenseStopped", timeWhenDefenseStopped)
        outState?.putLong("timeWhenClimbStopped", timeWhenClimbStopped)
        }
}
