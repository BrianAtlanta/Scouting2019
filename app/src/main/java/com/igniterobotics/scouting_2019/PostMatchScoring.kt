package com.igniterobotics.scouting_2019

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.igniterobotics.scouting_2019.Models.MatchResult

import kotlinx.android.synthetic.main.activity_post_match_scoring.*

class PostMatchScoring : AppCompatActivity() {

    lateinit var _matchResult: MatchResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_match_scoring)
        setSupportActionBar(toolbar)

        var data = intent.extras
        _matchResult = data?.getParcelable<MatchResult>("MatchResult")!!

        var submitScoresButton = findViewById<Button>(R.id.submitScoresButton)
        submitScoresButton.setOnClickListener(){


            val intent = Intent(this, MatchSelection::class.java)
            intent.putExtra("NextMatch", _matchResult.matchNumber +1)
            startActivity(intent)
        }

        setTitle("Team " + _matchResult.teamNumber.toString() + " - Post Match Scoring")
    }

}
