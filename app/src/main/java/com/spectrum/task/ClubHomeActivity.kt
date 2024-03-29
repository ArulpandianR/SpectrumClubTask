package com.spectrum.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spectrum.task.main.CompanyFragment
import com.spectrum.task.main.MemberFragment
import com.spectrum.task.model.Member

class ClubHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.club_home_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CompanyFragment.newInstance())
                .commitNow()
        }
    }

    fun showMemberFragment(memberList: ArrayList<Member>) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, MemberFragment.newInstance(memberList))
            .addToBackStack("member")
            .commit()
    }

}
