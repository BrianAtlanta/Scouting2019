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
import com.igniterobotics.scouting_2019.Enums.Movement
import com.igniterobotics.scouting_2019.Enums.Preload
import com.igniterobotics.scouting_2019.Enums.StartingPosition
import com.igniterobotics.scouting_2019.Models.AutonResult
import com.igniterobotics.scouting_2019.Models.MatchResult
import com.igniterobotics.scouting_2019.Models.DefensedPeriod
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import kotlin.collections.mutableListOf
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.ceil

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
    private var _defensePeriod = DefensedPeriod(0.0,0.0)
    private var _cargoTimestamps = ArrayList<Double>()
    private var _hatchTimestamps = ArrayList<Double>()
    private var _intakeErrorTimestamps = ArrayList<Double>()
    private var _dropTimestamps = ArrayList<Double>()
    private var _defenseList = ArrayList<DefensedPeriod>()
    private lateinit var _matchResult: MatchResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var data = intent.extras
        _matchResult = data?.getParcelable<MatchResult>("MatchResult")!!
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
        var cancelButton = findViewById<Button>(R.id.cancelTelop)

        var cargoCount = findViewById<TextView>(R.id.cargoCount)
        var hatchCount = findViewById<TextView>(R.id.hatchCount)
        var intakeErrorCount = findViewById<TextView>(R.id.intakeError)
        var dropCount = findViewById<TextView>(R.id.dropCount)

        setTitle("Team " + _matchResult.teamNumber.toString() + " - Teleop Scoring")
        //var defenseButton = findViewById<Button>(R.id.defenseTime)


            _cargoCount = _matchResult.autonResult.cargoCount
            _hatchCount = _matchResult.autonResult.hatchCount
            _dropCount = _matchResult.autonResult.itemDrops
            _intakeDrop = _matchResult.autonResult.intakeDrop

            cargoCount.text = _cargoCount.toString()
            dropCount.text = _dropCount.toString()
            intakeErrorCount.text = _intakeDrop.toString()
            hatchCount.text = _hatchCount.toString()
            _startOfTeleop = System.currentTimeMillis()


        cancelButton.setOnClickListener(){
            val intent = Intent(this, MatchSelection::class.java)
            startActivity(intent)
        }

        defenseTimerButton.setOnClickListener() {
            if (!isDefenseTimerOn)
            {
                _defensePeriod = DefensedPeriod(GetTimeStamp(),0.0)
                defenseTimer.setBase(SystemClock.elapsedRealtime() + timeWhenDefenseStopped)
                defenseTimer.start();
                isDefenseTimerOn = true;
                defenseTimerButton.text = "Stop Defense"
            }
            else
            {
                _defensePeriod.defenseStopped = GetTimeStamp()
                _defenseList.add((_defensePeriod))
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
                _matchResult.telopResult.climbTime = ceil(timeWhenClimbStopped / 1000.0).toInt()
                isClimbTimerOn = false
                climbTimerButton.text = "Start Climb"
            }
        }



        minusCargoButton.setOnClickListener() {
            if (_cargoCount > 0) {
                RemoveTimestamp(_cargoTimestamps)
                _cargoCount--
            }
            else
                _cargoCount = 0

            cargoCount.text = _cargoCount.toString()
        }

        addCargoButton.setOnClickListener() {
            if (_cargoCount <= 0)
                _cargoCount = 1
            else
                _cargoCount++
            Log.d("TAG", "Cargo Add at Timestamp " + GetTimeStamp())
            AddTimestamp(_cargoTimestamps)
            cargoCount.text = _cargoCount.toString()
        }

        minusHatchButton.setOnClickListener() {

            if (_hatchCount > 0) {
                RemoveTimestamp(_hatchTimestamps)
                _hatchCount--
            }
            else
                _hatchCount = 0
            hatchCount.text = _hatchCount.toString()
        }

        addHatchButton.setOnClickListener() {
            if (_hatchCount <= 0)
                _hatchCount = 1
            else
                _hatchCount++
            Log.d("TAG", "Hatch Add at Timestamp " + GetTimeStamp())
            AddTimestamp(_hatchTimestamps)
            hatchCount.text = _hatchCount.toString()
        }

        substractIntakeErrorButton.setOnClickListener() {
            if (_intakeDrop > 0) {
                RemoveTimestamp(_intakeErrorTimestamps)
                _intakeDrop--
            }
            else
                _intakeDrop = 0
            intakeErrorCount.text = _intakeDrop.toString()

        }

        addIntakeErrorButton.setOnClickListener() {
            if (_intakeDrop <= 0)
                _intakeDrop = 1
            else
                _intakeDrop++
            Log.d("TAG", "Intake Error at Timestamp " + GetTimeStamp())
            AddTimestamp(_intakeErrorTimestamps)
            intakeErrorCount.text = _intakeDrop.toString()

        }

        minusDropButton.setOnClickListener() {

            if (_dropCount > 0) {
                RemoveTimestamp(_dropTimestamps)
                _dropCount--
            }
            else
                _dropCount = 0
            dropCount.text = _dropCount.toString()

        }

        addDropButton.setOnClickListener() {
            if (_dropCount <= 0)
                _dropCount = 1
            else
                _dropCount++
            Log.d("TAG", "Item Drop at Timestamp " + GetTimeStamp())
            AddTimestamp(_dropTimestamps)
            dropCount.text = _dropCount.toString()
        }

        endTelopButton.setOnClickListener(){

            if (_cargoCount > _matchResult.autonResult.cargoCount)
                _matchResult.telopResult.cargoCount = _cargoCount - _matchResult.autonResult.cargoCount
            else
                _matchResult.autonResult.cargoCount = _cargoCount

            if (_hatchCount > _matchResult.autonResult.hatchCount)
                _matchResult.telopResult.hatchCount = _hatchCount - _matchResult.autonResult.hatchCount
            else
                _matchResult.autonResult.hatchCount = _hatchCount

            if (_intakeDrop > _matchResult.autonResult.itemDrops)
                _matchResult.telopResult.intakeDrops = _intakeDrop - _matchResult.autonResult.itemDrops
            else
                _matchResult.autonResult.itemDrops = _intakeDrop

            if (_dropCount > _matchResult.autonResult.itemDrops)
                _matchResult.telopResult.drops = _dropCount - _matchResult.autonResult.itemDrops
            else
                _matchResult.autonResult.itemDrops = _intakeDrop

            _matchResult.telopResult.cargoTimestamps = _cargoTimestamps
            _matchResult.telopResult.hatchTimestamps = _hatchTimestamps
            _matchResult.telopResult.intakeDropTimestamps = _intakeErrorTimestamps
            _matchResult.telopResult.dropTimestamps = _dropTimestamps
            _matchResult.telopResult.defensePeriods = _defenseList
            val intent = Intent(this, PostMatchScoring::class.java)
            intent.putExtra("MatchResult", _matchResult)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        true

    }

    fun AddTimestamp(list: ArrayList<Double>)
    {
        list.add(GetTimeStamp())
    }

    fun RemoveTimestamp(list: ArrayList<Double>)
    {
        if (list.size > 0)
            list.removeAt(list.size - 1)
    }

    fun GetTimeStamp(): Double {
        //val decimal = BigDecimal(System.currentTimeMillis() -_startOfTeleop)/1000):ouble.setScale(2, RoundingMode.HALF_EVEN)
        var totalMs = 135000 - (System.currentTimeMillis() -_startOfTeleop)
        var timestampDouble = totalMs / 1000.0
        return timestampDouble
    }
}
