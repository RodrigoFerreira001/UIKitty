package com.catbit.uikitty.widget.page_indicator

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager2.widget.ViewPager2
import com.catbit.uikitty.R
import com.catbit.uikitty.databinding.WidgetUikittyPageIndicatorBinding
import com.catbit.uikitty.extensions.toPx

class PageIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private lateinit var binding: WidgetUikittyPageIndicatorBinding
    private var pageIndicators: List<AppCompatImageView> = listOf()
    private var viewPager: ViewPager2? = null

    init {
        attrs?.apply {

            getLayout()

            context.obtainStyledAttributes(this, R.styleable.PageIndicator).run {
                currentPageIndicator = getDrawable(
                    R.styleable.PageIndicator_currentPageIndicator
                )

                currentPageIndicatorColor = getColor(
                    R.styleable.PageIndicator_currentPageIndicatorColor,
                    ContextCompat.getColor(context, R.color.uikitty_500)
                )

                unselectedPageIndicator = getDrawable(
                    R.styleable.PageIndicator_unselectedPageIndicator
                )

                unselectedPageIndicatorColor = getColor(
                    R.styleable.PageIndicator_unselectedPageIndicatorColor,
                    ContextCompat.getColor(context, R.color.uikitty_500)
                )

                indicatorsSpacing = getLayoutDimension(
                    R.styleable.PageIndicator_indicatorsSpacing,
                    DEFAULT_SPACING.toPx()
                )

                indicatorsGravity = IndicatorsGravity.getByType(
                    getInt(
                        R.styleable.PageIndicator_indicatorsGravity,
                        GRAVITY_CENTER
                    )
                )

                recycle()
            }
        }
    }

    var currentPageIndicator: Drawable? = null
        set(value) {
            field = value ?: ContextCompat.getDrawable(
                context,
                R.drawable.shape_uikitty_page_indicator_chip_selected
            )
            changeIndicatorsDrawable()
        }

    var unselectedPageIndicator: Drawable? = null
        set(value) {
            field = value ?: ContextCompat.getDrawable(
                context,
                R.drawable.shape_uikitty_page_indicator_chip_unselected
            )
            changeIndicatorsDrawable()
        }

    var currentPageIndicatorColor: Int = 0
        set(value) {
            field = value
            tintDrawable(currentPageIndicator, value)
            changeIndicatorsDrawable()
        }

    var unselectedPageIndicatorColor: Int = 0
        set(value) {
            field = value
            tintDrawable(unselectedPageIndicator, value)
            changeIndicatorsDrawable()
        }

    var indicatorsSpacing: Int? = null
        set(value) {
            field = value
            drawIndicators()
        }

    var indicatorsGravity: IndicatorsGravity? = null
        set(value) {
            field = value
            drawIndicators()
        }

    fun attachViewPager(viewPager: ViewPager2) {

        this.viewPager = viewPager

        drawIndicators()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                changeIndicatorsDrawable()
            }
        })
    }

    private fun drawIndicators() {

        viewPager?.adapter?.let { adapter ->

            binding.uikittyPageIndicatorRoot.apply {
                removeAllViews()
                pageIndicators = (0 until adapter.itemCount).map { index ->
                    AppCompatImageView(context).apply {
                        setImageDrawable(getIndicatorDrawable(index))

                        adjustViewBounds = true

                        layoutParams = LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).also {
                            if (index != adapter.itemCount.minus(1)) {
                                it.setMargins(0, 0, indicatorsSpacing ?: DEFAULT_SPACING.toPx(), 0)
                            }
                        }
                    }
                }

                pageIndicators.forEach { view ->
                    addView(view)
                }

                layoutParams.apply {
                    gravity = when (indicatorsGravity) {
                        is IndicatorsGravity.GravityStart -> Gravity.START
                        is IndicatorsGravity.GravityCenter -> Gravity.CENTER
                        else -> Gravity.END
                    }
                }
            }
        }
    }

    private fun changeIndicatorsDrawable() {
        pageIndicators.forEachIndexed { index, view ->
            view.setImageDrawable(getIndicatorDrawable(index))
        }
    }

    private fun getIndicatorDrawable(index: Int): Drawable? {
        return if (index == viewPager?.currentItem) currentPageIndicator else unselectedPageIndicator
    }

    private fun tintDrawable(drawable: Drawable?, color: Int) {
        drawable?.let {
            DrawableCompat.setTint(it, color)
        }
    }

    fun getLayout() {
        binding = WidgetUikittyPageIndicatorBinding.bind(
            inflate(context, R.layout.widget_uikitty_page_indicator, this)
        )
    }

    sealed class IndicatorsGravity(val type: Int) {
        object GravityStart : IndicatorsGravity(GRAVITY_START)
        object GravityCenter : IndicatorsGravity(GRAVITY_CENTER)
        object GravityEnd : IndicatorsGravity(GRAVITY_END)

        companion object {
            fun getByType(type: Int) = when (type) {
                GravityStart.type -> GravityStart
                GravityCenter.type -> GravityCenter
                else -> GravityEnd
            }
        }
    }

    companion object {
        const val DEFAULT_SPACING = 4
        const val GRAVITY_START = 0
        const val GRAVITY_CENTER = 1
        const val GRAVITY_END = 2
    }
}