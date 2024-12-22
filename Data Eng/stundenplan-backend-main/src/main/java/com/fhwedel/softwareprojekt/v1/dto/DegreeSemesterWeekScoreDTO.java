package com.fhwedel.softwareprojekt.v1.dto;

import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreAndWeight;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Response DTO that provides the data of the score of a timetable")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DegreeSemesterWeekScoreDTO {

	@Schema(description = "The week of the evaluation")
	private int week;

	@Schema(description = "totalScore für die Woche")
	private StudentEvaluationScoreAndWeight totalScore;

	/** Score und Weight welcher anhand der freien Tage ermittelt wird */
	@Schema(description = "Score and weight based on free days")
	StudentEvaluationScoreAndWeight freeDayScore;

	/** Score und Weight der die Anzahl von kurzen Tagen angibt */
	@Schema(description = "Score and weight indicating the number of short days")
	StudentEvaluationScoreAndWeight shortDayScore;

	/**
	 * Score und Weight der bewertet wie oft Online und Offline Veranstaltungen aneinander Grenzen
	 */
	@Schema(
			description =
					"Score and weight evaluating how often online and offline events border each other")
	StudentEvaluationScoreAndWeight onlineToOfflineConnectionScore;

	/** Score und Weight der die Tage mit Veranstaltungen im ersten Zeitslot bewertet */
	@Schema(description = "Score and weight evaluating days with events in the first timeslot")
	StudentEvaluationScoreAndWeight firstTimeslotScore;

	/** Score und Weight der die Tage mit Veranstaltungen im letzten Zeitslot bewertet */
	@Schema(description = "Score and weight evaluating days with events in the last timeslot")
	StudentEvaluationScoreAndWeight lastTimeslotScore;

	/** Score und Weight der Tage mit langen Pausen bewertet */
	@Schema(description = "Score and weight evaluating days with long gaps")
	StudentEvaluationScoreAndWeight longGapsScore;
}
