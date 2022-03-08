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
import com.application.poem_poet.ui.main.MainPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter


class JobUserFragment : MvpAppCompatFragment(), JobUserView {

    @InjectPresenter
    lateinit var jobUserPresenter: JobUserPresenter
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }
    lateinit var binding: FragmentJobUserBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJobUserBinding.bind(view)
        binding.jobUserBackImg.setOnClickListener {
            findNavController().navigate(R.id.action_jobUserFragment_to_profileFragment)
        }
        jobUserPresenter.getData()
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
        contextActivity.communityPresenter.setCheckDetailedFragment("FromProfile")
        val bundle = Bundle()
        with(bundle){
            putString("username", model.username)
            putString("titlePoem", model.titlePoem)
            putString("namePoet", model.namePoet)
            putString("poem", model.poem)
            putString("avatar", model.avatar)
            putInt("like",model.like)
            putString("id", model.id)
            putString("uid", model.uid)
            putString("genre", model.genre)
        }
        findNavController().navigate(R.id.detailedPoemFragment, bundle)
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