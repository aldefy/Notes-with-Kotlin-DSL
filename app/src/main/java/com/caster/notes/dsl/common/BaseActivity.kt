package com.caster.notes.dsl.common

import android.content.IntentFilter
import android.util.TypedValue
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


abstract class BaseActivity<V : ViewModel, S : ViewState> : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory

    abstract val clazz: Class<V>
    val _state = PublishSubject.create<S>()
    val state = _state.hide()

    val compositeBag = CompositeDisposable()

    val vm: V by lazy {
        ViewModelProviders.of(this, factory).get(clazz)
    }

    override fun onDestroy() {
        _state.onComplete()
        compositeBag.clear()
        super.onDestroy()
    }
}
