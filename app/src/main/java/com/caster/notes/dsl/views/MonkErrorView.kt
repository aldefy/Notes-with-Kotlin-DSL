package com.caster.notes.dsl.views
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.caster.notes.dsl.R
import com.caster.notes.dsl.common.show
import kotlinx.android.synthetic.main.view_error.view.*

const val ERROR_VIEW = "errorview.json"
const val EMPTY_VIEW = "emptyview.json"

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_error, this)
        animationView.setAnimation(ERROR_VIEW)
        var initJson: String = ERROR_VIEW
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ErrorView,
            0, 0
        ).apply {
            try {
                getString(R.styleable.ErrorView_errorTitleText)?.let {
                    errorTitle.text = it
                }
                getString(R.styleable.ErrorView_errorSubTitleText)?.let {
                    errorMessage.text = it
                }
                getString(R.styleable.ErrorView_animJson)?.let {
                    initJson = it
                }
            } finally {
                animationView.setAnimation(initJson)
                animationView.playAnimation()
            }
        }
    }

    fun showError(
        title: String? = context.resources.getString(R.string.error_general),
        message: String
    ) {
        render(
            ERROR_VIEW,
            title!!,
            message
        )
    }

    fun showEmpty(
        title: String = context.resources.getString(R.string.error_empty),
        message: String = context.resources.getString(R.string.error_empty_message)
    ) {
        render(
            EMPTY_VIEW,
            title,
            message
        )
    }

    fun showJson(
        errorType: String,
        title: String,
        message: String
    ) {
        render(
            errorType,
            title,
            message
        )
    }

    private fun render(
        animation: String,
        title: String = "",
        message: String = ""
    ) {
        animationView.setAnimation(animation)
        animationView.playAnimation()
        errorTitle.text = title
        errorMessage.text = message
        show()
    }
}
