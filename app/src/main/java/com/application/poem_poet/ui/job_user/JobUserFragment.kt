package com.application.poem_poet.ui.job_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentJobUserBinding
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.community.CommunityActivity
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class JobUserFragment : MvpAppCompatFragment(), JobUserView {

    private val detailedMyJobPresenter by moxyPresenter { JobUserPresenter() }
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }
    lateinit var binding: FragmentJobUserBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJobUserBinding.bind(view)
        detailedMyJobPresenter.getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_job_user, container, false)
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

    override fun workWithAdapter(model: AdapterJobUser) {
        binding.jobUserRecyclerView.layoutManager =
            LinearLayoutManager(contextActivity, RecyclerView.VERTICAL, false)
        binding.jobUserRecyclerView.adapter = model
    }

    override fun openingNewActivity(model: PoemAnswer) {
        contextActivity.communityPresenter.setCheckDetailedFragment("DetJobUser")
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
        findNavController().navigate(R.id.action_jobUserFragment_to_addPoemFragment)
    }
}