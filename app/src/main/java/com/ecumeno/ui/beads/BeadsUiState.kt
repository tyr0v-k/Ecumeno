package com.ecumeno.ui.beads

import com.ecumeno.ui.beads.enums.MysteryType
import com.ecumeno.ui.beads.enums.PrayerType

data class BeadsUiState(
    val prayerType: PrayerType = PrayerType.OUR_FATHER,
    val currentDecade: Int = 0,
    val currentBead: Int = 0,
    val displayBead: Int = 0,
    val mysteryType: MysteryType = MysteryType.JOYFUL,
    val mysteryVisibility: Boolean = false,
    val prayerVisibility: Boolean = true,
    val decadeVisibility: Boolean = false,
    val beadVisibility: Boolean = false,
    val hasCenterPiece: Boolean = false,
    val isDelimiter: Boolean = false
)