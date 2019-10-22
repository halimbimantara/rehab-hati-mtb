package com.pusatruq.muttabaah.ui.main.scorring

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.yearMonth
import com.kizitonwose.calendarviewsample.daysOfWeekFromLocale
import com.kizitonwose.calendarviewsample.setTextColorRes
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.databinding.FragmentScoreBinding
import com.pusatruq.muttabaah.di.ActivityScoped
import com.pusatruq.muttabaah.ui.core.base.BaseFragment
import com.pusatruq.muttabaah.ui.main.MainActivity
import com.pusatruq.muttabaah.ui.main.scorring.adapter.ScoreAdapter
import kotlinx.android.synthetic.main.calendar_day_layout.view.*
import kotlinx.android.synthetic.main.calendar_day_legend.*
import kotlinx.android.synthetic.main.fragment_score.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Created by cuongpm on 11/29/18.
 */

@ActivityScoped
class ScorringFragment @Inject constructor() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var scoreViewModel: ScoreViewModel

    internal lateinit var adapterS: ScoreAdapter
    internal lateinit var adapterBottom: ScoreAdapter

    private lateinit var dataBinding: FragmentScoreBinding
    private var ScorePut: Int = 0
    private val selectedDates = mutableSetOf<LocalDate>()
    private val today = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    private val monthTitleFormatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("MMMM")
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scoreViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ScoreViewModel::class.java)
//        newsAdapter = NewsAdapter(ArrayList(0), scoreViewModel)

        dataBinding = FragmentScoreBinding.inflate(inflater, container, false).apply {
            this.viewModel = scoreViewModel
        }
        (activity as MainActivity).supportActionBar!!.show()

        return dataBinding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        scoreViewModel.start()
        handleUIEvent()
        val daysOfWeek = daysOfWeekFromLocale()
        legendLayout.children.forEachIndexed { index, view ->
            (view as TextView).apply {
                text = daysOfWeek[index].name.take(3).toUpperCase()
                setTextColorRes(R.color.white)
            }
        }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(10)
        val endMonth = currentMonth.plusMonths(10)
        exOneCalendar.setup(startMonth, endMonth, daysOfWeek.first())
        exOneCalendar.scrollToMonth(currentMonth)

        class DayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: CalendarDay
            val textView = view.calendarDayText

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        if (selectedDates.contains(day.date)) {
                            selectedDates.remove(day.date)
                        } else {
                            selectedDates.add(day.date)
                        }
                        exOneCalendar.notifyDayChanged(day)
                    }
                }
            }
        }
        exOneCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                textView.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    when {
                        selectedDates.contains(day.date) -> {
                            textView.setTextColorRes(R.color.black)
                            textView.setBackgroundResource(R.drawable.example_1_selected_bg)

                        }
                        today == day.date -> {
                            textView.setTextColorRes(R.color.bottom_bg_color)
                            textView.setBackgroundResource(R.drawable.example_1_today_bg)
                        }
                        else -> {
                            textView.setTextColorRes(R.color.bottom_bg_color)
                            textView.background = null
                        }
                    }
                } else {
                    textView.setTextColorRes(R.color.bottom_bg_color)
                    textView.background = null
                }
            }
        }

        exOneCalendar.monthScrollListener = {
            if (exOneCalendar.maxRowCount == 6) {
                exOneYearText.text = it.yearMonth.year.toString()
                exOneMonthText.text = monthTitleFormatter.format(it.yearMonth)
            } else {
                // In week mode, we show the header a bit differently.
                // We show indices with dates from different months since
                // dates overflow and cells in one index can belong to different
                // months/years.
                val firstDate = it.weekDays.first().first().date
                val lastDate = it.weekDays.last().last().date
                if (firstDate.yearMonth == lastDate.yearMonth) {
                    exOneYearText.text = firstDate.yearMonth.year.toString()
                    exOneMonthText.text = monthTitleFormatter.format(firstDate)
                } else {
                    exOneMonthText.text =
                        "${monthTitleFormatter.format(firstDate)} - ${monthTitleFormatter.format(lastDate)}"
                    if (firstDate.year == lastDate.year) {
                        exOneYearText.text = firstDate.yearMonth.year.toString()
                    } else {
                        exOneYearText.text = "${firstDate.yearMonth.year} - ${lastDate.yearMonth.year}"
                    }
                }
            }

        }

        weekModeCheckBox.setOnCheckedChangeListener { _, monthToWeek ->
            val firstDate = exOneCalendar.findFirstVisibleDay()?.date ?: return@setOnCheckedChangeListener
            val lastDate = exOneCalendar.findLastVisibleDay()?.date ?: return@setOnCheckedChangeListener

            val oneWeekHeight = exOneCalendar.dayHeight
            val oneMonthHeight = oneWeekHeight * 6

            val oldHeight = if (monthToWeek) oneMonthHeight else oneWeekHeight
            val newHeight = if (monthToWeek) oneWeekHeight else oneMonthHeight

            // Animate calendar height changes.
            val animator = ValueAnimator.ofInt(oldHeight, newHeight)
            animator.addUpdateListener { animator ->
                exOneCalendar.layoutParams = exOneCalendar.layoutParams.apply {
                    height = animator.animatedValue as Int
                }
            }

            // When changing from month to week mode, we change the calendar's
            // config at the end of the animation(doOnEnd) but when changing
            // from week to month mode, we change the calendar's config at
            // the start of the animation(doOnStart). This is so that the change
            // in height is visible. You can do this whichever way you prefer.

            animator.doOnStart {
                if (!monthToWeek) {
                    exOneCalendar.inDateStyle = InDateStyle.ALL_MONTHS
                    exOneCalendar.maxRowCount = 6
                    exOneCalendar.hasBoundaries = true
                }
            }
            animator.doOnEnd {
                if (monthToWeek) {
                    exOneCalendar.inDateStyle = InDateStyle.FIRST_MONTH
                    exOneCalendar.maxRowCount = 1
                    exOneCalendar.hasBoundaries = false
                }

                if (monthToWeek) {
                    // We want the first visible day to remain
                    // visible when we change to week mode.
                    exOneCalendar.scrollToDate(firstDate)
                } else {
                    // When changing to month mode, we choose current
                    // month if it is the only one in the current frame.
                    // if we have multiple months in one frame, we prefer
                    // the second one unless it's an outDate in the last index.
                    if (firstDate.yearMonth == lastDate.yearMonth) {
                        exOneCalendar.scrollToMonth(firstDate.yearMonth)
                    } else {
                        exOneCalendar.scrollToMonth(minOf(firstDate.yearMonth.next, endMonth))
                    }
                }
            }
            animator.duration = 250
            animator.start()
        }
    }

    private fun handleUIEvent() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        scoreViewModel.stop()
    }

    private fun initActions() {


    }

}