package com.igniterobotics.scouting_2019

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.RadioButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.beust.klaxon.Klaxon
import com.google.firebase.firestore.FirebaseFirestore
import com.igniterobotics.scouting_2019.Enums.Movement
import com.igniterobotics.scouting_2019.Enums.StartingPosition
import com.igniterobotics.scouting_2019.Models.AutonResult
import com.igniterobotics.scouting_2019.Models.MatchResult
import com.igniterobotics.scouting_2019.Models.SummaryResult

import kotlinx.android.synthetic.main.activity_post_match_scoring.*
import kotlin.math.absoluteValue

class PostMatchScoring : AppCompatActivity() {

    lateinit var _matchResult: MatchResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_match_scoring)
        setSupportActionBar(toolbar)

        var data = intent.extras
        _matchResult = data?.getParcelable<MatchResult>("MatchResult")!!

        var submitScoresButton = findViewById<Button>(R.id.submitScoresButton)
        var cancelButton = findViewById<Button>(R.id.cancelPostMatch)

        cancelButton.setOnClickListener(){
            val intent = Intent(this, MatchSelection::class.java)
            startActivity(intent)
        }
        submitScoresButton.setOnClickListener(){


            populatePostMatchResults()
            displayResults()


            val db = FirebaseFirestore.getInstance()

            val item = mapOf(
                "match" to _matchResult.matchNumber,
                "team" to _matchResult.teamNumber,
                "data" to Klaxon().toJsonString(_matchResult)
            )

            db.collection("matches").document("Q${_matchResult.matchNumber}-${_matchResult.teamNumber}")
                .set(item)
                .addOnSuccessListener { doc ->
                    Log.d("TAG","Add success:")
                }
                .addOnFailureListener { e ->
                    Log.w("Add fail", e)
                }



            val intent = Intent(this, MatchSelection::class.java)
            // removed autoinccrement match number
            // intent.putExtra("NextMatch", _matchResult.matchNumber +1)
            startActivity(intent)

        }

        setTitle("Team " + _matchResult.teamNumber.toString() + " - Post Match Scoring")
    }



    /*private fun UpdateSummary(summaryResult: SummaryResult) {

        val item = mapOf(
            "team" to summaryResult.teamNumber,
            "data" to Klaxon().toJsonString(summaryResult)
        )
        val db = FirebaseFirestore.getInstance()
        db.collection("matches").document("")
           // .whereEqualTo("team", summaryResult.teamNumber)
            .delete
            .addOnSuccessListener { }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
    }

     */

    fun populatePostMatchResults(){
        var attemptedId = findViewById<RadioGroup>(R.id.HabAttemped).checkedRadioButtonId
        if (attemptedId != -1) {
            val selected:RadioButton = findViewById(attemptedId)
            when (selected.text) {
                "Level 1" -> _matchResult.postMatchResult.attemptedLevel = 1
                "Level 2" -> _matchResult.postMatchResult.attemptedLevel = 2
                "Level 3" -> _matchResult.postMatchResult.attemptedLevel = 3
            }
        }
        else {
            _matchResult.postMatchResult.attemptedLevel = 0
        }

        var scoredId = findViewById<RadioGroup>(R.id.HabScored).checkedRadioButtonId
        if (scoredId != -1) {
            val selected:RadioButton = findViewById(scoredId)
            when (selected.text) {
                "Level 1" -> _matchResult.postMatchResult.scoredLevel = 1
                "Level 2" -> _matchResult.postMatchResult.scoredLevel = 2
                "Level 3" -> _matchResult.postMatchResult.scoredLevel = 3
            }
        }
        else {
            _matchResult.postMatchResult.scoredLevel = 0
        }
        _matchResult.postMatchResult.redCard = findViewById<CheckBox>(R.id.redCard).isChecked
        _matchResult.postMatchResult.yellowCard = findViewById<CheckBox>(R.id.yellowCard).isChecked
        _matchResult.postMatchResult.habRp = findViewById<CheckBox>(R.id.habRp).isChecked
        _matchResult.postMatchResult.rocketRp = findViewById<CheckBox>(R.id.rocketRp).isChecked
        _matchResult.postMatchResult.rocketLevel2 = findViewById<CheckBox>(R.id.rocketLevel2).isChecked
        _matchResult.postMatchResult.rocketLevel3 = findViewById<CheckBox>(R.id.rocketLevel3).isChecked
    }

    fun displayResults(){
        displayMatchLevelResults()
        displayPreMatchResults()
        displayAutoResults()
        displayTeleopResults()
        displayPostMatchResults()
    }

    fun displayPreMatchResults() {
        Log.d("TAG", "=========== Pre-Match Properties ==========")
        Log.d("TAG", "Preload : " + _matchResult.preMatchResult.preload)
        Log.d("TAG", "Starting Position : " + _matchResult.preMatchResult.startingPosision)
    }
    fun displayAutoResults() {
        Log.d("TAG", "=========== Auton Properties ==========")
        Log.d("TAG", "Hatches : " + _matchResult.autonResult.hatchCount)
        Log.d("TAG", "Cargo : " + _matchResult.autonResult.cargoCount)
        Log.d("TAG", "Intake Drop : " + _matchResult.autonResult.intakeDrop)
        Log.d("TAG", "Item Drop : " + _matchResult.autonResult.itemDrops)

    }

    fun displayTeleopResults() {
        Log.d("TAG", "=========== Teleop Properties ==========")
        Log.d("TAG", "Hatches : " + _matchResult.telopResult.hatchCount)
        Log.d("TAG", "Cargo : " + _matchResult.telopResult.cargoCount)
        Log.d("TAG", "Intake Drop : " + _matchResult.telopResult.intakeDrops)
        Log.d("TAG", "Item Drop : " + _matchResult.telopResult.drops)

    }

    fun displayMatchLevelResults(){
        Log.d("TAG", "=========== Match Properties ==========")
        Log.d("TAG", "Team Number : " + _matchResult.teamNumber.toString())
        Log.d("TAG", "Alliance : " + _matchResult.alliance)
    }
    fun displayPostMatchResults() {
        Log.d("TAG", "=========== Post Match Results ==========")
        Log.d("TAG", "Attempted Hab : " + _matchResult.postMatchResult.attemptedLevel.toString())
        Log.d("TAG", "Scored Hab : " + _matchResult.postMatchResult.scoredLevel.toString())
        Log.d("TAG", "Scored Rocket 2 : " + _matchResult.postMatchResult.rocketLevel2.toString())
        Log.d("TAG", "Scored Rocket 3" + _matchResult.postMatchResult.rocketLevel3.toString())
        Log.d("TAG", "Red Card : " + _matchResult.postMatchResult.redCard.toString())
        Log.d("TAG", "Yellow Card : " + _matchResult.postMatchResult.yellowCard.toString())
        Log.d("TAG", "Rocket RP : " + _matchResult.postMatchResult.rocketRp.toString())
        Log.d("TAG", "Hab RP : " + _matchResult.postMatchResult.habRp.toString())

    }

    override fun onBackPressed() {
        true

    }

}
