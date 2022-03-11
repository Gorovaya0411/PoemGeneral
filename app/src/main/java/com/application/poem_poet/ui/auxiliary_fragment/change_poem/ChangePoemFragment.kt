package com.application.poem_poet.ui.auxiliary_fragment.change_poem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.findNavController
import com.application.poem_poet.R
import com.application.poem_poet.databinding.FragmentChangePoemBinding
import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.ui.community.CommunityActivity
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class ChangePoemFragment : MvpAppCompatFragment(), ChangePoemView {
    private lateinit var namePoetModified: String
    lateinit var binding: FragmentChangePoemBinding
    private lateinit var namePoet: String
    private lateinit var titlePoem: String
    private lateinit var genre: String
    private lateinit var poem: String
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }

    @InjectPresenter
    lateinit var changePoemPresenter: ChangePoemPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_change_poem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChangePoemBinding.bind(view)
        with(binding) {
            changePoemPresenter.receivingNamePoet()
            changePoemTitleEditTxt.setText(arguments?.getString("titlePoem"))

            namePoetModified = arguments?.getString("namePoet")!!.replace("|", ".", true)

            if (arguments?.getString("namePoet")!! == "") {
                changePoemAutoCompleteTxt.visibility = AutoCompleteTextView.INVISIBLE
                changePoemNameTxt.text = arguments?.getString("username")!!
            } else {
                changePoemNameTxt.visibility = AutoCompleteTextView.INVISIBLE
                changePoemAutoCompleteTxt.setText(namePoetModified)
            }
            changePoemGenreEditTxt.setText(arguments?.getString("genre")!!)
            changePoemPoemEditTxt.setText(arguments?.getString("poem")!!)

            changePoemAddBtn.setOnClickListener {
                titlePoem = changePoemTitleEditTxt.text.toString()
                genre = changePoemGenreEditTxt.text.toString()
                poem = changePoemPoemEditTxt.text.toString()
                namePoet = changePoemAutoCompleteTxt.text.toString()
                changePoemPresenter.changePoem(
                    arguments?.getString("id")!!, contextActivity,
                    PoemHelp(
                        arguments?.getString("username")!!,
                        titlePoem,
                        namePoet,
                        genre,
                        poem,
                        arguments?.getString("avatar")!!,
                        arguments?.getString("uid")!!
                    ), arguments?.getInt("like")!!
                )
            }
        }
    }

    override fun back() {
        val bundle = Bundle()
        with(bundle) {
            putString("username", arguments?.getString("username")!!)
            putString("titlePoem", titlePoem)
            putString("namePoet", namePoet)
            putString("poem", poem)
            putString("avatar", arguments?.getString("avatar")!!)
            putInt("like", arguments?.getInt("like")!!)
            putString("id", arguments?.getString("id")!!)
            putString("uid", arguments?.getString("uid")!!)
            putString("genre", genre)
        }
        findNavController().navigate(R.id.detailedPoemFragment, bundle)
    }

    override fun workWithAutoTxt(array: MutableList<String>) {
        with(binding) {
            val adapter = ArrayAdapter(
                contextActivity, android.R.layout.simple_dropdown_item_1line, array
            )
            changePoemAutoCompleteTxt.setAdapter(adapter)
            changePoemAutoCompleteTxt.threshold = 1
            changePoemAutoCompleteTxt.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _,
                                                  position, _ ->
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    namePoet = selectedItem
                }
        }
    }
}
