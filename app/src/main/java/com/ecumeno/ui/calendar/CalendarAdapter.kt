package com.ecumeno.ui.calendar

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecumeno.R
import com.ecumeno.core.domain.CalendarDay
import com.ecumeno.core.domain.FastLevel
import com.ecumeno.databinding.CalendarDayItemBinding
import java.time.LocalDate
import java.time.format.TextStyle

class CalendarAdapter(
    private val onDateClick: (CalendarDay) -> Unit
) : ListAdapter<CalendarDay, CalendarAdapter.CalendarViewHolder>(DiffCallback()) {

    private var selectedDate: LocalDate? = null

    fun setSelectedDate(date: LocalDate?) {
        val old = selectedDate
        if (old == date) return
        selectedDate = date
        notifyDate(old)
        notifyDate(date)
    }

    private fun notifyDate(date: LocalDate?) {
        if (date == null) return
        val position = currentList.indexOfFirst { it.date == date }
        if (position != RecyclerView.NO_POSITION) notifyItemChanged(position)
    }
    inner class CalendarViewHolder(
        private val binding: CalendarDayItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: CalendarDay) {
            val context = binding.root.context
            val locale = context.resources.configuration.locales[0]

            binding.dayNumber.text = day.date.dayOfMonth.toString()
            binding.dayOfWeek.text = day.date.dayOfWeek.getDisplayName(TextStyle.SHORT, locale)

            binding.root.background = null
            binding.root.setOnClickListener(null)
            binding.root.isClickable = false
            binding.holidayIndicator.isVisible = false
            binding.fastIndicator.isVisible = false
            binding.todayIndicator.isVisible = false

            if (!day.currentMonth) {
                binding.dayNumber.setTextColor(context.getColor(R.color.not_available))
                return
            }

            val selected = day.date == selectedDate
            val color = context.getColor(if (selected) R.color.main else R.color.accent)
            val tint = ColorStateList.valueOf(color)

            if (selected) binding.root.setBackgroundResource(R.drawable.selected_day_bg)
            binding.dayNumber.setTextColor(color)

            day.holidays.minByOrNull { it.priority }?.let { holiday ->
                binding.holidayIndicator.isVisible = true
                binding.holidayIndicator.setBackgroundResource(
                    when (holiday.priority) {
                        0, 1 -> R.drawable.ic_big_feast
                        2 -> R.drawable.ic_medium_feast
                        else -> R.drawable.ic_small_feast
                    }
                )
                binding.holidayIndicator.backgroundTintList = tint
            }

            if (day.fastLevel != FastLevel.NO_FAST && day.fastLevel != FastLevel.CONTINUOUS_WEEK) {
                binding.fastIndicator.isVisible = true
                binding.fastIndicator.backgroundTintList = tint
            }

            if (day.isToday) {
                binding.todayIndicator.isVisible = true
                binding.todayIndicator.backgroundTintList = tint
            }

            binding.root.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                setSelectedDate(day.date)
                onDateClick(day)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = CalendarDayItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CalendarDay>() {
        override fun areItemsTheSame(oldItem: CalendarDay, newItem: CalendarDay) =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: CalendarDay, newItem: CalendarDay) =
            oldItem == newItem
    }
}