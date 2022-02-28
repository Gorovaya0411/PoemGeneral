package com.application.poem_poet.ui.job_user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.DialogFragment
import com.application.poem_poet.R
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.auth.FirebaseAuth
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class JobUserFragment : MvpAppCompatFragment(), JobUserView {

    private val detailedMyJobPresenter by moxyPresenter { JobUserPresenter() }
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailedMyJobPresenter.getData()
    }

    override fun openDeleteDialog(model: DialogFragment) {
        model.show(contextActivity.supportFragmentManager, "ForDeleteMyPoemDialog")
    }

    override fun openDialog(model: DialogFragment) {
        model.show(
            contextActivity.supportFragmentManager,
            "ForEmptyJobsDialog"
        )
    }

//    override fun workWithAdapter() {
////        RecyclerMainMyJub.layoutManager =
////            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
////        RecyclerMainMyJub.adapter = model
//    }

    override fun openingNewActivity(model: PoemAnswer) {
//        val intent = Intent(this, DetailedPoemForJobActivity::class.java)
//        intent.putExtra("KEY", model)
//        startActivity(intent)
    }

    override fun toast() {
        Toast.makeText(
            contextActivity,
            "Успешно удалено!",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun openAddActivity() {
//        val intent = Intent(this, AddPoemActivity::class.java)
//        startActivity(intent)
    }
}