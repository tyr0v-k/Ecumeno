package com.ecumeno.core.calculator

import com.ecumeno.core.domain.CalendarDay
import com.ecumeno.core.domain.FastLevel
import com.ecumeno.core.domain.Holiday
import com.ecumeno.core.domain.Confession
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.MonthDay
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

object EasterCalculator {
    lateinit var fixedHolidays : MutableMap<String, Holiday>
    fun calculateOrthodoxEaster(year: Int): LocalDate {
        val a = year % 19
        val b = year % 4
        val c = year % 7
        val d = (19 * a + 15) % 30
        val e = (2 * b + 4 * c + 6 * d + 6) % 7
        val f = d + e

        val day = if (f <= 9) {
            22 + f
        } else {
            f - 9
        }
        val month = if (f <= 9) Month.MARCH else Month.APRIL

        var date = LocalDate.of(year, month, day).plusDays(13)

        if (date.month == Month.MAY && date.dayOfMonth > 7) {
            date = date.minusDays(7)
        }

        return date
    }
    fun calculateGregorianEaster(year: Int): LocalDate {
        val a = year % 19
        val b = year / 100
        val c = year % 100
        val d = b / 4
        val e = b % 4
        val f = (b + 8) / 25
        val g = (b - f + 1) / 3
        val h = (19 * a + b - d - g + 15) % 30
        val i = c / 4
        val k = c % 4
        val l = (32 + 2 * e + 2 * i - h - k) % 7
        val m = (a + 11 * h + 22 * l) / 451

        val month = (h + l - 7 * m + 114) / 31
        val day = ((h + l - 7 * m + 114) % 31) + 1

        return LocalDate.of(year, month, day)
    }

    fun getOrthodoxFixedHolidays() {
        fixedHolidays = mutableMapOf(
            "0106" to Holiday(name = "christmas_eve", priority = 2),
            "0107" to Holiday(name = "christmas", priority = 1),
            "0108" to Holiday(name = "synaxis_of_the_most_holy_theotokos", priority = 3),
            "0114" to Holiday(name = "circumcision_of_the_lord", priority = 2),
            "0115" to Holiday(name = "seraphim_of_sarov_repose", priority = 3),
            "0118" to Holiday(name = "theophany_eve", priority = 2),
            "0119" to Holiday(name = "theophany_lord", priority = 1),
            "0120" to Holiday(name = "synaxis_of_john_the_baptist", priority = 2),
            "0206" to Holiday(name = "xenia_of_petersburg", priority = 3),
            "0212" to Holiday(name = "synaxis_of_three_hierarchs", priority = 3),
            "0215" to Holiday(name = "presentation_of_the_lord", priority = 1),
            "0309" to Holiday(name = "finding_of_the_head_of_john_the_baptist", priority = 3),
            "0322" to Holiday(name = "forty_martyrs_of_sebaste", priority = 3),
            "0407" to Holiday(name = "annunciation_of_the_most_holy_theotokos", priority = 1),
            "0502" to Holiday(name = "matrona_of_moscow", priority = 3),
            "0506" to Holiday(name = "george_the_victorious", priority = 3),
            "0509" to Holiday(name = "memorial_day_for_fallen_soldiers", priority = 3),
            "0521" to Holiday(name = "john_the_apostle", priority = 3),
            "0522" to Holiday(name = "translation_of_relics_of_nicholas_the_wonderworker", priority = 2),
            "0524" to Holiday(name = "cyril_and_methodius", priority = 3),
            "0603" to Holiday(name = "constantine_and_helena", priority = 3),
            "0707" to Holiday(name = "nativity_of_john_the_baptist", priority = 2),
            "0712" to Holiday(name = "feast_of_saints_peter_and_paul", priority = 2),
            "0718" to Holiday(name = "sergius_of_radonezh_relics", priority = 3),
            "0721" to Holiday(name = "kazan_icon_of_the_theotokos_summer", priority = 3),
            "0724" to Holiday(name = "olga_equal_to_the_apostles", priority = 3),
            "0728" to Holiday(name = "vladimir_equal_to_the_apostles", priority = 3),
            "0801" to Holiday(name = "seraphim_of_sarov_glorification", priority = 3),
            "0802" to Holiday(name = "elijah_the_prophet", priority = 2),
            "0809" to Holiday(name = "panteleimon_the_healer", priority = 3),
            "0814" to Holiday(name = "origin_of_the_venerable_trees_of_the_cross", priority = 2),
            "0819" to Holiday(name = "transfiguration_of_the_lord", priority = 1),
            "0828" to Holiday(name = "dormition_of_the_most_holy_theotokos", priority = 1),
            "0829" to Holiday(name = "image_of_christ_not_made_by_hands", priority = 2),
            "0911" to Holiday(name = "beheading_of_john_the_baptist", priority = 2),
            "0914" to Holiday(name = "church_new_year", priority = 3),
            "0921" to Holiday(name = "nativity_of_the_most_holy_theotokos", priority = 1),
            "0927" to Holiday(name = "exaltation_of_the_holy_cross_lord", priority = 1),
            "1008" to Holiday(name = "sergius_of_radonezh_repose", priority = 3),
            "1009" to Holiday(name = "john_the_theologian_repose", priority = 3),
            "1014" to Holiday(name = "protection_of_the_most_holy_theotokos", priority = 2),
            "1104" to Holiday(name = "kazan_icon_of_the_theotokos_autumn", priority = 3),
            "1108" to Holiday(name = "demetrius_of_thessalonica", priority = 3),
            "1121" to Holiday(name = "synaxis_of_archangel_michael", priority = 2),
            "1204" to Holiday(name = "entrance_of_the_most_holy_theotokos_into_the_temple", priority = 1),
            "1209" to Holiday(name = "george_the_victorious_autumn", priority = 3),
            "1219" to Holiday(name = "nicholas_the_wonderworker", priority = 2),
            "1225" to Holiday(name = "spyridon_of_trimythous", priority = 3)
        )
    }

