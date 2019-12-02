package com.spectrum.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spectrum.task.ui.main.ClubFragment

class ClubHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.club_home_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ClubFragment.newInstance())
                .commitNow()
        }
    }

}
