package cz.kaserdan.example.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import cz.kaserdan.example.R
import cz.kaserdan.example.navigation.NavigationCommander
import cz.kaserdan.example.ui.list.ListFragment
import cz.kaserdan.example.util.rx.AutoDisposable
import cz.kaserdan.example.util.rx.AutoDisposable.Companion.addTo
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    internal lateinit var navigationCommander: NavigationCommander

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> = dispatchingAndroidInjector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            replaceFragment(ListFragment.newInstance(), false)
        }
    }

    override fun onResume() {
        super.onResume()
        navigationCommander.replaceFragment.subscribe { fragment -> replaceFragment(fragment, true) }.addTo(AutoDisposable(lifecycle))
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .apply { if (addToBackStack) addToBackStack(null) }
                .commit()
    }

}
