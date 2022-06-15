package com.application.poem_poet.ui.general_navigation.poets.navigation.poets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentListPoetsBinding
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.ui.community.CommunityActivity
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import org.w3c.dom.Text

class ListPoetsFragment : MvpAppCompatFragment(), ListPoetsView {

    @InjectPresenter
    lateinit var poemsPoetsPresenter: ListPoetsPresenter
    lateinit var binding: FragmentListPoetsBinding
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_poets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListPoetsBinding.bind(view)
        with(binding) {

            listPoetCountrySearch.onActionViewCollapsed()
            poemsPoetsPresenter.getData(contextActivity)
            listPoetClose.visibility = TextView.INVISIBLE

            if (!contextActivity.lackInternet()) {
                listPoetNoVersesTxt.visibility = TextView.VISIBLE
                listPoetHelpMessageTxt.visibility = TextView.VISIBLE
            } else {
                listPoetNoVersesTxt.visibility = TextView.INVISIBLE
                listPoetHelpMessageTxt.visibility = TextView.INVISIBLE
            }
        }
    }

    override fun workWithSearchWidget(model: AdapterListPoets) {
        binding.listPoetCountrySearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.listPoetClose.visibility = ImageView.VISIBLE
                binding.listPoetClose.setOnClickListener {
                    binding.listPoetCountrySearch.onActionViewCollapsed()
                    binding.listPoetClose.visibility = ImageView.INVISIBLE
                }
                model.getFilter().filter(newText)
                return false
            }
        })
    }

    override fun openingNewActivity(model: PoemAnswer) {
        contextActivity.communityPresenter.setCheckDetailedFragment("FromList")
        contextActivity.openingNewActivity(model)
    }

    override fun workWithAdapter(model: AdapterListPoets) {
        binding.listPoetRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.listPoetRecyclerView.adapter = model
    }
}





