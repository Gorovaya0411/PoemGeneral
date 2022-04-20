package com.application.poem_poet.ui.general_navigation.my_poem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentMyPoemBinding
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.community.CommunityActivity
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class MyPoemFragment : MvpAppCompatFragment(), MyPoemView {

    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }
    lateinit var binding: FragmentMyPoemBinding

    @InjectPresenter
    lateinit var myPoemPresenter: MyPoemPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_my_poem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPoemBinding.bind(view)
        binding.myPoemNoVersesTxt.visibility = TextView.INVISIBLE
        binding.myPoemHelpMessageTxt.visibility = TextView.INVISIBLE
        myPoemPresenter.getData()
    }

    override fun showDialog(model: DialogFragment) {
        model.show(
            requireActivity().supportFragmentManager,
            "ForEmptyJobsDialog"
        )
        binding.myPoemNoVersesTxt.visibility = TextView.VISIBLE
        binding.myPoemHelpMessageTxt.visibility = TextView.VISIBLE
    }

    override fun showDeleteDialog(model: DialogFragment) {
        model.show(requireActivity().supportFragmentManager, "ForDeleteMyPoemDialog")
    }

    override fun openingNewActivity(model: PoemAnswer) {
        contextActivity.communityPresenter.setCheckDetailedFragment("FromMyPoem")
        findNavController().navigate(R.id.detailedPoemFragment)
    }

    override fun workWithAdapter(model: AdapterMyPoem) {
        binding.myPoemRecycleView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.myPoemRecycleView.adapter = model
    }

    override fun openListPoemActivity() {
        val intent = Intent(requireContext(), CommunityActivity::class.java)
        startActivity(intent)
    }

    override fun showToast() {
        Toast.makeText(
            context,
            "Успешно удалено!",
            Toast.LENGTH_LONG
        ).show()
    }
}
