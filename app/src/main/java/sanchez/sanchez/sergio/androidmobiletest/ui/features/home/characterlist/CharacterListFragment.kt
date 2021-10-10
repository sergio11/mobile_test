package sanchez.sanchez.sergio.androidmobiletest.ui.features.home.characterlist

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import sanchez.sanchez.sergio.androidmobiletest.R
import sanchez.sanchez.sergio.androidmobiletest.databinding.CharactersFragmentBinding
import sanchez.sanchez.sergio.androidmobiletest.di.factory.DaggerComponentFactory
import sanchez.sanchez.sergio.androidmobiletest.domain.models.Character
import sanchez.sanchez.sergio.androidmobiletest.domain.models.CharactersPage
import sanchez.sanchez.sergio.androidmobiletest.ui.core.SupportFragment
import sanchez.sanchez.sergio.androidmobiletest.ui.core.ext.navigate
import timber.log.Timber
import java.lang.Exception

/**
 * Character List Fragment
 */
class CharacterListFragment: SupportFragment<CharactersListViewModel, CharactersFragmentBinding>(CharactersListViewModel::class.java),
    IPaginationCallBack, CharacterListAdapter.OnCharacterClickListener {

    private var recyclerViewAdapter: CharacterListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadInitialCharacters()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated CALLED")
        // Configure Recycler View
        with(binding) {
            contentView.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(object: RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        outRect.bottom = resources.getDimension(R.dimen.item_vertical_separation).toInt()
                    }
                })
                setHasFixedSize(true)
                isNestedScrollingEnabled = true
                adapter = recyclerViewAdapter ?: CharacterListAdapter(requireContext(), mutableListOf(), this@CharacterListFragment, this@CharacterListFragment).also {
                    recyclerViewAdapter = it
                }
            }
            swipeRefreshLayout.setOnRefreshListener { loadInitialCharacters() }
        }
    }

    /**
     * on Create View Binding
     * @param inflater
     * @param container
     */
    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CharactersFragmentBinding =
        CharactersFragmentBinding.inflate(inflater, container, false)

    override fun onAttachComponent() {
        DaggerComponentFactory.getCharactersFragmentComponent(requireActivity() as AppCompatActivity)
            .inject(this)
    }

    override fun onDetachComponent() {
        DaggerComponentFactory.removeCharactersFragmentComponent()
    }

    override fun onInitObservers() {
        lifecycleScope.launchWhenStarted {
            // Observe operation result
            viewModel.charactersState.observe(this@CharacterListFragment, {
                when(it) {
                    is CharactersState.OnSuccess -> onCharactersLoaded(it.characterPage)
                    is CharactersState.OnError -> onErrorOccurred(it.ex)
                    is CharactersState.OnNotFound -> onNotFound()
                    is CharactersState.OnLoading -> onLoading()
                }
            })
        }
    }

    /**
     * On Load Next Page
     */
    override fun onLoadNextPage() {
        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.loadNextPage()
    }

    /**
     * Is Pagination Enabled
     */
    override fun isPaginationEnabled(): Boolean = viewModel.charactersState.value?.let {
        it !is CharactersState.OnNotFound
    } ?: true


    /**
     * On Character Clicked
     * @param character
     */
    override fun onCharacterClicked(character: Character) {
        Timber.d("onCharacterClicked CALLED, name -> %s", character.name)
        navigate(CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(characterId = character.id))
    }

    /**
     * Private Methods
     */

    /**
     * On Characters Loaded
     * @param charactersPage
     */
    private fun onCharactersLoaded(charactersPage: CharactersPage) {
        Timber.d("onCharactersLoaded CALLED, characters -> %d", charactersPage.characterList.size)
        with(binding) {
            loadingView.root.visibility = View.GONE
            errorView.root.visibility = View.GONE
            contentView.root.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = false

            recyclerViewAdapter?.let { adapter ->
                if(charactersPage.isFromCache) {
                    // Data from cache, If there is no character in the list, the cached information is displayed
                    if(adapter.itemCount == 0)
                        adapter.replaceData(charactersPage.characterList)
                    Snackbar.make(requireView(), getString(R.string.response_from_cache), Snackbar.LENGTH_LONG).show()
                } else {
                    val currentSize = adapter.itemCount
                    adapter.addData(charactersPage.characterList)
                    contentView.recyclerView.scrollToPosition(currentSize)
                }
            }
        }
    }

    /**
     * On Loading
     */
    private fun onLoading() {
        Timber.d("onLoading CALLED!")
        with(binding) {
            errorView.root.visibility = View.GONE
            contentView.root.visibility = View.GONE
            loadingView.root.visibility = View.VISIBLE
        }
    }

    /**
     * On Error Occurred
     * @param ex
     */
    private fun onErrorOccurred(ex: Exception) {
        Timber.d("onErrorOccurred CALLED, message -> %s", ex.message)
        with(binding) {
            swipeRefreshLayout.isRefreshing = false
            loadingView.root.visibility = View.GONE
            errorView.root.visibility = View.VISIBLE
            contentView.root.visibility = View.GONE
            Snackbar.make(requireView(), getString(R.string.error_occurred_message), Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * On Not Found
     */
    private fun onNotFound() {
        Timber.d("onNotFound CALLED!")
        with(binding) {
            swipeRefreshLayout.isRefreshing = false
            loadingView.root.visibility = View.GONE
            errorView.root.visibility = View.VISIBLE
            contentView.root.visibility = View.GONE
            Snackbar.make(requireView(), getString(R.string.no_data_found_message), Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * Load Initial Characters
     */
    private fun loadInitialCharacters() {
        viewModel.load()
        recyclerViewAdapter?.clearData()
    }

}