package com.application.poem_poet.ui.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.application.poem_poet.MyApplication
import com.application.poem_poet.R
import com.application.poem_poet.di.modul.ui.community.CommunityActivityModule
import com.application.poem_poet.di.modul.ui.main.MainActivityModule
import com.application.poem_poet.ui.main.MainActivityPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class CommunityActivity : MvpAppCompatActivity(), CommunityActivityView {
    @InjectPresenter
    lateinit var presenter: CommunityActivityPresenter

    @ProvidePresenter
    fun provideCommunityActivityPresenter(): CommunityActivityPresenter {
        return MyApplication.appComponent.with(CommunityActivityModule()).presenter
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.main_nav_host_container)

        bottomNavigationView.setupWithNavController(navController)

        startPostponedEnterTransition()
    }
}