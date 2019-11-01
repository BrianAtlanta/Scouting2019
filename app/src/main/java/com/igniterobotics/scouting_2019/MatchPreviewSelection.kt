package com.igniterobotics.scouting_2019

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.igniterobotics.scouting_2019.Models.PreviewTeamList


class MatchPreviewSelection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_preview_selection)

        var redBot1 = findViewById<EditText>(R.id.redBot1)
        var redBot2 = findViewById<EditText>(R.id.redBot2)
        var redBot3 = findViewById<EditText>(R.id.redBot3)

        var blueBot1 = findViewById<EditText>(R.id.blueBot1)
        var blueBot2 = findViewById<EditText>(R.id.blueBot2)
        var blueBot3 = findViewById<EditText>(R.id.blueBot3)

        var previewButton = findViewById<Button>(R.id.previewButton)
        var returnToScoutingButton = findViewById<Button>(R.id.returnToScoutingButton)

        returnToScoutingButton.setOnClickListener() {
            val intent = Intent(this, MatchSelection::class.java)
            startActivity(intent)
        }

        previewButton.setOnClickListener() {

            var redTeam1 = if (!redBot1.text.isNullOrBlank()) redBot1.text.toString().toInt() else 0
            var redTeam2 = if (!redBot2.text.isNullOrBlank()) redBot2.text.toString().toInt() else 0
            var redTeam3 = if (!redBot3.text.isNullOrBlank()) redBot3.text.toString().toInt() else 0
            var blueTeam1 = if (!blueBot1.text.isNullOrBlank()) blueBot1.text.toString().toInt() else 0
            var blueTeam2 = if (!blueBot2.text.isNullOrBlank()) blueBot2.text.toString().toInt() else 0
            var blueTeam3 = if (!blueBot3.text.isNullOrBlank()) blueBot3.text.toString().toInt() else 0

            var teams = PreviewTeamList(redTeam1, redTeam2, redTeam3, blueTeam1, blueTeam2, blueTeam3)

            val intent = Intent(this, MatchPreview::class.java)
            intent.putExtra("teams", teams)
            startActivity(intent)
        }
    }
}
