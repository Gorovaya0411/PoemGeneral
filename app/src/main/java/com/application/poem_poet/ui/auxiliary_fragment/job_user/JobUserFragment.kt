package com.application.poem_poet.ui.auxiliary_fragment.job_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentJobUserBinding
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.community.CommunityActivity
import moxy.MvpAppCompatFragment
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
        binding.jobUserNoVersesTxt.visibility = TextView.INVISIBLE
        binding.jobUserHelpMessageTxt.visibility = TextView.INVISIBLE

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_jobUserFragment_to_profileFragment)
                }
            }
            )

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
        binding.jobUserNoVersesTxt.visibility = TextView.VISIBLE
        binding.jobUserHelpMessageTxt.visibility = TextView.VISIBLE
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
        findNavController().navigate(R.id.detailedPoemFragment)
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