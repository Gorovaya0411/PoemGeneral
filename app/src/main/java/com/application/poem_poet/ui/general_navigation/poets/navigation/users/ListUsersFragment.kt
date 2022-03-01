package com.application.poem_poet.ui.general_navigation.poets.navigation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.databinding.FragmentListUsersBinding
import com.application.poem_poet.model.PoemAnswer
import moxy.MvpAppCompatFragment
import androidx.appcompat.widget.SearchView
import com.application.poem_poet.R
import moxy.ktx.moxyPresenter

class ListUsersFragment : MvpAppCompatFragment(), ListUserView {

    private val poemsPoetsPresenter by moxyPresenter { ListUsersPresenter() }
    lateinit var binding: FragmentListUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListUsersBinding.bind(view)
        poemsPoetsPresenter.getData()
    }

    override fun workWithSearchWidget(model: AdapterListUser) {
        binding.listUsersCountrySearch.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                model.getFilter().filter(newText)
                return false
            }

        })
    }

    override fun openingNewActivity(model: PoemAnswer) {
//        val intent = Intent(context, DetailedPoemGeneralActivity::class.java)
//        intent.putExtra("KEY", model)
//        startActivity(intent)
    }

    override fun workWithAdapter(model: AdapterListUser) {
        binding.listUsersRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.listUsersRecyclerView.adapter = model
    }
}

