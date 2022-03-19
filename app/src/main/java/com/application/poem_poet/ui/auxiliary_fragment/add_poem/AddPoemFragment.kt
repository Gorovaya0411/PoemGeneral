package com.application.poem_poet.ui.auxiliary_fragment.add_poem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.application.poem_poet.databinding.FragmentAddPoemBinding
import com.application.poem_poet.R
import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.ui.community.CommunityActivity
import com.google.firebase.auth.FirebaseAuth
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class AddPoemFragment : MvpAppCompatFragment(), AddPoemView {
    private lateinit var mAuth: FirebaseAuth
    private var namePoet = ""
    private val contextActivity: CommunityActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as CommunityActivity)
    }
    private var checkAdd = true
    lateinit var binding: FragmentAddPoemBinding

    @InjectPresenter
    lateinit var addPoemPresenter: AddPoemPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_add_poem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        binding = FragmentAddPoemBinding.bind(view)

        with(binding) {
            addPoemNameTxt.visibility = TextView.INVISIBLE

            addPoemPresenter.receivingNamePoet()
            addPoemPresenter.addUser()

            addPoemCheckBox.setOnCheckedChangeListener { _, isChecked ->

                if (!isChecked) {
                    addPoemNameTxt.visibility = TextView.INVISIBLE
                    addPoemAutoCompleteTxt.visibility = AutoCompleteTextView.VISIBLE
                } else {
                    addPoemAutoCompleteTxt.visibility = AutoCompleteTextView.INVISIBLE
                    addPoemAutoCompleteTxt.setText("")
                    addPoemNameTxt.visibility = TextView.VISIBLE
                    addPoemNameTxt.text = addPoemPresenter.addUser().login

                }
            }

            addPoemAddBtn.setOnClickListener {
                val titlePoem = addPoemTitleEditTxt.text.toString()
                val genre = addPoemGenreEditTxt.text.toString()
                val poem = addPoemPoemEditTxt.text.toString()
                val username = addPoemPresenter.addUser().login
                val avatar = addPoemPresenter.addUser().avatar
                val uid = addPoemPresenter.addUser().uidHere
                namePoet = addPoemAutoCompleteTxt.text.toString()
                if (checkAdd) {
                    checkAdd = false
                    checkAdd = addPoemPresenter.addPoem(
                        checkAdd,
                        contextActivity,
                        PoemHelp(username, titlePoem, namePoet, genre, poem, avatar, uid),
                        binding.addPoemCheckBox,
                        binding.addPoemAddBtn
                    )
                }
            }

            addPoemBackImg.setOnClickListener {
                findNavController().navigate(R.id.action_addPoemFragment_to_generalPoetsFragment)
            }
        }
    }

    override fun openGeneralPoets() {
        findNavController().navigate(R.id.action_addPoemFragment_to_generalPoetsFragment)
    }

    override fun workWithAutoTxt(array: MutableList<String>) {
        with(binding) {
            val adapter = ArrayAdapter(
                contextActivity, android.R.layout.simple_dropdown_item_1line, array
            )
            addPoemAutoCompleteTxt.setAdapter(adapter)
            addPoemAutoCompleteTxt.threshold = 1
            addPoemAutoCompleteTxt.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _,
                                                  position, _ ->
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    namePoet = selectedItem
                }
        }
    }
}
