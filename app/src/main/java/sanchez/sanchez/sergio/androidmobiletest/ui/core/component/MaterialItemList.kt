package sanchez.sanchez.sergio.androidmobiletest.ui.core.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import sanchez.sanchez.sergio.androidmobiletest.R
import sanchez.sanchez.sergio.androidmobiletest.databinding.MaterialListItemLayoutBinding

/**
 * Material List Item
 */
class MaterialListItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: MaterialListItemLayoutBinding

    /**
     * Label Text
     */
    var labelText: String? = null
        set(value) {
            field = value
            binding.labelTextView.text = value
        }


    /**
     * Help Text
     */
    var helpText: String? = null
        set(value) {
            field = value
            binding.helpTextView.text = value
        }


    /**
     * Value Text
     */
    var valueText: String? = null
        set(value) {
            field = value
            binding.valueTextView.text = value
        }
        get() = binding.valueTextView.text.toString()

    /**
     * Value Text Color
     */
    @ColorRes
    var valueTextColor: Int? = null
        set(value) {
            field = value
            field?.let {
                binding.valueTextView.setTextColor(ContextCompat.getColor(context, it))
            }
        }

    /**
     * Action Text
     */
    var actionText: String? = null
        set(value) {
            field = value
            binding.actionButton.text = value
        }

    init {
        binding = MaterialListItemLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.MaterialListItem, defStyleAttr, 0
        ).also {
            setAttributes(it)
            it.recycle()
        }
    }

    /**
     * Public Methods
     */

    fun addAction(action: () -> Unit = {}){
        binding.actionButton.apply {
            visibility=View.VISIBLE
            setOnClickListener {
                action()
            }

        }
    }


    /**
     * Set Attributes
     * @param attrs
     */
    private fun setAttributes(attrs: TypedArray) {
        //labelText
        val labelResId = attrs.getResourceId(R.styleable.MaterialListItem_labelText, DEFAULT_NO_RESOURCE_ID)
        if(labelResId != DEFAULT_NO_RESOURCE_ID)
            labelText = context.getString(labelResId)
        else
            binding.labelTextView.visibility = View.GONE

        val valueResId = attrs.getResourceId(R.styleable.MaterialListItem_valueText, DEFAULT_NO_RESOURCE_ID)
        if(valueResId != DEFAULT_NO_RESOURCE_ID)
            valueText = context.getString(valueResId)

        //helpText
        val helperResId = attrs.getResourceId(R.styleable.MaterialListItem_helpText, DEFAULT_NO_RESOURCE_ID)
        if(helperResId != DEFAULT_NO_RESOURCE_ID)
            helpText = context.getString(helperResId)
        else
            binding.helpTextView.visibility = View.GONE

        //actionText
        val actionResId = attrs.getResourceId(R.styleable.MaterialListItem_actionText, DEFAULT_NO_RESOURCE_ID)
        if(actionResId != DEFAULT_NO_RESOURCE_ID) {
            actionText = context.getString(actionResId)
        }
        binding.actionButton.visibility = View.GONE

    }

    companion object {
        const val DEFAULT_NO_RESOURCE_ID = -1
    }
}