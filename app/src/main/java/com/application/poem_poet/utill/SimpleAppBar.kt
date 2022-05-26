package com.application.poem_poet.utill

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.MenuRes
import com.application.poem_poet.R
import com.application.poem_poet.databinding.LayoutSimpleAppbarBinding

class SimpleAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.appBarLayoutStyle
) : LiftableAppBarLayout(context, attrs, defStyleAttr) {

    val binding = LayoutSimpleAppbarBinding.inflate(LayoutInflater.from(context), this)

    init {
        ToolbarUtils.setSupportActionBar(context, binding.simpleAppbarToolbar)
        ToolbarUtils.setToolbarHomeAsUp(context, R.drawable.ic_base_back)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SimpleAppBar)
        setTitle(attributes.getString(R.styleable.SimpleAppBar_simple_appbar_title))
        attributes.recycle()
    }

    fun setTitle(title: String?) {
        binding.simpleAppbarToolbar.title = title
    }

    fun setMenu(@MenuRes menu: Int, onMenuItemClickAction: (Int) -> Boolean) =
        with(binding.simpleAppbarToolbar) {
            post { inflateMenu(menu) }
            setOnMenuItemClickListener {
                onMenuItemClickAction.invoke(it.itemId)
            }
        }
}
