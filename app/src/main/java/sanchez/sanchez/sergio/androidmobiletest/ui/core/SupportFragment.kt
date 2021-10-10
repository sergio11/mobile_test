package sanchez.sanchez.sergio.androidmobiletest.ui.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import javax.inject.Inject

/**
 * Support Fragment
 */
abstract class SupportFragment<VM : ViewModel, VB: ViewBinding>(private val mViewModelClass: Class<VM>): Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var parentActivity: AppCompatActivity

    // View Model
    lateinit var viewModel: VM

    // Binding
    lateinit var binding: VB


    /**
     * on Create
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        onAttachComponent()
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
        onInitObservers()
    }

    /**
     * On Create View
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = onCreateViewBinding(inflater, container)
        .also { binding = it }.root

    override fun onDestroy() {
        onDetachComponent()
        super.onDestroy()
    }

    /**
     * If you want to inject Dependency Injection
     * on your activity, you can override this.
     */
    abstract fun onAttachComponent()

    /**
     * Using this method for remove component
     */
    abstract fun onDetachComponent()

    /**
     * On Init Observers
     */
    abstract fun onInitObservers()

    /**
     * on Create View Binding
     */
    abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    /**
     * Private Methods
     */

    /**
     * Get View Model
     */
    private fun initViewModel(): VM = ViewModelProvider(this, viewModelFactory).get(mViewModelClass)

}