    fun getWesternFixedHolidays() {
        fixedHolidays = mutableMapOf(
            "0106" to Holiday(name = "epiphany", priority = 1),
            "0125" to Holiday(name = "paul_conversion", priority = 2),
            "0202" to Holiday(name = "presentation_of_the_lord", priority = 2),
            "0319" to Holiday(name = "joseph", priority = 2),
            "0325" to Holiday(name = "annunciation_of_the_most_holy_theotokos", priority = 1),
            "0425" to Holiday(name = "mark", priority = 2),
            "0531" to Holiday(name = "visitation", priority = 2),
            "0611" to Holiday(name = "barnabas", priority = 3),
            "0624" to Holiday(name = "nativity_of_john_the_baptist", priority = 1),
            "0629" to Holiday(name = "feast_of_saints_peter_and_paul", priority = 1),
            "0722" to Holiday(name = "magdalene", priority = 2),
            "0725" to Holiday(name = "james", priority = 2),
            "0806" to Holiday(name = "transfiguration_of_the_lord", priority = 2),
            "0824" to Holiday(name = "bartholomew", priority = 2),
            "0829" to Holiday(name = "beheading_of_john_the_baptist", priority = 3),
            "0914" to Holiday(name = "exaltation_of_the_holy_cross_lord", priority = 2),
            "0921" to Holiday(name = "matthew", priority = 2),
            "0929" to Holiday(name = "archangels", priority = 2),
            "1018" to Holiday(name = "luke", priority = 2),
            "1028" to Holiday(name = "simon_and_jude", priority = 2),
            "1101" to Holiday(name = "all_saints", priority = 1),
            "1111" to Holiday(name = "martin_of_tours", priority = 3),
            "1130" to Holiday(name = "andrew", priority = 2),
            "1206" to Holiday(name = "nicholas_of_myra", priority = 3),
            "1213" to Holiday(name = "lucy_of_syracuse", priority = 3),
            "1225" to Holiday(name = "christmas", priority = 1),
            "1226" to Holiday(name = "stephen", priority = 2),
            "1227" to Holiday(name = "john_the_apostle", priority = 2),
            "1228" to Holiday(name = "innocents", priority = 2)
        )
    }

