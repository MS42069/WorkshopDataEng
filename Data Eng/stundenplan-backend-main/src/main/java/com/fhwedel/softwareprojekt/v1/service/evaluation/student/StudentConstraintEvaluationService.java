package com.fhwedel.softwareprojekt.v1.service.evaluation.student;

import com.fhwedel.softwareprojekt.v1.dto.DegreeScoreDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterScoreDTO;
import com.fhwedel.softwareprojekt.v1.dto.DegreeSemesterWeekScoreDTO;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreAndWeight;
import com.fhwedel.softwareprojekt.v1.dto.evaluate.StudentEvaluationScoreDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Degree;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.repository.DegreeRepository;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 1) Veranstaltungsfreie(n) Tage(e) in der Woche: 478 3) Keine Tage mit nur einer Veranstaltung 447
 * 4) Keine Online-Veranstaltung und Präsenzveranstaltung direkt hintereinander 413 5) Keine
 * Veranstaltung im letzten Zeitslot 384 6) Keine Veranstaltung im ersten Zeitslot 358 7) Tage ohne
 * Präsenzveranstaltungen / nur Online-Veranstaltungen: 225 8) TODO Ein Zeitslot Pause zwischen
 * Veranstaltungen (z.B. eine Mittagspause) 207 9) Tage ohne Online-Veranstaltungen / nur
 * Präsenzveranstaltungen: 173
 */

/**
 * Service for evaluating student soft constraints related to course scheduling.
 */
@Service
@RequiredArgsConstructor
public class StudentConstraintEvaluationService {

	/**
	 * Total weight for all soft constraints.
	 */
	private static final int TOTAL_WEIGHT =
			StudentFirstTimeslotEvaluationService.WEIGHT
					+ StudentLastTimeslotEvaluationService.WEIGHT
					+ StudentFreeDaysEvaluationService.WEIGHT
					+ StudentOneEventOnlyEvaluationService.WEIGHT
					+ StudentLongGapsEvaluationService.WEIGHT
					+ StudentConnectionOnlineToOfflineEvaluationService.WEIGHT;

	/**
	 * Service for evaluating the first timeslot constraint.
	 */
	private final StudentFirstTimeslotEvaluationService studentFirstTimeslotEvaluationService;

	/**
	 * Service for evaluating the last timeslot constraint.
	 */
	private final StudentLastTimeslotEvaluationService studentLastTimeslotEvaluationService;

	/**
	 * Service for evaluating the free days constraint.
	 */
	private final StudentFreeDaysEvaluationService studentFreeDaysEvaluationService;

	/**
	 * Service for evaluating the one event only constraint.
	 */
	private final StudentOneEventOnlyEvaluationService studentOneEventOnlyEvaluationService;

	/**
	 * Service for evaluating the long gaps constraint.
	 */
	private final StudentLongGapsEvaluationService studentLongGapsEvaluationService;

	/**
	 * Service for evaluating the online to offline connection constraint.
	 */
	private final StudentConnectionOnlineToOfflineEvaluationService
			studentConnectionOnlineToOfflineEvaluationService;

	/**
	 * Repository for accessing degree information.
	 */
	private final DegreeRepository degreeRepository;

	/**
	 * Evaluates the student's course scheduling preferences and calculates the overall score.
	 *
	 * @param timetableId The UUID of the timetable to evaluate
	 * @return StudentEvaluationScoreDTO containing degree scores and total score
	 */
	public StudentEvaluationScoreDTO evaluateStudentScore(UUID timetableId) {
		List<Degree> degrees = degreeRepository.findByTimetableId(timetableId);
		List<DegreeScoreDTO> degreeScoreDTOS = degrees.stream().map(this::evaluateDegree).toList();
		return StudentEvaluationScoreDTO.builder()
				.degreeScores(degreeScoreDTOS)
				.totalScore(calculateTotalScore(degreeScoreDTOS))
				.build();
	}

	/**
	 * Evaluates the soft constraints for a specific degree program.
	 *
	 * @param degree The degree program to evaluate.
	 * @return A DTO containing the evaluation scores for the degree program.
	 */
	private DegreeScoreDTO evaluateDegree(Degree degree) {
		List<DegreeSemester> semesters = degree.getSemesters();
		List<DegreeSemesterScoreDTO> degreeSemesterScoreDTOs =
				semesters.stream().map(this::evaluateSemester).toList();
		float totalDegreeScore =
				(float)
						degreeSemesterScoreDTOs.stream()
								.map(DegreeSemesterScoreDTO::getTotalScore)
								.mapToInt(Math::round) // Map to integer score
								.average() // Calculate average
								.orElse(0); // Return 0 if there are no elements
		return DegreeScoreDTO.builder()
				.degreeSemesterScores(degreeSemesterScoreDTOs)
				.totalDegreeScore(totalDegreeScore)
				.degreeName(degree.getName())
				.degreeRegulation(degree.getStudyRegulation())
				.build();
	}

