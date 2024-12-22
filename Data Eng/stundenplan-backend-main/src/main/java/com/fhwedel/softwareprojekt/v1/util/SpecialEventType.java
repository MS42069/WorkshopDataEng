package com.fhwedel.softwareprojekt.v1.util;

/** Represents the event category that may be assigned to a special event. */
public enum SpecialEventType {

    /** MONDAY_PLAN (= Montags-Stundenplan gültig) */
    MONDAY_PLAN,
    /** TUESDAY_PLAN (= Dienstags-Stundenplan gültig) */
    TUESDAY_PLAN,
    /** WEDNESDAY_PLAN (= Mittwochs-Stundenplan gültig) */
    WEDNESDAY_PLAN,
    /** THURSDAY_PLAN (= Donnerstags-Stundenplan gültig) */
    THURSDAY_PLAN,
    /** FRIDAY_PLAN (= Freitags-Stundenplan gültig) */
    FRIDAY_PLAN,
    /** SATURDAY_PLAN (= Samstags-Stundenplan gültig) */
    SATURDAY_PLAN,

    /** SUNDAY_PLAN (= Sonntags-Stundenplan gültig) */
    SUNDAY_PLAN,
    /** FREE (= FREI) */
    FREE
}