    fun getCatholicFixedHolidays() {
        getWesternFixedHolidays()

        fixedHolidays.put("0101", Holiday(name = "solemnity_of_mary", priority = 1))
        fixedHolidays.put("0211", Holiday(name = "our_lady_of_lourdes", priority = 3))
        fixedHolidays.put("0214", Holiday(name = "cyril_and_methodius", priority = 2))
        fixedHolidays.put("0222", Holiday(name = "chair_of_peter", priority = 2))
        fixedHolidays.put("0317", Holiday(name = "patrick_of_ireland", priority = 3))
        fixedHolidays.put("0319", Holiday(name = "joseph", priority = 1))
        fixedHolidays.put("0423", Holiday(name = "george_the_victorious", priority = 3))
        fixedHolidays.put("0503", Holiday(name = "philip_and_james", priority = 2))
        fixedHolidays.put("0513", Holiday(name = "our_lady_of_fatima", priority = 3))
        fixedHolidays.put("0514", Holiday(name = "matthias", priority = 2))
        fixedHolidays.put("0613", Holiday(name = "anthony_of_padua", priority = 3))
        fixedHolidays.put("0703", Holiday(name = "thomas", priority = 2))
        fixedHolidays.put("0711", Holiday(name = "benedict_of_nursia", priority = 3))
        fixedHolidays.put("0716", Holiday(name = "our_lady_of_mount_carmel", priority = 3))
        fixedHolidays.put("0726", Holiday(name = "joachim_and_anne", priority = 3))
        fixedHolidays.put("0731", Holiday(name = "ignatius_of_loyola", priority = 3))
        fixedHolidays.put("0810", Holiday(name = "lawrence", priority = 2))
        fixedHolidays.put("0815", Holiday(name = "assumption", priority = 1))
        fixedHolidays.put("0822", Holiday(name = "queenship_of_mary", priority = 3))
        fixedHolidays.put("0828", Holiday(name = "augustine_of_hippo", priority = 3))
        fixedHolidays.put("0908", Holiday(name = "nativity_of_the_most_holy_theotokos", priority = 2))
        fixedHolidays.put("0915", Holiday(name = "our_lady_of_sorrows", priority = 3))
        fixedHolidays.put("1001", Holiday(name = "therese_of_lisieux", priority = 3))
        fixedHolidays.put("1002", Holiday(name = "guardian_angels", priority = 3))
        fixedHolidays.put("1004", Holiday(name = "francis_of_assisi", priority = 3))
        fixedHolidays.put("1007", Holiday(name = "our_lady_of_the_rosary", priority = 3))
        fixedHolidays.put("1015", Holiday(name = "teresa_of_avila", priority = 3))
        fixedHolidays.put("1022", Holiday(name = "john_paul_ii", priority = 3))
        fixedHolidays.put("1102", Holiday(name = "all_souls", priority = 1))
        fixedHolidays.put("1109", Holiday(name = "dedication_lateran_basilica_lord", priority = 2))
        fixedHolidays.put("1121", Holiday(name = "presentation_of_mary", priority = 3))
        fixedHolidays.put("1203", Holiday(name = "francis_xavier", priority = 3))
        fixedHolidays.put("1208", Holiday(name = "immaculate_conception", priority = 1))
        fixedHolidays.put("1212", Holiday(name = "our_lady_of_guadalupe", priority = 3))
        fixedHolidays.put("1224", Holiday(name = "christmas_eve", priority = 2))
    }

    fun getLutheranFixedHolidays() {
        getWesternFixedHolidays()

        fixedHolidays.put("0101", Holiday(name = "circumcision_of_the_lord", priority = 1))
        fixedHolidays.put("0118", Holiday(name = "confession_of_peter", priority = 3))
        fixedHolidays.put("0218", Holiday(name = "martin_luther", priority = 3))
        fixedHolidays.put("0224", Holiday(name = "matthias", priority = 2))
        fixedHolidays.put("0503", Holiday(name = "philip_and_james", priority = 2))
        fixedHolidays.put("0625", Holiday(name = "augsburg_confession", priority = 2))
        fixedHolidays.put("0815", Holiday(name = "mary_virgin", priority = 2))
        fixedHolidays.put("1031", Holiday(name = "reformation_day", priority = 2))
        fixedHolidays.put("1221", Holiday(name = "thomas", priority = 2))
        fixedHolidays.put("1224", Holiday(name = "christmas_vigil", priority = 2))
    }