	/**
	 * Evaluates the soft constraints for a specific semester within a degree program.
	 *
	 * @param semester The semester to evaluate.
	 * @return A DTO containing the evaluation scores for the semester.
	 */
	private DegreeSemesterScoreDTO evaluateSemester(DegreeSemester semester) {
		List<Course> courses = semester.getCourses();
		Map<Integer, MultiValueMap<DayOfWeek, WeekEvent>> weeks = new HashMap<>();

		//Initialisieren
		for (int i = 1; i <= semester.getTimetable().getNumberOfWeeks(); i++) {
			MultiValueMap<DayOfWeek, WeekEvent> week = new LinkedMultiValueMap<>();
			Arrays.stream(DayOfWeek.values()).forEach(day -> week.put(day, new ArrayList<>()));
			weeks.put(i, week);
		}

		// Populate the week with courses
		courses.stream()
				.flatMap(course -> course.getWeekEvents().stream())
				.forEach(event -> {
					MultiValueMap<DayOfWeek, WeekEvent> week = weeks.get(event.getWeek());
					List<WeekEvent> weekEvents = week.get(event.getWeekday());
					weekEvents.add(event);
					week.put(event.getWeekday(), weekEvents);
				});
		List<DegreeSemesterWeekScoreDTO> weekScores = weeks.entrySet().stream().map(entry -> evaluateWeek(entry.getValue(), entry.getKey())).toList();

		return DegreeSemesterScoreDTO.builder()
				.weeks(weekScores)
				.semesterNumber(semester.getSemesterNumber())
				.totalScore(calculateTotalWeekScore(weekScores))
				.build();
	}

	private DegreeSemesterWeekScoreDTO evaluateWeek(MultiValueMap<DayOfWeek, WeekEvent> week, int weekIdx) {
		StudentEvaluationScoreAndWeight freeDayScore =
				studentFreeDaysEvaluationService.evaluate(week);
		StudentEvaluationScoreAndWeight shortDayScore =
				studentOneEventOnlyEvaluationService.evaluate(week);
		StudentEvaluationScoreAndWeight onlineToOfflineConnectionScore =
				studentConnectionOnlineToOfflineEvaluationService.evaluate(week);
		StudentEvaluationScoreAndWeight lastTimeslotScore =
				studentLastTimeslotEvaluationService.evaluate(week);
		StudentEvaluationScoreAndWeight firstTimeslotScore =
				studentFirstTimeslotEvaluationService.evaluate(week);
		StudentEvaluationScoreAndWeight longGapsScore =
				studentLongGapsEvaluationService.evaluate(week);


		return DegreeSemesterWeekScoreDTO.builder()
				.week(weekIdx)
				.freeDayScore(freeDayScore)
				.shortDayScore(shortDayScore)
				.onlineToOfflineConnectionScore(onlineToOfflineConnectionScore)
				.lastTimeslotScore(lastTimeslotScore)
				.firstTimeslotScore(firstTimeslotScore)
				.longGapsScore(longGapsScore)
				.totalScore(
						calculateTotalScoreAndWeightForSemester(
								freeDayScore,
								shortDayScore,
								onlineToOfflineConnectionScore,
								firstTimeslotScore,
								lastTimeslotScore,
								longGapsScore))
				.build();
	}

	/**
	 * Calculates the total score and weight for a semester based on individual scores and weights.
	 *
	 * @param scoresAndWeights StudentEvaluationScoreAndWeight objects containing score and weight
	 *                         for each constraint
	 * @return Total score and weight for the semester
	 */
	private StudentEvaluationScoreAndWeight calculateTotalScoreAndWeightForSemester(
			StudentEvaluationScoreAndWeight... scoresAndWeights) {

		double weightedAverageScore =
				Arrays.stream(scoresAndWeights).mapToDouble(s -> s.getScore() * s.getWeight()).sum()
						/ TOTAL_WEIGHT;
		return StudentEvaluationScoreAndWeight.builder()
				.score((int) Math.round(weightedAverageScore))
				.weight(TOTAL_WEIGHT)
				.build();
	}

	/**
	 * Calculates the total score for a list of degree scores.
	 *
	 * @param degreeScoreDTOS The list of degree scores to calculate the total score for.
	 * @return The calculated total score.
	 */
	private float calculateTotalScore(List<DegreeScoreDTO> degreeScoreDTOS) {
		return degreeScoreDTOS.isEmpty()
				? 0
				: degreeScoreDTOS.stream()
				.map(DegreeScoreDTO::getTotalDegreeScore)
				.reduce(0f, Float::sum)
				/ degreeScoreDTOS.size();
	}

	private Integer calculateTotalWeekScore(List<DegreeSemesterWeekScoreDTO> degreeSemesterWeekScoreDTOS) {
		return degreeSemesterWeekScoreDTOS.isEmpty()
				? 0
				: degreeSemesterWeekScoreDTOS.stream()
				.map(DegreeSemesterWeekScoreDTO::getTotalScore)
				.map(StudentEvaluationScoreAndWeight::getScore)
				.reduce(0, Integer::sum)
				/ degreeSemesterWeekScoreDTOS.size();
	}
}
