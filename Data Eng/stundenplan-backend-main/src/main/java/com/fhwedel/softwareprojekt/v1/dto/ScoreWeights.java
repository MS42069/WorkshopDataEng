package com.fhwedel.softwareprojekt.v1.dto;

/**
 * This class defines the weights and descriptions for scoring criteria used in the timetable
 * evaluation.
 */
public class ScoreWeights {

    /** Weight for evaluating free days. */
    public final int FREE_DAYS_WEIGHT = 478;

    /** Description for evaluating free days. */
    public final String FREE_DAYS_DESCRIPTION =
            "bewertet Positiv wenn der Student einen Freien Tag in der Woche hat. Besonders gut wird es bewertet, wenn Montag oder Freitag frei ist.";

    /** Weight for evaluating days with only one event. */
    public final int DAYS_WITH_ONE_EVENT_WEIGHT = 447;

    /** Description for evaluating days with only one event. */
    public final String DAYS_WITH_ONE_EVENT_DESCRIPTION =
            "bewertet Tage mit nur einem Event negativ. (viele Tage mit nur einem Event = niedriger Score)";

    /** Weight for evaluating connections between online and offline events. */
    public final int CONNECTIONS_ONLINE_TO_OFFLINE_WEIGHT = 413;

    /** Description for evaluating connections between online and offline events. */
    public final String CONNECTIONS_ONLINE_TO_OFFLINE_DESCRIPTION =
            "bewertet Tage mit einer Online Veranstaltung in direkter Folge auf eine Präsenzveranstaltung oder anders herum negativ.";

    /** Weight for evaluating events in the last timeslot. */
    public final int EVENT_IN_LAST_TIMESLOT_WEIGHT = 384;

    /** Description for evaluating events in the last timeslot. */
    public final String EVENT_IN_LAST_TIMESLOT_DESCRIPTION =
            "bewertet Tage mit Events im letzten Timeslot negativ";

    /** Weight for evaluating events in the first timeslot. */
    public final int EVENT_IN_FIRST_TIMESLOT_WEIGHT = 358;

    /** Description for evaluating events in the first timeslot. */
    public final String EVENT_IN_FIRST_TIMESLOT_DESCRIPTION =
            "bewertet Tage mit Events im ersteb Timeslot negativ";

    /** Weight for evaluating long breaks between events. */
    public final int LONG_BREAKS_WEIGHT = 173;

    /** Description for evaluating long breaks between events. */
    public final String LONG_BREAKS_DESCRIPTION =
            "bewertet Tage mit Events mit langen Pausen negativ (eine lange Pause sind mindestens 2 Zeitslots Pause)";

    /** Total weight calculated as the sum of individual weights. */
    public final int TOTAL_WEIGHT =
            FREE_DAYS_WEIGHT
                    + DAYS_WITH_ONE_EVENT_WEIGHT
                    + CONNECTIONS_ONLINE_TO_OFFLINE_WEIGHT
                    + EVENT_IN_LAST_TIMESLOT_WEIGHT
                    + EVENT_IN_FIRST_TIMESLOT_WEIGHT
                    + LONG_BREAKS_WEIGHT;
}
