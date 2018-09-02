package cz.kaserdan.example.navigation

import android.support.v4.app.Fragment
import io.reactivex.Observable

interface NavigationCommander {

    val replaceFragment: Observable<Fragment>

}