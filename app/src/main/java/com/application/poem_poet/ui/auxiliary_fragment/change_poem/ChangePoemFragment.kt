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
            with(contextActivity.communityPresenter.getSavePoemHelp()) {
                changePoemPresenter.receivingNamePoet()
                changePoemTitleEditTxt.setText(titlePoem)

                namePoetModified = namePoet.replace("|", ".", true)

                if (namePoet == "") {
                    changePoemAutoCompleteTxt.visibility = AutoCompleteTextView.INVISIBLE
                    changePoemNameTxt.text = username
                } else {
                    changePoemNameTxt.visibility = AutoCompleteTextView.INVISIBLE
                    changePoemAutoCompleteTxt.setText(namePoetModified)
                }
                changePoemGenreEditTxt.setText(genre)
                changePoemPoemEditTxt.setText(poem)

                changePoemAddBtn.setOnClickListener {
                    titlePoem = changePoemTitleEditTxt.text.toString()
                    genre = changePoemGenreEditTxt.text.toString()
                    poem = changePoemPoemEditTxt.text.toString()
                    namePoet = changePoemAutoCompleteTxt.text.toString()
                    contextActivity.communityPresenter.setSavePoemHelp(
                        PoemHelp(
                            contextActivity.communityPresenter.getSavePoemHelp().username,
                            titlePoem,
                            namePoet,
                            genre,
                            poem,
                            contextActivity.communityPresenter.getSavePoemHelp().avatar,
                            contextActivity.communityPresenter.getSavePoemHelp().id,
                            contextActivity.communityPresenter.getSavePoemHelp().uid,
                            contextActivity.communityPresenter.getSavePoemHelp().like
                        )
                    )
                    changePoemPresenter.changePoem(
                        id, contextActivity,
                        PoemHelp(
                            username,
                            titlePoem,
                            namePoet,
                            genre,
                            poem,
                            avatar,
                            id,
                            uid,
                            like
                        ),
                    )
                }
            }
        }
    }

    override fun back() {
        findNavController().navigate(R.id.detailedPoemFragment)
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
