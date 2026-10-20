package com.ecumeno.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecumeno.R
import com.ecumeno.core.domain.CalendarDay
import com.ecumeno.core.domain.FastLevel
import com.ecumeno.databinding.CalendarDayItemBinding
import java.time.format.TextStyle
import java.util.Locale

class CalendarAdapter(private val onDateClick: (CalendarDay) -> Unit) : ListAdapter<CalendarDay, CalendarAdapter.CalendarViewHolder>(DiffCallback()) {
    private var selectedDate: CalendarDay? = null
    fun setCurrentDate(date: CalendarDay?) {
        selectedDate = date
    }
    inner class CalendarViewHolder(
        private val binding: CalendarDayItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: CalendarDay) {
            binding.dayNumber.text = day.date.dayOfMonth.toString()
            val currentLocales = AppCompatDelegate.getApplicationLocales()
            val languageCode : String
            if (currentLocales.isEmpty){
                languageCode = Locale.getDefault().language
            } else{
                languageCode = currentLocales[0]?.language ?: "en"
            }
            binding.dayOfWeek.text = day.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag(languageCode))

            if (!day.currentMonth) {
                binding.dayNumber.setTextColor(
                    binding.root.context.getColor(R.color.not_available)
                )
                binding.holidayIndicator.visibility = View.GONE
                binding.fastIndicator.visibility = View.GONE
                binding.root.background = null
                binding.root.setOnClickListener(null)
            } else{
                if (day.holidays.isNotEmpty()) {
                    val holiday = day.holidays.first()
                    binding.holidayIndicator.visibility = View.VISIBLE
                    binding.holidayIndicator.setBackgroundResource(
                        when (holiday.priority) {
                            0, 1 -> R.drawable.ic_big_feast
                            2 -> R.drawable.ic_medium_feast
                            else -> R.drawable.ic_small_feast
                        }
                    )
                } else {
                    binding.holidayIndicator.visibility = View.GONE
                }

                if (day.date == selectedDate?.date) {
                    binding.root.setBackgroundResource(R.drawable.selected_day_bg)
                    binding.holidayIndicator.background.setTint(
                        binding.root.context.getColor(R.color.main)
                    )
                    binding.fastIndicator.background.setTint(
                        binding.root.context.getColor(R.color.main),
                    )
                    binding.todayIndicator.background.setTint(
                        binding.root.context.getColor(R.color.main),
                    )
                    binding.dayNumber.setTextColor(
                        binding.root.context.getColor(R.color.main)
                    )
                } else {
                    binding.root.background = null
                    binding.holidayIndicator.background.setTint(
                        binding.root.context.getColor(R.color.accent)
                    )
                    binding.fastIndicator.background.setTint(
                        binding.root.context.getColor(R.color.accent),
                    )
                    binding.todayIndicator.background.setTint(
                        binding.root.context.getColor(R.color.accent),
                    )
                    binding.dayNumber.setTextColor(
                        binding.root.context.getColor(R.color.accent)
                    )
                }

                if (day.isToday) {
                    binding.todayIndicator.visibility = View.VISIBLE
                }

                binding.fastIndicator.visibility = when (day.fastLevel) {
                    FastLevel.NO_FAST -> View.GONE
                    FastLevel.CONTINUOUS_WEEK -> View.GONE
                    else -> View.VISIBLE
                }

                binding.root.setOnClickListener {
                    val oldDate = selectedDate
                    onDateClick(day)
                    selectedDate = day
                    notifyItemChanged(adapterPosition)
                    if (oldDate != null) {
                        val oldPosition = currentList.indexOfFirst { it.date == oldDate.date }
                        if (oldPosition != -1) {
                            notifyItemChanged(oldPosition)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = CalendarDayItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CalendarDay>() {
        override fun areItemsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: CalendarDay, newItem: CalendarDay): Boolean {
            return oldItem == newItem
        }
    }
}