package com.ecumeno.ui.beads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecumeno.core.domain.Confession
import com.ecumeno.ui.beads.enums.MysteryType
import com.ecumeno.ui.beads.enums.PrayerType
import com.ecumeno.data.local.preferences.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Calendar

class BeadsViewModel(private val preferencesRepository: PreferencesRepository) : ViewModel() {
    private lateinit var beadsStructure: List<PrayerType>
    private var beadsStart = 1
    private var beadsLimit = 0
    private var currentDecade = 0
    private var currentBead = 0
    private var currentPrayerIndex = 0
    private lateinit var confession: Confession
    private var ruleEnabled: Boolean = true
    private val _uiState = MutableStateFlow(BeadsUiState())
    val uiState: StateFlow<BeadsUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(preferencesRepository.confession, preferencesRepository.isRuleEnabled) {
                conf, rule -> confession = Confession.fromPreferences(conf) ; ruleEnabled = rule
            }.collect {
                resetBeads()
            }
        }
    }

    fun nextBead() {
        if(currentDecade == beadsLimit && currentPrayerIndex == beadsStructure.size - 1){
            resetBeads()
        }
        else{
            if (confession == Confession.cat && ruleEnabled){
                if (currentPrayerIndex < beadsStructure.size - 1) {
                    if(currentPrayerIndex == beadsLimit){
                        currentDecade++
                    }
                    currentPrayerIndex++
                }
                else{
                    currentPrayerIndex = beadsLimit + 1
                    currentDecade++
                }
            } else{
                if (currentPrayerIndex < beadsStructure.size - 1) {
                    currentPrayerIndex++
                }
                else{
                    currentDecade++
                    currentPrayerIndex = 0
                }
            }
            updateCurrentDecadeAndBead()
            updateDisplay()
        }
    }

    fun previousBead() {
        if (currentPrayerIndex > 0) {
            currentPrayerIndex--
            updateCurrentDecadeAndBead()
            updateDisplay()
        }
    }

    private fun updateCurrentDecadeAndBead() {
        if (confession == Confession.cat && ruleEnabled){
            when {
                currentPrayerIndex == 0 -> { currentDecade = 0; currentBead = 0 }
                currentPrayerIndex <= 5 -> { currentDecade = 0; currentBead = currentPrayerIndex }
                else -> {
                    val beadInDecade = (currentPrayerIndex - 5) % 14
                    currentBead = beadInDecade
                }
            }
        }
        else{
            currentBead = currentPrayerIndex
        }
    }

    private fun initBeadsStructure(){
        if (ruleEnabled){
            when (confession){
                Confession.cat -> {
                    beadsStructure = listOf(
                        PrayerType.CREED,
                        PrayerType.OUR_FATHER,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.GLORY_BE,
                        PrayerType.OUR_FATHER,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.HAIL_MARY,
                        PrayerType.GLORY_BE,
                        PrayerType.FATIMA_PRAYER,
                    )
                    beadsStart = 0
                    beadsLimit = 5
                }
                Confession.lut -> {
                    beadsStructure = listOf(
                        PrayerType.FRALSARKRANSEN_FIRST,
                        PrayerType.FRALSARKRANSEN_SECOND,
                        PrayerType.FRALSARKRANSEN_THIRD,
                        PrayerType.FRALSARKRANSEN_FOURTH,
                        PrayerType.FRALSARKRANSEN_SECOND,
                        PrayerType.FRALSARKRANSEN_FIFTH,
                        PrayerType.FRALSARKRANSEN_SECOND,
                        PrayerType.FRALSARKRANSEN_SIXTH,
                        PrayerType.FRALSARKRANSEN_SECOND,
                        PrayerType.FRALSARKRANSEN_SEVENTH,
                        PrayerType.FRALSARKRANSEN_EIGHT,
                        PrayerType.SILENCE,
                        PrayerType.SILENCE,
                        PrayerType.SILENCE,
                        PrayerType.FRALSARKRANSEN_NINTH,
                        PrayerType.FRALSARKRANSEN_SECOND,
                        PrayerType.FRALSARKRANSEN_TENTH,
                        PrayerType.FRALSARKRANSEN_SECOND
                    )
                    beadsStart = 1
                    beadsLimit = 0
                }
                Confession.ort -> {
                    beadsStructure = listOf(
                        PrayerType.OUR_FATHER,
                        PrayerType.JESUS_PRAYER,
                        PrayerType.JESUS_PRAYER,
                        PrayerType.JESUS_PRAYER,
                        PrayerType.JESUS_PRAYER,
                        PrayerType.JESUS_PRAYER,
                        PrayerType.JESUS_PRAYER,
                        PrayerType.JESUS_PRAYER,
                        PrayerType.JESUS_PRAYER,
                        PrayerType.JESUS_PRAYER,
                        PrayerType.JESUS_PRAYER
                    )
                    beadsStart = 1
                    beadsLimit = 3
                }
            }
        }
        else {
            beadsStructure = listOf(
                PrayerType.OUR_FATHER,
                PrayerType.JESUS_PRAYER,
                PrayerType.JESUS_PRAYER,
                PrayerType.JESUS_PRAYER,
                PrayerType.JESUS_PRAYER,
                PrayerType.JESUS_PRAYER,
                PrayerType.JESUS_PRAYER,
                PrayerType.JESUS_PRAYER,
                PrayerType.JESUS_PRAYER,
                PrayerType.JESUS_PRAYER,
                PrayerType.JESUS_PRAYER
            )
            beadsStart = 1
            beadsLimit = 3
        }
        currentDecade = beadsStart
    }

    fun resetBeads() {
        initBeadsStructure()
        currentPrayerIndex = 0
        currentDecade = beadsStart
        currentBead = 0
        updateDisplay()
    }

    private fun updateDisplay() {
        val confession = confession
        val prayerType = beadsStructure[currentPrayerIndex]
        val delimiter = prayerType != uiState.value.prayerType
        var displayBead = currentBead
        if (confession == Confession.lut && ruleEnabled) displayBead++
        val mysteryVisibility = confession == Confession.cat && ruleEnabled
        val mysteryType = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> MysteryType.SORROWFUL
            Calendar.FRIDAY -> MysteryType.SORROWFUL
            Calendar.WEDNESDAY -> MysteryType.GLORIOUS
            Calendar.SUNDAY -> MysteryType.GLORIOUS
            Calendar.THURSDAY -> MysteryType.LUMINOUS
            else -> MysteryType.JOYFUL
        }
        val decadeVisibility = currentDecade != 0 && !(confession == Confession.lut && ruleEnabled)
        val beadVisibility = displayBead != 0 && !(confession == Confession.cat && currentPrayerIndex > 16)
        val hasCenterPiece = confession == Confession.cat && currentPrayerIndex > 16
        val currBead = if (!hasCenterPiece && (confession != Confession.cat || (!ruleEnabled) || (currentPrayerIndex > 5))) currentBead + 10 else currentBead
        _uiState.value = BeadsUiState(
            prayerType = prayerType,
            currentDecade = currentDecade,
            currentBead = currBead,
            displayBead = displayBead,
            mysteryType = mysteryType,
            mysteryVisibility = mysteryVisibility,
            prayerVisibility = ruleEnabled,
            decadeVisibility = decadeVisibility,
            beadVisibility = beadVisibility,
            hasCenterPiece = hasCenterPiece,
            isDelimiter = delimiter
        )
    }
}