package com.igniterobotics.scouting_2019

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_post_match_scoring.*

class PostMatchScoring : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_match_scoring)
        setSupportActionBar(toolbar)

    }

}
