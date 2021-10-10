package sanchez.sanchez.sergio.androidmobiletest.ui.features.home.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import sanchez.sanchez.sergio.androidmobiletest.R
import sanchez.sanchez.sergio.androidmobiletest.databinding.CharacterDetailBinding
import sanchez.sanchez.sergio.androidmobiletest.di.factory.DaggerComponentFactory
import sanchez.sanchez.sergio.androidmobiletest.domain.models.CharacterDetail
import sanchez.sanchez.sergio.androidmobiletest.domain.models.ComicsItem
import sanchez.sanchez.sergio.androidmobiletest.ui.core.SupportFragment
import sanchez.sanchez.sergio.androidmobiletest.ui.core.ext.loadFromCacheIfExists
import sanchez.sanchez.sergio.androidmobiletest.ui.core.ext.popBackStack
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Character Detail Fragment
 */
class CharacterDetailFragment: SupportFragment<CharacterDetailViewModel, CharacterDetailBinding>(CharacterDetailViewModel::class.java) {


    private val modifiedAtDateFormat by lazy {
        SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.getDefault())
    }

    private val args by navArgs<CharacterDetailFragmentArgs>()

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CharacterDetailBinding = CharacterDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Load Character Detail
        viewModel.loadById(args.characterId)

        binding.toolbar.setNavigationOnClickListener { popBackStack() }
    }

    override fun onAttachComponent() {
        DaggerComponentFactory.getCharacterDetailFragmentComponent(requireActivity() as AppCompatActivity)
            .inject(this)
    }

    override fun onDetachComponent() {
        DaggerComponentFactory.removeCharacterDetailFragmentComponent()
    }

    override fun onStart() {
        super.onStart()
        binding.placeholderImageView.resume()
    }

    override fun onStop() {
        super.onStop()
        binding.placeholderImageView.pause()
    }

    override fun onInitObservers() {
        lifecycleScope.launchWhenStarted {
            // Observe operation result
            viewModel.characterDetailState.observe(this@CharacterDetailFragment, {
                when(it) {
                    is CharacterDetailState.OnSuccess -> onLoadSuccessfully(it.character)
                    is CharacterDetailState.OnError -> onErrorOccurred(it.ex)
                }
            })
        }

    }

    /**
     * Private Methods
     */

    /**
     * on Load Successfully
     * @param character
     */
    private fun onLoadSuccessfully(character: CharacterDetail) {
        with(binding) {
            characterNameTextView.text = character.name
            if(character.description.isNotEmpty())
                characterDescriptionListItem.valueText = character.description
            characterDetailTitleTextView.text = character.name
            character.modified?.let {
                characterModifiedAtItem.valueText = modifiedAtDateFormat.format(it)
            }

            characterComicItem.apply {
                valueText = String.format(Locale.getDefault(),
                    getString(R.string.character_comic_value), character.comics.available)
                if(character.comics.items.isNotEmpty())
                    addAction {
                        showDetailDialog(
                            titleRes = R.string.character_comic_dialog_detail_title,
                            items = character.comics.items
                        )
                    }
            }

            characterSeriesItem.apply {
                valueText = String.format(Locale.getDefault(),
                    getString(R.string.character_series_value), character.series.available)
                if(character.series.items.isNotEmpty())
                    addAction {
                        showDetailDialog(
                            titleRes = R.string.character_series_dialog_detail_title,
                            items = character.series.items
                        )
                    }
            }

            characterEventsItem.apply {
                valueText = String.format(Locale.getDefault(),
                    getString(R.string.character_events_value), character.events.available)
                if(character.events.items.isNotEmpty())
                    addAction {
                        showDetailDialog(
                            titleRes = R.string.character_events_dialog_detail_title,
                            items = character.events.items
                        )
                    }
            }

            characterThumbnailImageView.loadFromCacheIfExists(character.thumbnail)
        }
    }

    /**
     * on Error Occurred
     * @param ex
     */
    private fun onErrorOccurred(ex: Exception) {
        Timber.d("OnErrorOccurred -> ${ex.message}")
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(resources.getString(R.string.character_detail_error))
            .setPositiveButton(resources.getString(R.string.character_detail_error_accept_button)) { _, _ ->
                popBackStack(R.id.characterListFragment)
            }
            .show()
    }

    /**
     * Show Detail Dialog
     * @param titleRes
     * @param items
     */
    private fun showDetailDialog(@StringRes titleRes: Int, items: List<ComicsItem>) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(titleRes)
            .setItems(items.map { it.name }.toTypedArray(), null)
            .show()
    }
}