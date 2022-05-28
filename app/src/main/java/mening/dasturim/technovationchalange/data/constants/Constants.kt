package mening.dasturim.technovationchalange.data.constants

import mening.dasturim.technovationchalange.data.local.PrefsHelper
import mening.dasturim.technovationchalange.data.module.CalendarItems

object Constants {
    lateinit var prefs: PrefsHelper

    var language = ""

    const val LANGUAGE_RUSSIAN: String = "ru"
    const val LANGUAGE_UZBEK: String = "uz"
    const val LANGUAGE_ENGLISH: String = "en"

    const val CALL_CENTER_NUMBER_UZMOBILE = "1099"
    const val CALL_CENTER_NUMBER_MOBIEUZ = "0890"
    const val CALL_CENTER_NUMBER_UCELL = "8123"
    const val CALL_CENTER_NUMBER_BEELINE = "0611"

    const val CALL_BALANS = "*100#"
    const val CALL_BALANS_BEELINE = "*102#"

    fun getCalendarItems():ArrayList<CalendarItems>{
        return arrayListOf(
            CalendarItems("Mon","9"),
            CalendarItems("Tue","10"),
            CalendarItems("Wed","11"),
            CalendarItems("Thu","12"),
            CalendarItems("Fri","13"),
            CalendarItems("Sat","14"),
            CalendarItems("San","15"),
            CalendarItems("Mon","16"),
            CalendarItems("Tue","17"),
            CalendarItems("Wed","18"),
            CalendarItems("Thu","19"),
            CalendarItems("Fri","20"),
            CalendarItems("Sat","21"),
            CalendarItems("San","22")
        )
    }

}