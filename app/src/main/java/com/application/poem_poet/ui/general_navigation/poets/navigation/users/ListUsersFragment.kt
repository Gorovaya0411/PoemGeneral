package com.application.poem_poet.ui.general_navigation.poets.navigation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.databinding.FragmentListUsersBinding
import com.application.poem_poet.model.PoemAnswer
import moxy.MvpAppCompatFragment
import com.application.poem_poet.R
import com.application.poem_poet.ui.community.CommunityActivity
import moxy.presenter.InjectPresenter

class ListUsersFragment : MvpAppCompatFragment(), ListUserView {

    @InjectPresenter
    lateinit var poemsUsersPresenter: ListUsersPresenter
    lateinit var binding: FragmentListUsersBinding
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListUsersBinding.bind(view)
        binding.listUsersCountrySearch.onActionViewCollapsed()
        binding.listUsersClose.visibility = ImageView.INVISIBLE
        poemsUsersPresenter.getData()
    }

    override fun workWithSearchWidget(model: AdapterListUser) {
        binding.listUsersCountrySearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.listUsersClose.visibility = ImageView.VISIBLE
                binding.listUsersClose.setOnClickListener {
                    binding.listUsersCountrySearch.onActionViewCollapsed()
                    binding.listUsersClose.visibility = ImageView.INVISIBLE
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

    override fun workWithAdapter(model: AdapterListUser) {
        binding.listUsersRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.listUsersRecyclerView.adapter = model
    }
}

