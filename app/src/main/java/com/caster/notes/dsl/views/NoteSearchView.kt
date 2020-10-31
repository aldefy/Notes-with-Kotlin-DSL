package com.caster.notes.dsl.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.caster.notes.dsl.R
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.common.observableIo
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class NoteSearchView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SearchView(context, attrs, defStyleAttr) {

    private val closeButton = findViewById<ImageView>(R.id.search_close_btn)
    private val searchEditText: EditText = findViewById(androidx.appcompat.R.id.search_src_text)
    var isExpanded = false

    init {
        this.maxWidth = Int.MAX_VALUE
        this.setIconifiedByDefault(false)
        closeButton.setImageResource(R.drawable.ic_close_white)
        searchEditText.hint = context.getString(R.string.generic_search_message)
        searchEditText.apply {
            setHintTextColor(ContextCompat.getColor(context, R.color.gray))
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    override fun onActionViewCollapsed() {
        super.onActionViewCollapsed()
        isExpanded = false
    }

    @SuppressLint("PrivateResource")
    override fun onActionViewExpanded() {
        super.onActionViewExpanded()
        searchEditText.setBackgroundResource(R.drawable.abc_textfield_search_default_mtrl_alpha)
        isExpanded = true
    }

    fun setCloseClickedListener(
        onCloseClicked: () -> Unit
    ) {
        setOnCloseListener {
            onCloseClicked()
            this.onActionViewCollapsed()
            true
        }
    }

    fun setSearchHint(hint:String){
        searchEditText.hint = hint
    }

    fun setSearchText(changes: String) {
        searchEditText.setText(changes)
    }

    fun observeTextChanges(
        disposable: CompositeDisposable,
        onSuccess: (changes: String) -> Unit,
        onError: (error: Throwable) -> Unit = { }
    ) {
        RxTextView.textChangeEvents(findViewById(androidx.appcompat.R.id.search_src_text))
            .skipInitialValue()
            .debounce(300, TimeUnit.MILLISECONDS)
            .compose(observableIo())
            .subscribe(
                {
                    onSuccess(it.text().toString())
                }, {
                    onError(it)
                }
            )
            .addTo(disposable)
    }
}