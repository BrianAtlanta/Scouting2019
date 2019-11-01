package com.igniterobotics.scouting_2019

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
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
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.absoluteValue


class MatchSelection : AppCompatActivity() {


    var preMatchResult = PreMatchResult(StartingPosition.NotSet,Preload.NotSet)
    var autonResult = AutonResult(0,0,0,0,Movement.NotSet)
    var teleopResult = TeleopResult(0,0,0,0,0,0, ArrayList<Double>(), ArrayList<Double>(), ArrayList<Double>(), ArrayList<Double>(), ArrayList<DefensedPeriod>())
    var postMatchResult = PostMatchResult(0,0,false,false,false,false,false,false, false, false)
    var _matchResult = MatchResult(0,0,Alliance.Red, preMatchResult, autonResult, teleopResult,postMatchResult)
    var _autonResults= arrayListOf<AutonResult>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var teamList = ArrayList<Int>()

        teamList.add(832)
        teamList.add(1002)
        teamList.add(1102)
        teamList.add(1261)
        teamList.add(1311)
        teamList.add(1414)
        teamList.add(1683)
        teamList.add(1746)
        teamList.add(2974)
        teamList.add(3344)
        teamList.add(3635)
        teamList.add(4026)
        teamList.add(4112)
        teamList.add(4188)
        teamList.add(4468)
        teamList.add(4509)
        teamList.add(4516)
        teamList.add(4910)
        teamList.add(4941)
        teamList.add(5132)
        teamList.add(5293)
        teamList.add(5332)
        teamList.add(5632)
        teamList.add(5900)
        teamList.add(6341)
        teamList.add(6829)
        teamList.add(6905)
        teamList.add(6910)
        teamList.add(6925)
        teamList.add(7427)
        teamList.add(7451)
        teamList.add(7538)

         

    /*    val db = FirebaseFirestore.getInstance()

        val item = mapOf(
            "teams" to teamList
        )

        db.collection("teams").document("TeamList")
            .get()
            .addOnSuccessListener { doc ->
                teamList = Klaxon().parse<ArrayList<Int>>(doc.data?.getValue("teams"))
                Log.d("TAG","Add success:")
            }
            .addOnFailureListener { e ->
                Log.w("Add fail", e)
            }
            */



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

       var matchPreviewButton = findViewById<Button>(R.id.matchPreviewSelectionButton)


        var matches = mutableListOf<String>()
        for (num in 1..100) {
            matches.add("   Quals #" + num.toString() + "   ")
        }

        var matchSpinner = findViewById<Spinner>(R.id.matches)
        val matchAdapter = ArrayAdapter(this, R.layout.ignite_spinner, matches)
        matchAdapter.setDropDownViewResource(R.layout.ignite_spinner)
        matchSpinner.adapter = matchAdapter



        matchPreviewButton.setOnClickListener() {
            val intent = Intent(this, MatchPreviewSelection::class.java)
            startActivity(intent)
        }
        startPreMatch.setOnClickListener() {
            UpdateTeamInfo()
            if (_matchResult.teamNumber == 0) {
                var errorMsg = findViewById<TextView>(R.id.errorMsg)
                errorMsg.text = "No Team Number"
                errorMsg.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (!teamList.contains(_matchResult.teamNumber)) {
                var errorMsg = findViewById<TextView>(R.id.errorMsg)
                errorMsg.text = "Invalid Team Number"
                errorMsg.visibility = View.VISIBLE
                return@setOnClickListener
            }

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
