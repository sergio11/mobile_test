package sanchez.sanchez.sergio.androidmobiletest.ui.features.home.characterlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sanchez.sanchez.sergio.androidmobiletest.R
import sanchez.sanchez.sergio.androidmobiletest.databinding.CharacterItemListBinding
import sanchez.sanchez.sergio.androidmobiletest.domain.models.Character
import sanchez.sanchez.sergio.androidmobiletest.ui.core.ext.loadFromCacheIfExists
import timber.log.Timber

/**
 * Character List Adapter
 */
class CharacterListAdapter(
    private val context: Context,
    private val data: MutableList<Character>,
    private val characterItemListener: OnCharacterClickListener,
    private val paginationCallBack: IPaginationCallBack? = null
): RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {


    /**
     * Create View Holder
     * @param parent
     * @param viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(CharacterItemListBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))


    /**
     * On Bind Model to View Holder
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        data.getOrNull(position)?.let {
            holder.bind(it)
        }
        paginationCallBack?.let { callback ->
            if(callback.isPaginationEnabled()) {
               if(position+1 == data.size) {
                   Timber.d("PAGINATION -> position: %d, total size: %d", position, data.size)
                   callback.onLoadNextPage()
               }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    /**
     * Update Adapter Data
     * @param newData
     */
    fun addData(newData: List<Character>) {
        data.apply {
            addAll(newData)
        }
        notifyDataSetChanged()
    }

    /**
     * Clear Data
     */
    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    /**
     * Replace Data
     * @param newData
     */
    fun replaceData(newData: List<Character>) {
        data.clear()
        addData(newData)
    }


    interface OnCharacterClickListener {
        fun onCharacterClicked(character: Character)
    }

    /**
     * Character View Holder
     * @param binding
     */
    inner class CharacterViewHolder(private val binding: CharacterItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            with(binding) {
                root.setOnClickListener { characterItemListener.onCharacterClicked(character) }
                characterThumbnail.loadFromCacheIfExists(character.thumbnail)
                characterNameTextView.text = character.name
                comicsCountTextView.text = String.format(context.getString(R.string.character_item_comics), character.comics)
                seriesCountTextView.text = String.format(context.getString(R.string.character_item_series), character.series)
                eventsCountTextView.text = String.format(context.getString(R.string.character_item_events), character.events)
            }
        }
    }

}