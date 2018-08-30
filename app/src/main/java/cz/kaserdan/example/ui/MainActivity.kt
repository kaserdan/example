package cz.kaserdan.example.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.kaserdan.example.R
import cz.kaserdan.example.ui.list.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListFragment.newInstance())
                    .commitNow()
        }
    }

}
