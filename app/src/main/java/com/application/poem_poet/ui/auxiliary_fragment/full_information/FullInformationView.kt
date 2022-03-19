package com.application.poem_poet.ui.auxiliary_fragment.full_information

import android.widget.ImageView
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.WorkAddInfo
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface FullInformationView : MvpView {
    fun populateData(listPoemPoet: MutableList<PoemAnswer?>)
    fun convertData(biog:String)
}

abstract class FullInformationImpl : MvpPresenter<FullInformationView>() {
    abstract fun getAvatarNew(id: String, view: ImageView)
    abstract fun getData(pathName: String)
    abstract fun addBio(pathName: String): String
   abstract fun addInfoForUser(uidGet:String): WorkAddInfo

}