    fun moveCatholicFixedHolidays(easterDate: LocalDate){
        val josephDate = LocalDate.of(easterDate.year, 3, 19)
        val annunciationDate = LocalDate.of(easterDate.year, 3, 25)
        if (annunciationDate.isAfter(easterDate.minusDays(46)) && annunciationDate.isBefore(easterDate.minusDays(7)) && annunciationDate.dayOfWeek == DayOfWeek.SUNDAY){
            fixedHolidays.remove("0325")
            fixedHolidays.put("0326",
                Holiday(name = "annunciation_of_the_most_holy_theotokos", priority = 1)
            )
        } else if (annunciationDate.isAfter(easterDate.minusDays(8)) && annunciationDate.isBefore(easterDate.plusDays(7))){
            fixedHolidays.remove("0325")
            fixedHolidays.put((if (easterDate.plusDays(8).dayOfMonth < 10) "0" + easterDate.plusDays(8).monthValue + "0" else "0" + easterDate.plusDays(8).monthValue) + easterDate.plusDays(8).dayOfMonth,
                Holiday(name = "annunciation_of_the_most_holy_theotokos", priority = 1)
            )
        }
        if (josephDate.isAfter(easterDate.minusDays(46)) && josephDate.isBefore(easterDate.minusDays(7)) && annunciationDate.dayOfWeek == DayOfWeek.SUNDAY){
            fixedHolidays.remove("0319")
            fixedHolidays.put("0320", Holiday(name = "joseph", priority = 1))
        } else if (josephDate.isAfter(easterDate.minusDays(8)) && josephDate.isBefore(easterDate)){
            fixedHolidays.remove("0319")
            fixedHolidays.put("03" + easterDate.minusDays(8).dayOfMonth,
                Holiday(name = "joseph", priority = 1)
            )
        }
    }

    fun moveLutheranFixedHolidays(easterDate: LocalDate){
        val annunciationDate = LocalDate.of(easterDate.year, 3, 25)
        if (annunciationDate.isAfter(easterDate.minusDays(8)) && annunciationDate.isBefore(easterDate.plusDays(7))){
            fixedHolidays.remove("0325")
            fixedHolidays.put("03" + easterDate.minusDays(7).with(
                TemporalAdjusters.previous(
                DayOfWeek.SUNDAY)).dayOfMonth,
                Holiday(name = "annunciation_of_the_most_holy_theotokos", priority = 1)
            )
        }
    }

    fun calculateBasicMovableHolidays(easterDate: LocalDate): MutableMap<String, LocalDate> {
        val holidays = mutableMapOf<String, LocalDate>()

        holidays["palm_sunday_greatfeast"] = easterDate.minusDays(7)
        holidays["reserved_holy_monday"] = easterDate.minusDays(6)
        holidays["reserved_holy_tuesday"] = easterDate.minusDays(5)
        holidays["reserved_holy_wednesday"] = easterDate.minusDays(4)
        holidays["reserved_holy_thursday"] = easterDate.minusDays(3)
        holidays["reserved_holy_friday"] = easterDate.minusDays(2)
        holidays["reserved_holy_saturday"] = easterDate.minusDays(1)
        holidays["reserved_easter_monday"] = easterDate.plusDays(1)
        holidays["reserved_easter_tuesday"] = easterDate.plusDays(2)
        holidays["reserved_easter_wednesday"] = easterDate.plusDays(3)
        holidays["reserved_easter_thursday"] = easterDate.plusDays(4)
        holidays["reserved_easter_friday"] = easterDate.plusDays(5)
        holidays["reserved_easter_saturday"] = easterDate.plusDays(6)
        holidays["ascension_of_the_lord_greatfeast"] = easterDate.plusDays(39)
        holidays["pentecost_greatfeast"] = easterDate.plusDays(49)

        return holidays
    }

