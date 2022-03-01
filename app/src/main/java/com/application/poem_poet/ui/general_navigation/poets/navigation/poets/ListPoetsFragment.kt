package com.application.poem_poet.ui.general_navigation.poets.navigation.poets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentListPoetsBinding
import com.application.poem_poet.model.PoemAnswer
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ListPoetsFragment : MvpAppCompatFragment(), ListPoetsView {

    private val poemsPoetsPresenter by moxyPresenter { ListPoetsPresenter() }
    lateinit var binding: FragmentListPoetsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_poets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListPoetsBinding.bind(view)
        poemsPoetsPresenter.getData()
    }

    override fun workWithSearchWidget(model: AdapterListPoets) {
        binding.listPoetCountrySearch.setOnQueryTextListener(object :
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

    override fun workWithAdapter(model: AdapterListPoets) {
        binding.listPoetRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.listPoetRecyclerView.adapter = model
    }
}





