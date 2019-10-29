package com.igniterobotics.scouting_2019

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.igniterobotics.scouting_2019.BuildConfig.VERSION_NAME
import com.igniterobotics.scouting_2019.Enums.Alliance
import com.igniterobotics.scouting_2019.Enums.Movement
import com.igniterobotics.scouting_2019.Enums.Preload
import com.igniterobotics.scouting_2019.Enums.StartingPosition
import com.igniterobotics.scouting_2019.Models.*
import kotlinx.android.synthetic.main.activity_match_selection.*
import java.lang.Exception
import java.net.URL


class MatchSelection : AppCompatActivity() {


    var preMatchResult = PreMatchResult(StartingPosition.NotSet,Preload.NotSet)
    var autonResult = AutonResult(0,0,0,0,Movement.NotSet)
    var teleopResult = TeleopResult(0,0,0,0,0,0, ArrayList<Double>(), ArrayList<Double>(), ArrayList<Double>(), ArrayList<Double>(), ArrayList<DefensedPeriod>())
    var postMatchResult = PostMatchResult(0,0,false,false,false,false,false,false, false, false)
    var _matchResult = MatchResult(0,0,Alliance.Red, preMatchResult, autonResult, teleopResult,postMatchResult)
    var _autonResults= arrayListOf<AutonResult>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        _autonResults.add(AutonResult(4,2,2,0,Movement.Crossed))
        _autonResults.add(AutonResult(2,5,1,1,Movement.Crossed))
        _autonResults.add(AutonResult(5,0,0,2,Movement.Crossed))
        _autonResults.add(AutonResult(0,8,2,0,Movement.Crossed))
        _autonResults.add(AutonResult(0,10,0,0,Movement.Crossed))





        var maxHatch = 0
        var minHatch = 0
        var avgHatch = 0
        var hatchMatchCnt = 0

        var hatchMatches = _autonResults.filter { it.hatchCount > 0 }
        if (hatchMatches.count() > 0) {
            hatchMatchCnt = hatchMatches.count()
            maxHatch = (hatchMatches.maxBy { it.hatchCount })?.hatchCount as Int
            minHatch = (hatchMatches.minBy { it.hatchCount })?.hatchCount as Int
            var avgHatch = _autonResults.sumBy { it.hatchCount } / (hatchMatchCnt * 1.0)
        }
        Log.w("TAG", "################ Cnt : " + hatchMatchCnt + "  Max Hatch : " + maxHatch.toString() + "  Min Hatch : " + minHatch + "   Avg Hatch :" + avgHatch)


         */


        setContentView(R.layout.activity_match_selection)
        setTitle("GRITS 2019 Scouting - "  + VERSION_NAME)





        var matches = mutableListOf<String>()
        for (num in 1..100) {
            matches.add("   Quals #" + num.toString() + "   ")
        }

        var matchSpinner = findViewById<Spinner>(R.id.matches)
        val matchAdapter = ArrayAdapter(this, R.layout.ignite_spinner, matches)
        matchAdapter.setDropDownViewResource(R.layout.ignite_spinner)
        matchSpinner.adapter = matchAdapter




        startPreMatch.setOnClickListener() {
            UpdateTeamInfo()
            if (_matchResult.teamNumber == 0)
                return@setOnClickListener

            val intent = Intent(this, PreMatch::class.java)
            intent.putExtra("MatchResult", _matchResult)
            startActivity(intent)
        }


    }
    override fun onBackPressed() {
        true

    }
    fun UpdateTeamInfo(){

        var matchSpinner = findViewById<Spinner>(R.id.matches)
        var teamNumberTextBox = findViewById<EditText>(R.id.scoutingBotTextBox)

        _matchResult.alliance = Alliance.Red
        var scoredId = findViewById<RadioGroup>(R.id.allianceGroup).checkedRadioButtonId
        if (scoredId != -1) {
            val selected:RadioButton = findViewById(scoredId)
            when (selected.text) {
                "Blue Alliance" -> _matchResult.alliance = Alliance.Blue

            }
        }


        _matchResult.matchNumber = matchSpinner.selectedItemPosition +1;

        try {
            _matchResult.teamNumber = teamNumberTextBox.text.toString().toInt()
        }
        catch(e: Exception) {
            _matchResult.teamNumber = 0
        }

    }

}