    fun calculateOrthodoxMovableHolidays(easterDate: LocalDate): MutableMap<String, LocalDate> {
        val holidays = calculateBasicMovableHolidays(easterDate)

        holidays["publican_and_pharisee_sunday"] = easterDate.minusDays(70)
        holidays["prodigal_son_sunday"] = easterDate.minusDays(63)
        holidays["universal_memorial_saturday"] = easterDate.minusDays(57)
        holidays["meatfare_sunday"] = easterDate.minusDays(56)
        holidays["forgiveness_sunday"] = easterDate.minusDays(49)
        holidays["clean_monday"] = easterDate.minusDays(48)
        holidays["triumph_of_orthodoxy"] = easterDate.minusDays(42)
        holidays["lent_saturday_2_memorial"] = easterDate.minusDays(36)
        holidays["gregory_palamas_sunday"] = easterDate.minusDays(35)
        holidays["lent_saturday_3_memorial"] = easterDate.minusDays(29)
        holidays["veneration_of_the_cross_sunday"] = easterDate.minusDays(28)
        holidays["lent_saturday_4_memorial"] = easterDate.minusDays(22)
        holidays["john_climacus_sunday"] = easterDate.minusDays(21)
        holidays["lent_saturday_5"] = easterDate.minusDays(15)
        holidays["mary_of_egypt_sunday"] = easterDate.minusDays(14)
        holidays["lazarus_saturday"] = easterDate.minusDays(8)

        holidays["thomas_sunday"] = easterDate.plusDays(7)
        holidays["radonitsa_memorial"] = easterDate.plusDays(9)
        holidays["myrrhbearing_women_sunday"] = easterDate.plusDays(14)
        holidays["paralytic_sunday"] = easterDate.plusDays(21)
        holidays["mid_pentecost"] = easterDate.plusDays(25)
        holidays["samaritan_woman_sunday"] = easterDate.plusDays(28)
        holidays["blind_man_sunday"] = easterDate.plusDays(35)
        holidays["easter_leavetaking"] = easterDate.plusDays(38)
        holidays["fathers_of_first_council_sunday"] = easterDate.plusDays(42)

        holidays["trinity_saturday_memorial"] = easterDate.plusDays(48)
        holidays["day_of_the_holy_spirit"] = easterDate.plusDays(50)
        holidays["all_saints_sunday"] = easterDate.plusDays(56)
        holidays["all_saints_of_russia_sunday"] = easterDate.plusDays(63)

        holidays["demetrius_saturday_memorial"] = LocalDate.of(easterDate.year, Month.NOVEMBER, 8)
            .minusDays(1)
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY))

        return holidays
    }

    fun calculateWesternMovableHolidays(easterDate: LocalDate): MutableMap<String, LocalDate> {
        val holidays = calculateBasicMovableHolidays(easterDate)

        holidays["theophany_lord"] = LocalDate.of(easterDate.year, 1, 6).with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
        holidays["ash_wednesday"] = easterDate.minusDays(46)
        holidays["pentecost_monday"] = easterDate.plusDays(50)
        holidays["day_of_the_holy_trinity_greatfeast"] = easterDate.plusDays(56)
        val christmas = LocalDate.of(easterDate.year, 12, 25)

        holidays["reserved_advent_1_sunday"] = christmas.with(
            TemporalAdjusters.previous(
                DayOfWeek.SUNDAY)).minusWeeks(3)
        holidays["reserved_advent_2_sunday"] = christmas.with(
            TemporalAdjusters.previous(
                DayOfWeek.SUNDAY)).minusWeeks(2)
        holidays["reserved_advent_3_sunday"] = christmas.with(
            TemporalAdjusters.previous(
                DayOfWeek.SUNDAY)).minusWeeks(1)
        holidays["reserved_advent_4_sunday"] = christmas.with(
            TemporalAdjusters.previous(
                DayOfWeek.SUNDAY))
        return holidays
    }

    fun calculateCatholicMovableHolidays(easterDate: LocalDate): MutableMap<String, LocalDate> {
        val holidays = calculateWesternMovableHolidays(easterDate)
        val christmas = LocalDate.of(easterDate.year, 12, 25)
        holidays["reserved_divine_mercy_sunday"] = easterDate.plusDays(7)
        holidays["corpus_christi_greatfeast"] = easterDate.plusDays(60)
        holidays["mary_mother_of_the_church_memorial"] = easterDate.plusDays(50)
        holidays["jesus_heart_greatfeast"] = easterDate.plusDays(68)
        holidays["mary_heart_memorial"] = easterDate.plusDays(69)
        holidays["jesus_king_greatfeast"] = christmas.with(
            TemporalAdjusters.previous(DayOfWeek.SUNDAY))
            .minusWeeks(4)
        holidays["holy_family_lord"] =
            if (christmas.dayOfWeek == DayOfWeek.SUNDAY) LocalDate.of(easterDate.year, 12, 30)
            else christmas.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))

        return holidays
    }

    fun calculateLutheranMovableHolidays(easterDate: LocalDate): MutableMap<String, LocalDate> {
        val holidays = calculateWesternMovableHolidays(easterDate)

        holidays["eternity_sunday"] = LocalDate.of(easterDate.year, 12, 25).with(
            TemporalAdjusters.previous(
                DayOfWeek.SUNDAY)).minusWeeks(4)

        return holidays
    }

    fun getFastLevel(
        date: LocalDate,
        easterDate: LocalDate,
        confession: Confession,
    ): FastLevel = when (confession) {
        Confession.cat -> when {
            date == easterDate.minusDays(46) || date == easterDate.minusDays(2) -> FastLevel.FAST
            date.dayOfWeek == DayOfWeek.FRIDAY -> FastLevel.ABSTINENCE
            else -> FastLevel.NO_FAST
        }
        Confession.lut -> FastLevel.NO_FAST
        Confession.ort -> getOrthodoxFastLevel(date, easterDate)
    }

    private fun getOrthodoxFastLevel(date: LocalDate, easterDate: LocalDate): FastLevel {
        val dow = date.dayOfWeek
        val md = MonthDay.from(date)
        val weekend = dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY
        val wedFri = dow == DayOfWeek.WEDNESDAY || dow == DayOfWeek.FRIDAY
        val monWedFri = wedFri || dow == DayOfWeek.MONDAY
        val daysToEaster = ChronoUnit.DAYS.between(date, easterDate).toInt()

        if (isContinuousWeek(date, easterDate)) return FastLevel.CONTINUOUS_WEEK

        if (isGreatLent(date, easterDate)) {
            return when {
                daysToEaster == 2 -> FastLevel.XEROPHAGY
                daysToEaster == 1 -> FastLevel.NO_OIL
                daysToEaster in 3..6 -> {
                    if (md == MonthDay.of(4, 7)) FastLevel.OIL_ALLOWED else FastLevel.XEROPHAGY
                }
                daysToEaster == 7 -> FastLevel.FISH
                daysToEaster == 8 -> FastLevel.FISH
                md == MonthDay.of(4, 7) -> FastLevel.FISH
                md == MonthDay.of(3, 22) -> FastLevel.OIL_ALLOWED
                weekend -> FastLevel.OIL_ALLOWED
                monWedFri -> FastLevel.XEROPHAGY
                else -> FastLevel.NO_OIL
            }
        }

        if (isChristmasFast(md)) return christmasFastLevel(md, weekend, wedFri, monWedFri)

        if (isPeterFast(date, easterDate)) {
            return when {
                md == MonthDay.of(7, 7) -> FastLevel.FISH
                wedFri -> FastLevel.OIL_ALLOWED
                else -> FastLevel.FISH
            }
        }

        if (isDormitionFast(md)) {
            return when {
                md == MonthDay.of(8, 19) -> FastLevel.FISH
                monWedFri -> FastLevel.XEROPHAGY
                weekend -> FastLevel.OIL_ALLOWED
                else -> FastLevel.NO_OIL
            }
        }

        if (md == MonthDay.of(1, 18)) return if (weekend) FastLevel.OIL_ALLOWED else FastLevel.XEROPHAGY
        if (md == MonthDay.of(9, 11) || md == MonthDay.of(9, 27)) return FastLevel.OIL_ALLOWED

        if (wedFri) {
            if (md == MonthDay.of(1, 7) || md == MonthDay.of(1, 19)) return FastLevel.NO_FAST

            val absoluteNoFastDays = setOf(
                MonthDay.of(2, 15),
                MonthDay.of(7, 7),
                MonthDay.of(7, 12),
                MonthDay.of(8, 28),
                MonthDay.of(9, 21),
                MonthDay.of(10, 14)
            )
            if (md in absoluteNoFastDays) return FastLevel.NO_FAST

            val paschalPeriod = daysToEaster in -48..-1
            val fishHolidays = setOf(
                MonthDay.of(5, 21),
                MonthDay.of(12, 4)
            )

            return if (paschalPeriod || md in fishHolidays) FastLevel.FISH else FastLevel.FAST
        }

        return FastLevel.NO_FAST
    }

    private fun christmasFastLevel(
        md: MonthDay,
        weekend: Boolean,
        wedFri: Boolean,
        monWedFri: Boolean
    ): FastLevel {
        val nov28 = MonthDay.of(11, 28)
        val dec19 = MonthDay.of(12, 19)
        val dec20 = MonthDay.of(12, 20)
        val dec31 = MonthDay.of(12, 31)
        val jan1 = MonthDay.of(1, 1)
        val jan2 = MonthDay.of(1, 2)
        val jan6 = MonthDay.of(1, 6)

        return when {
            (!md.isBefore(nov28) && !md.isAfter(dec19)) -> when {
                md == MonthDay.of(12, 4) || md == MonthDay.of(12, 19) -> FastLevel.FISH
                wedFri -> FastLevel.OIL_ALLOWED
                else -> FastLevel.FISH
            }
            (!md.isBefore(dec20) && !md.isAfter(dec31)) || md == jan1 -> when {
                wedFri -> FastLevel.NO_OIL
                weekend -> FastLevel.FISH
                else -> FastLevel.OIL_ALLOWED
            }
            (!md.isBefore(jan2) && !md.isAfter(jan6)) -> when {
                md == jan6 && !weekend -> FastLevel.XEROPHAGY
                weekend -> FastLevel.OIL_ALLOWED
                monWedFri -> FastLevel.XEROPHAGY
                else -> FastLevel.NO_OIL
            }
            else -> FastLevel.NO_FAST
        }
    }

    private fun isChristmasFast(md: MonthDay): Boolean {
        val start = MonthDay.of(11, 28)
        val end = MonthDay.of(1, 6)
        return !md.isBefore(start) || !md.isAfter(end)
    }

    private fun isDormitionFast(md: MonthDay): Boolean {
        val start = MonthDay.of(8, 14)
        val end = MonthDay.of(8, 27)
        return !md.isBefore(start) && !md.isAfter(end)
    }


    private fun isGreatLent(date: LocalDate, easterDate: LocalDate): Boolean {
        val start = easterDate.minusDays(48)
        val end = easterDate.minusDays(1)
        return !date.isBefore(start) && !date.isAfter(end)
    }

    private fun isContinuousWeek(date: LocalDate, easterDate: LocalDate): Boolean {
        val year = date.year

        val christmastideStart = LocalDate.of(year, 1, 7)
        val christmastideEnd = LocalDate.of(year, 1, 17)
        val publicanWeekStart = easterDate.minusDays(69)
        val publicanWeekEnd = easterDate.minusDays(63)
        val cheeseFareWeekStart = easterDate.minusDays(55)
        val cheeseFareWeekEnd = easterDate.minusDays(49)
        val brightWeekStart = easterDate.plusDays(1)
        val brightWeekEnd = easterDate.plusDays(6)
        val pentecostWeekStart = easterDate.plusDays(50)
        val pentecostWeekEnd = easterDate.plusDays(56)

        return (!date.isBefore(christmastideStart) && !date.isAfter(christmastideEnd)) ||
                (!date.isBefore(publicanWeekStart) && !date.isAfter(publicanWeekEnd)) ||
                (!date.isBefore(cheeseFareWeekStart) && !date.isAfter(cheeseFareWeekEnd)) ||
                (!date.isBefore(brightWeekStart) && !date.isAfter(brightWeekEnd)) ||
                (!date.isBefore(pentecostWeekStart) && !date.isAfter(pentecostWeekEnd))
    }

    private fun isPeterFast(date: LocalDate, easterDate: LocalDate): Boolean {
        val start = easterDate.plusDays(57)
        val end = LocalDate.of(date.year, 7, 11)
        return !date.isBefore(start) && !date.isAfter(end)
    }


    fun getMonthCalendar(year: Int, month: Int, confession: Confession): List<CalendarDay> {
        val today = LocalDate.now()
        val firstDay = LocalDate.of(year, month, 1)
        val lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth())
        val easterDate = if (confession == Confession.ort) calculateOrthodoxEaster(year) else calculateGregorianEaster(year)
        val movableHolidays: MutableMap<String, LocalDate>

        when (confession) {
            Confession.ort -> {
                getOrthodoxFixedHolidays(); movableHolidays =
                    calculateOrthodoxMovableHolidays(easterDate)
            }

            Confession.cat -> {
                getCatholicFixedHolidays(); movableHolidays =
                    calculateCatholicMovableHolidays(easterDate); moveCatholicFixedHolidays(
                    easterDate
                )
            }

            Confession.lut -> {
                getLutheranFixedHolidays(); movableHolidays =
                    calculateLutheranMovableHolidays(easterDate); moveLutheranFixedHolidays(
                    easterDate
                )
            }
        }

        val movableByDate: Map<LocalDate, List<String>> = movableHolidays.entries
            .groupBy({ it.value }, { it.key })

        var currentDate = firstDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

        val totalDaysInGrid = if (
            (firstDay.dayOfWeek == DayOfWeek.SUNDAY && lastDay.dayOfMonth >= 30) ||
            (firstDay.dayOfWeek == DayOfWeek.SATURDAY && lastDay.dayOfMonth == 31)
        ) 42 else 35

        return List(totalDaysInGrid) {
            val day = generateCalendarDay(currentDate, today, easterDate, confession, fixedHolidays, movableByDate)
                .copy(currentMonth = month == currentDate.monthValue)
            currentDate = currentDate.plusDays(1)
            day
        }
    }

    fun getDailyCalendar(confession: Confession): CalendarDay {
        val today = LocalDate.now()
        val easterDate = if (confession == Confession.ort) calculateOrthodoxEaster(today.year) else calculateGregorianEaster(today.year)
        val movableHolidays: MutableMap<String, LocalDate>

        when (confession) {
            Confession.ort -> {
                getOrthodoxFixedHolidays(); movableHolidays =
                    calculateOrthodoxMovableHolidays(easterDate)
            }

            Confession.cat -> {
                getCatholicFixedHolidays(); movableHolidays =
                    calculateCatholicMovableHolidays(easterDate); moveCatholicFixedHolidays(
                    easterDate
                )
            }

            Confession.lut -> {
                getLutheranFixedHolidays(); movableHolidays =
                    calculateLutheranMovableHolidays(easterDate); moveLutheranFixedHolidays(
                    easterDate
                )
            }
        }

        val movableByDate = movableHolidays.entries.groupBy({ it.value }, { it.key })

        return generateCalendarDay(today, today, easterDate, confession, fixedHolidays, movableByDate)
    }

    private fun generateCalendarDay(
        date: LocalDate,
        today: LocalDate,
        easterDate: LocalDate,
        confession: Confession,
        fixedHolidays: Map<String, Holiday>,
        movableByDate: Map<LocalDate, List<String>>
    ): CalendarDay {
        val dayHolidays = mutableListOf<Holiday>()

        val monthDayKey = String.format("%02d%02d", date.monthValue, date.dayOfMonth)
        fixedHolidays[monthDayKey]?.let { dayHolidays.add(it) }

        movableByDate[date]?.forEach { name ->
            val priority = when {
                name.contains("greatfeast") -> 1
                name.contains("memorial") -> 3
                else -> 2
            }
            dayHolidays.add(Holiday(name = name, priority = priority))
        }

        if (date == easterDate) {
            dayHolidays.add(Holiday(name = "easter", priority = 0))
        }

        val fastLevel = if (confession == Confession.cat && dayHolidays.isNotEmpty()) {
            filterCatholicHolidays(dayHolidays, date, easterDate)

            if (dayHolidays.any { it.priority < 2 }) {
                FastLevel.NO_FAST
            } else getFastLevel(date, easterDate, confession)
        } else getFastLevel(date, easterDate, confession)

        return CalendarDay(
            date = date,
            holidays = dayHolidays.sortedBy { it.priority },
            fastLevel = fastLevel,
            isToday = date == today
        )
    }

    private fun filterCatholicHolidays(dayHolidays: MutableList<Holiday>, date: LocalDate, easterDate: LocalDate) {
        val maxPriority = dayHolidays.minOf { it.priority }
        val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY
        val isHolyOrEasterOctave = date.isAfter(easterDate.minusDays(7)) && date.isBefore(easterDate.plusDays(8))

        dayHolidays.removeIf { holiday ->
            !holiday.name.contains("easter") && !holiday.name.contains("reserved_") && isHolyOrEasterOctave
        }
        dayHolidays.removeIf { holiday ->
            (maxPriority < 3 || isSunday) && holiday.priority == 3
        }
        dayHolidays.removeIf { holiday ->
            val isException = holiday.name.contains("easter") || holiday.name.contains("reserved_") || holiday.name.contains("_lord")
            !isException && holiday.priority == 2 && (maxPriority < 2 || isSunday)
        }
    }
}