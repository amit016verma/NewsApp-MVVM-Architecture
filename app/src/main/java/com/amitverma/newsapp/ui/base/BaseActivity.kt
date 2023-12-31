package com.amitverma.newsapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.amitverma.newsapp.NewsApplication
import com.amitverma.newsapp.di.component.ActivityComponent
import com.amitverma.newsapp.di.component.DaggerActivityComponent
import com.amitverma.newsapp.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<T : BaseViewModel<*>, ViewBindingType : ViewBinding> :
    AppCompatActivity() {

    @Inject
    lateinit var viewModel: T

    private var _binding: ViewBindingType? = null

    // Binding variable to be used for accessing views.
    protected val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        _binding = setupViewBinding(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setupView(savedInstanceState)
        setupObserver()
    }

    protected open fun setupObserver() {
    }

    private fun buildActivityComponent(): ActivityComponent = DaggerActivityComponent.builder()
        .applicationComponent((application as NewsApplication).applicationComponent)
        .activityModule(ActivityModule(this)).build()


    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    abstract fun setupView(savedInstanceState: Bundle?)

    abstract fun setupViewBinding(inflater: LayoutInflater): ViewBindingType

    /*
    * Safe call method, just in case, if anything is messed up and lifecycle Event does not gets
    * called.
    */
    override fun onDestroy() {
        _binding = null
        super.onDestroy()

    }

}