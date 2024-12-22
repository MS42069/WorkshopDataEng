package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.converter.CourseConverter;
import com.fhwedel.softwareprojekt.v1.converter.ProblemConverter;
import com.fhwedel.softwareprojekt.v1.converter.WeekEventConverter;
import com.fhwedel.softwareprojekt.v1.dto.course.CourseToPlanResDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionsDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.ProblemsDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.SchedulingResultDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.WeekEventReqDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.WeekEventResDTO;
import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.repository.CourseRelationRepository;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeSemesterRepository;
import com.fhwedel.softwareprojekt.v1.repository.EmployeeRepository;
import com.fhwedel.softwareprojekt.v1.repository.RoomRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimeslotRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.WeekEventRepository;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeTimeslotConstraintRepository;
import com.fhwedel.softwareprojekt.v1.scheduler.ClassScheduler;
import com.fhwedel.softwareprojekt.v1.scheduler.Scheduler;
import com.fhwedel.softwareprojekt.v1.scheduler.conflict.CheckedEvent;
import com.fhwedel.softwareprojekt.v1.scheduler.conflict.SchedulerProblemException;
import com.fhwedel.softwareprojekt.v1.util.SizeLimitedHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

// TODO Test des Evaluators

/**
 * Service class for managing scheduling-related operations. This class encapsulates a state and
 * manages schedulers for timetables.
 */
@RequiredArgsConstructor
@Slf4j
@Component
// No service annotation, because it is strictly speaking not really a service, since it
// encapsulates a state
public class SchedulerService {

    /**
     * Maximum number of {@link ClassScheduler scheduler} instances that can be stored
     * simultaneously before the oldest instance is removed.
     */
    private static final int SCHEDULER_MAP_MAX_ENTRIES = 30;

    /**
     * Manages a scheduler for each timetable.
     */
    private final Map<UUID, ClassScheduler> timetableSchedulerMap =
            Collections.synchronizedMap(new SizeLimitedHashMap<>(SCHEDULER_MAP_MAX_ENTRIES));

    // Converter for problems to be solved by the scheduler.
    private final ProblemConverter problemConverter;

    // Repository for week events used in scheduling.
    private final WeekEventRepository weekEventRepository;

    // Service for managing course-related operations.
    private final CourseService courseService;

    // Service for managing timetable-related operations.
    private final TimetableService timetableService;

    // Service for managing room-related operations.
    private final RoomService roomService;

    // Service for managing timeslot-related operations.
    private final TimeslotService timeslotService;

    // Converter for week events.
    private final WeekEventConverter weekEventConverter;

    // Repository for timetables.
    private final TimetableRepository timetableRepository;

    /* Scheduler Dependencies */

    // Repository for courses used in scheduling.
    private final CourseRepository courseRepository;

    // Repository for rooms used in scheduling.
    private final RoomRepository roomRepository;

    // Repository for timeslots used in scheduling.
    private final TimeslotRepository timeslotRepository;

    // Repository for employees used in scheduling.
    private final EmployeeRepository employeeRepository;

    // Repository for degree semesters used in scheduling.
    private final DegreeSemesterRepository degreeSemesterRepository;

    // Repository for course relations used in scheduling.
    private final CourseRelationRepository courseRelationRepository;

    // Repository for employee timeslot constraints used in scheduling.
    private final EmployeeTimeslotConstraintRepository employeeTimeslotConstraintRepository;

    // Converter for courses used in scheduling.
    private final CourseConverter courseConverter;

    // TODO Test des Evaluators

    // Repository for degrees used in scheduling.
    private final DegreeRepository degreeRepository;

    /**
     * Finds all {@link WeekEvent week events} of the given timetable. If a non-empty optional
     * containing a {@link DayOfWeek} is given, the search is limited to events that take place on
     * the specified weekday. If a non-empty optional containing an Integer week is given, the
     * search is limited to events that take place on the specified week.
     *
     * @param timetableId       ID that identifies the relevant timetable
     * @param optionalWeek      week number that is optional to limit the search
     * @param optionalDayOfWeek day-of-week that is optional to limit the search
     * @return all {@link WeekEvent week events} with the given timetableId that take place on the
     * specified weekday or week, or on any weekday or week if not specified
     */
    public List<WeekEvent> findAllWeekEvents(
            UUID timetableId,
            Optional<Integer> optionalWeek,
            Optional<DayOfWeek> optionalDayOfWeek) {
        List<WeekEvent> result;

        if (optionalWeek.isPresent() && optionalDayOfWeek.isPresent()) {
            Integer week = optionalWeek.get();
            DayOfWeek dayOfWeek = optionalDayOfWeek.get();
            log.debug(
                    "Finding all events on week '{}' and weekday '{}' for timetable '{}'",
                    week,
                    dayOfWeek,
                    timetableId);
            result =
                    weekEventRepository.findByTimetableIdAndWeekAndWeekday(
                            timetableId, week, dayOfWeek);
        } else if (optionalWeek.isPresent()) {
            Integer week = optionalWeek.get();
            log.debug("Finding all events on week '{}' for timetable '{}'", week, timetableId);
            result = weekEventRepository.findByTimetableIdAndWeek(timetableId, week);
        } else if (optionalDayOfWeek.isPresent()) {
            DayOfWeek dayOfWeek = optionalDayOfWeek.get();
            log.debug(
                    "Finding all events on weekday '{}' for timetable '{}'",
                    dayOfWeek,
                    timetableId);
            result = weekEventRepository.findByTimetableIdAndWeekday(timetableId, dayOfWeek);
        } else {
            log.debug("Finding all events of timetable '{}'", timetableId);
            result = weekEventRepository.findByTimetableId(timetableId);
        }

        return result;
    }

    /**
     * Retrieves all {@link WeekEvent week events} for the specified timetable and converts them to
     * a list of {@link WeekEventResDTO}. The search can be optionally limited by a specific week
     * number and/or a specific day of the week.
     *
     * @param timetableId       ID that identifies the relevant timetable.
     * @param optionalWeek      Optional week number to limit the search. If not provided, events from
     *                          all weeks are considered.
     * @param optionalDayOfWeek Optional day-of-week to limit the search. If not provided, events
     *                          from all weekdays are considered.
     * @return A list of {@link WeekEventResDTO} representing the week events for the given
     * criteria.
     */
    public List<WeekEventResDTO> getAllWeekEventsAsDTO(
            UUID timetableId,
            Optional<Integer> optionalWeek,
            Optional<DayOfWeek> optionalDayOfWeek) {
        List<WeekEvent> events = findAllWeekEvents(timetableId, optionalWeek, optionalDayOfWeek);

        return weekEventConverter.convertEntitiesToResDTOList(events);
    }

    /**
     * Retrieves all {@link WeekEvent week events} associated with a specific employee based on the
     * courses the employee lectures. This method first fetches the courses taught by the employee
     * and then retrieves the week events for those courses.
     *
     * @param employeeId The unique identifier of the employee whose week events are to be fetched.
     * @return A list of {@link WeekEventResDTO week event DTOs} associated with the given employee.
     */
    public List<WeekEventResDTO> getAllWeekEventsOfEmployee(UUID employeeId) {
        log.debug("Searching for all events for employee with id {}", employeeId);
        List<WeekEvent> events = new ArrayList<>();
        List<Course> courses = courseRepository.findByLecturersId(employeeId);
        log.debug("Found {} courses for the semester", courses.size());
        if (!courses.isEmpty()) {
            events = weekEventRepository.findByCourseIn(courses);
        }
        return weekEventConverter.convertEntitiesToResDTOList(events);
    }

    /**
     * Retrieves all {@link WeekEvent week events} associated with a given degree semester. This
     * method first fetches all the courses for the specified degree semester and then retrieves all
     * week events for those courses.
     *
     * @param degreeSemesterId ID that identifies the relevant degree semester
     * @return a list of {@link WeekEventResDTO week event DTOs} associated with the courses of the
     * given degree semester. Returns an empty list if no events are found.
     */
    public List<WeekEventResDTO> getAllWeekEventsOfDegreeSemester(UUID degreeSemesterId) {
        log.debug("Searching for all events for degreeSemester with id {}", degreeSemesterId);
        List<WeekEvent> events = new ArrayList<>();
        List<Course> courses = courseRepository.findBySemestersId(degreeSemesterId);
        log.debug("Found {} courses for the semester", courses.size());
        if (!courses.isEmpty()) {
            events = weekEventRepository.findByCourseIn(courses);
        }
        return weekEventConverter.convertEntitiesToResDTOList(events);
    }

    /**
     * Retrieves all {@link WeekEvent week events} associated with a specific room.
     *
     * @param roomId The unique identifier of the room.
     * @return A list of {@link WeekEventResDTO week event DTOs} that take place in the specified
     * room.
     */
    public List<WeekEventResDTO> getAllWeekEventsOfRoom(UUID roomId) {
        log.debug("Searching for all events for room with id {}", roomId);
        List<WeekEvent> events = weekEventRepository.findByRoomsId(roomId);
        return weekEventConverter.convertEntitiesToResDTOList(events);
    }

    /**
     * Searches for a {@link WeekEvent} based on the provided ID. If the event with the given ID is
     * not found, a {@link ResponseStatusException} with a NOT_FOUND status is thrown.
     *
     * @param id The unique identifier of the {@link WeekEvent} to search for.
     * @return The {@link WeekEvent} associated with the given ID.
     * @throws ResponseStatusException If no {@link WeekEvent} with the provided ID is found.
     */
    public WeekEvent findWeekEventById(UUID id) {
        log.debug("Searching for event with id {}", id);
        return weekEventRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.warn("No event entity with id {} was found", id);
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    String.format(
                                            APIError.ENTITY_NOT_FOUND.getMessage(),
                                            WeekEvent.class,
                                            id));
                        });
    }

    /**
     * Retrieves a {@link WeekEvent} by its unique identifier and converts it to a {@link
     * WeekEventResDTO}.
     *
     * @param eventId The unique identifier of the desired {@link WeekEvent}.
     * @return A {@link WeekEventResDTO} representation of the found {@link WeekEvent}.
     * @throws EntityNotFoundException if no {@link WeekEvent} with the given eventId is found.
     */
    public WeekEventResDTO getWeekEventByIdAsDTO(UUID eventId) {
        WeekEvent event = findWeekEventById(eventId);
        return weekEventConverter.convertEntityToResDTO(event);
    }

    /**
     * Creates the scheduler for the timetable with the given ID.
     *
     * @param timetableId ID of the relevant timetable
     */
    private synchronized void initializeScheduler(UUID timetableId) {
        // check if a timetable with the given ID exists
        if (!timetableRepository.existsById(timetableId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format(
                            "Trying to initialize scheduler for a non existing timetable %s",
                            timetableId));
        }
        if (timetableSchedulerMap.containsKey(timetableId)) {
            return;
        }

        log.debug("Creating and initializing new scheduler");
        ClassScheduler scheduler =
                Scheduler.initialize(
                        timetableId,
                        courseRepository,
                        roomRepository,
                        timeslotRepository,
                        weekEventRepository,
                        employeeRepository,
                        degreeSemesterRepository,
                        courseRelationRepository,
                        timetableRepository,
                        employeeTimeslotConstraintRepository);
        timetableSchedulerMap.put(timetableId, scheduler);
    }

    /**
     * Initializes the scheduler for the given timetable ID if it's not already present.
     *
     * @param timetableId The ID of the timetable to check and potentially initialize.
     */
    public void initializeIfNotPresent(UUID timetableId) {
        if (!timetableSchedulerMap.containsKey(timetableId)) {
            initializeScheduler(timetableId);
        }
    }

    /**
     * Retrieves the {@link ClassScheduler} associated with the given timetable ID. If no scheduler
     * exists for the specified timetable, it initializes one.
     *
     * @param timetableId ID that identifies the relevant timetable
     * @return the {@link ClassScheduler} associated with the given timetableId
     */
    private ClassScheduler getSchedulerIfAbsentInitialize(UUID timetableId) {
        // if scheduler for given timetable does not exist, initialize
        initializeIfNotPresent(timetableId);
        return timetableSchedulerMap.get(timetableId);
    }

    /**
     * Removes the scheduler instance of the timetable with the given ID. It is reasonable, when the
     * timetable is deleted, to remove the scheduler as well to free up resources.
     *
     * @param timetableId ID of the relevant timetable
     */
    public void removeScheduler(UUID timetableId) {
        timetableSchedulerMap.remove(timetableId);
    }

    /**
     * Schedules the week event specified in the DTO. Creates and saves a corresponding Week Event
     * Entities, provided that the event does not lead to conflicts (constraint violations).
     *
     * @param timetableId ID of the timetable in which the event is to be scheduled
     * @param eventReqDTO the DTO specifying the event to be scheduled
     * @param force       whether to schedule an event even though a conflict has been detected
     * @return result of the scheduling, contains if there is no conflict the scheduled event with
     * its ID.
     * @throws SchedulerProblemException if the event is in conflict with other events or violates
     *                                   defined constraints
     */
    public SchedulingResultDTO scheduleEvent(
            UUID timetableId, WeekEventReqDTO eventReqDTO, boolean force) {
        log.debug("Scheduling new event {}, force={}", eventReqDTO, force);
        WeekEvent[] events = createEventsFromReqDTO(timetableId, eventReqDTO);

        ClassScheduler scheduler = getSchedulerIfAbsentInitialize(timetableId);
        Timetable timetable = this.timetableRepository.findById(timetableId)
                .orElse(null);
        int noOfWeeks = timetable != null ? timetable.getNumberOfWeeks() : 1;
        int noOfScheduledEvents = events.length;

        for (WeekEvent event : events) {
            if (event.getWeek() == null) {
                if (timetable == null) {
                    return null;
                }
                noOfScheduledEvents += (noOfWeeks - 1);
            }
        }

        WeekEventResDTO[] scheduledEvents = new WeekEventResDTO[noOfScheduledEvents];
        int scheduledEventIdx = 0;

        for (WeekEvent event : events) {
            if (event.getWeek() == null) {
                if (timetable == null) {
                    return null;
                }
                for (int i = 0; i < noOfWeeks; i++) {
                    event.setWeek(i + 1);
                    scheduledEventIdx =
                            scheduleSingleEvent(
                                    scheduler, event, force, scheduledEvents, scheduledEventIdx);
                }
            } else {
                scheduledEventIdx =
                        scheduleSingleEvent(
                                scheduler, event, force, scheduledEvents, scheduledEventIdx);
            }
        }

        return new SchedulingResultDTO(scheduledEvents);
    }

    private int scheduleSingleEvent(
            ClassScheduler scheduler,
            WeekEvent event,
            boolean force,
            WeekEventResDTO[] scheduledEvents,
            int idx) {
        CheckedEvent checkedEvent = scheduler.checkAdmissibility(event);

        // if no scheduling conflicts (constraint violations) have occurred
        if (!checkedEvent.isCausingConflict() || force) {
            // persist valid event
            log.info("Saving new week event {}", event);
            log.info(
                    "before c={} degrees={}",
                    event.getCourse()
                            .getId(),
                    scheduler.getDegreesOfFreedom(event.getCourse()));
            event = weekEventRepository.save(event);
            boolean scheduleResult = scheduler.scheduleEvent(event, force);
            log.info(
                    "after result={}  c={} degrees={}",
                    scheduleResult,
                    event.getCourse()
                            .getId(),
                    scheduler.getDegreesOfFreedom(event.getCourse()));
        } else {
            log.warn(
                    "Event could not be scheduled because encountered conflicts {}",
                    checkedEvent.getConflicts());
            ProblemsDTO problemsDTO =
                    problemConverter.convertConflictsToProblemsDTO(
                            checkedEvent.getEvent(), checkedEvent.getConflicts());
            throw new SchedulerProblemException(problemsDTO);
        }

        // convert result into DTO
        scheduledEvents[idx] = weekEventConverter.convertEntityToResDTO(event);
        return idx + 1;
    }

    /**
     * Deletes the specified {@link WeekEvent week event} associated with the given timetable.
     * Before deletion, the event is unscheduled using the associated scheduler.
     *
     * @param timetableId ID that identifies the relevant timetable.
     * @param eventId     ID of the {@link WeekEvent week event} to be deleted.
     * @throws EntityNotFoundException if the specified event or timetable is not found.
     */
    public void deleteEvent(UUID timetableId, UUID eventId) {
        WeekEvent event = findWeekEventById(eventId);

        ClassScheduler scheduler = getSchedulerIfAbsentInitialize(timetableId);
        log.info("Unscheduled and deleting event {}", event);
        scheduler.unscheduleEvent(event);
        weekEventRepository.delete(event);
    }

    /**
     * Creates a new {@link WeekEvent} from the provided request DTO. This method fetches the
     * necessary entities like {@link Timetable}, {@link Course}, {@link Timeslot}, and {@link Room}
     * based on the information provided in the request DTO. Once all the necessary entities are
     * fetched, a new {@link WeekEvent} is created and populated with the fetched data.
     *
     * @param timetableId ID of the timetable for which the event is to be created.
     * @param eventReqDTO The request DTO containing the necessary information to create the event.
     * @return A newly created {@link WeekEvent} populated with the data from the request DTO.
     */
    private WeekEvent[] createEventsFromReqDTO(UUID timetableId, WeekEventReqDTO eventReqDTO) {
        log.debug("Creating new event for timetable '{}' from '{}'", timetableId, eventReqDTO);
        Timetable timetable = timetableService.findByID(timetableId);
        Course course = courseService.findCourseByID(eventReqDTO.getCourseId());
        Integer week = eventReqDTO.getWeek();
        DayOfWeek weekday = eventReqDTO.getWeekday();

        // find all referenced timeslots and collect them into an ArrayList
        List<Timeslot> timeslots =
                eventReqDTO.getBlockOfTimeslots()
                        .stream()
                        .map(timeslotService::findByID)
                        .collect(Collectors.toCollection(ArrayList::new));
        // find all references rooms and collect them into an ArrayList
        List<Room> rooms =
                eventReqDTO.getTakesPlaceInRooms()
                        .stream()
                        .map(roomService::findByID)
                        .collect(Collectors.toCollection(ArrayList::new));

        WeekEvent[] events = new WeekEvent[week == null ? timetable.getNumberOfWeeks() : 1];

        for (int w = 0; w < events.length; w++) {
            WeekEvent event = new WeekEvent();
            event.setTimetable(timetable);
            event.setCourse(course);
            event.setWeek(week == null ? w + 1 : week);
            event.setWeekday(weekday);
            event.setRooms(rooms);
            event.setTimeslots(timeslots);

            events[w] = event;
        }

        return events;
    }

    /**
     * Searches for all possible week dates for each specified course (by their IDs) on which the
     * course can be scheduled as a {@link WeekEvent}. The search can be limited to specific weeks
     * and weekdays if provided.
     *
     * @param timetableId      ID that identifies the relevant timetable.
     * @param courseIds        A set of IDs representing the courses for which event date options are to be
     *                         determined.
     * @param optSetOfWeeks    An optional set of weeks to restrict the search. If not provided, all
     *                         weeks are considered.
     * @param optSetOfWeekdays An optional set of weekdays to restrict the search. If not provided,
     *                         all weekdays (Mon-Sun) are considered.
     * @return A list of {@link OptionsDTO} objects, each containing possible scheduling options for
     * a specific course.
     */
    public List<OptionsDTO> findEventOptions(
            UUID timetableId,
            Set<UUID> courseIds,
            Optional<Set<Integer>> optSetOfWeeks,
            Optional<Set<DayOfWeek>> optSetOfWeekdays) {
        List<OptionsDTO> result = new ArrayList<>();

        List<Course> courses = fetchCoursesById(courseIds);

        Set<Integer> setOfWeeks = optSetOfWeeks.orElse(Collections.emptySet());
        Set<DayOfWeek> setOfWeekdays = optSetOfWeekdays.orElse(Collections.emptySet());
        ClassScheduler scheduler = getSchedulerIfAbsentInitialize(timetableId);

        for (Course course : courses) {
            OptionsDTO optionsDTO = scheduler.findAllOptionsFor(course);

            // Filter options by setOfWeekdays
            if (!setOfWeekdays.isEmpty()) {
                log.debug(
                        "Filter out options that are not included in the set of weekdays {}",
                        setOfWeekdays);
                List<OptionDTO> filteredOptionsByWeekdays =
                        filterOptionByWeekdays(optionsDTO.getOptions(), setOfWeekdays);
                optionsDTO.setOptions(filteredOptionsByWeekdays);
            }

            // Filter options by setOfWeeks
            if (!setOfWeeks.isEmpty()) {
                log.debug(
                        "Filter out options that are not included in the set of weeks {}",
                        setOfWeeks);
                List<OptionDTO> filteredOptionsByWeeks =
                        filterOptionByWeeks(optionsDTO.getOptions(), setOfWeeks);
                optionsDTO.setOptions(filteredOptionsByWeeks);
            }

            log.info("Found {} options {}", optionsDTO.getOptions()
                    .size(), optionsDTO);
            result.add(optionsDTO);
        }

        return result;
    }

    /**
     * Filters a list of {@link OptionDTO} objects based on a set of specified weeks.
     *
     * @param options    The list of {@link OptionDTO} objects to be filtered.
     * @param setOfWeeks A set of weeks used to filter the options.
     * @return A filtered list of {@link OptionDTO} objects that match the specified set of weeks.
     */
    private List<OptionDTO> filterOptionByWeeks(List<OptionDTO> options, Set<Integer> setOfWeeks) {
        return options.stream()
                .filter(option -> setOfWeeks.contains(option.getWeek()))
                .collect(Collectors.toList());
    }

    /**
     * Filters a collection of {@link OptionDTO} based on a set of specified weekdays.
     *
     * @param optionList    The collection of {@link OptionDTO} to be filtered.
     * @param setOfWeekdays The set of {@link DayOfWeek} used for filtering the options.
     * @return A list of {@link OptionDTO} that match the specified weekdays.
     */
    public List<OptionDTO> filterOptionByWeekdays(
            Collection<OptionDTO> optionList, Set<DayOfWeek> setOfWeekdays) {
        return optionList.stream()
                .filter(option -> setOfWeekdays.contains(option.getWeekday()))
                .toList();
    }

    /**
     * regenerate for the given scheduler all options
     *
     * @param timetableID id of timetable
     */
    public synchronized void regenerateScheduler(UUID timetableID) {
        ClassScheduler scheduler = getSchedulerIfAbsentInitialize(timetableID);
        scheduler.regenerate();
    }

    /**
     * Fetches a list of {@link Course courses} based on the provided collection of course IDs.
     *
     * @param courseIds A collection of course IDs to fetch the corresponding courses.
     * @return A list of {@link Course courses} corresponding to the provided course IDs.
     */
    private List<Course> fetchCoursesById(Collection<UUID> courseIds) {
        List<Course> result = new ArrayList<>();
        for (UUID courseId : courseIds) {
            result.add(courseService.findCourseByID(courseId));
        }
        return result;
    }

    /**
     * Retrieves a list of courses that are yet to be planned for a given timetable. The search can
     * be further narrowed down by specifying an employee ID and/or a semester ID. The method
     * calculates the number of slots that are still available for each course and determines the
     * degree of freedom for scheduling each course.
     *
     * @param timetableId   ID that identifies the relevant timetable.
     * @param optEmployeeId Optional employee ID to filter courses by a specific lecturer.
     * @param optSemesterId Optional semester ID to filter courses by a specific semester.
     * @return List of {@link CourseToPlanResDTO} objects representing courses that still need
     * planning. Each object contains details of the course, the number of times it still needs
     * to be planned, and its degree of freedom for scheduling.
     */
    public List<CourseToPlanResDTO> findAllCoursesStillToPlan(
            UUID timetableId, Optional<UUID> optEmployeeId, Optional<UUID> optSemesterId) {
        log.info("Finding all courses that still have to be planned");
        List<CourseToPlanResDTO> coursesToPlan = new ArrayList<>();
        CourseToPlanResDTO resDTO;
        Streamable<Course> courseStream = courseRepository.streamByTimetableId(timetableId);
        if (optEmployeeId.isPresent()) {
            UUID employeeId = optEmployeeId.get();
            log.debug("filter courses by employeeId {}", employeeId);
            courseStream =
                    courseStream.filter(
                            course ->
                                    course.getLecturers()
                                            .stream()
                                            .anyMatch(
                                                    lecturer ->
                                                            lecturer.getId()
                                                                    .equals(employeeId)));
        }
        if (optSemesterId.isPresent()) {
            UUID semesterId = optSemesterId.get();
            log.debug("filter courses by semesterId {}", optSemesterId);
            courseStream =
                    courseStream.filter(
                            course ->
                                    course.getSemesters()
                                            .stream()
                                            .anyMatch(
                                                    semester ->
                                                            semester.getId()
                                                                    .equals(semesterId)));
        }
        List<Course> courses = courseStream.toList();

        ClassScheduler scheduler = getSchedulerIfAbsentInitialize(timetableId);
        for (Course course : courses) {
            int stillToPlan = course.getSlotsPerWeek() * course.getWeeksPerSemester();
            log.debug("Getting how much Course with id {} has to be planned", course.getId());
            for (WeekEvent event : weekEventRepository.findByCourse(course)) {
                stillToPlan = stillToPlan - event.getTimeslots()
                        .size();
            }
            log.debug("The course has to be still planned {} times", stillToPlan);
            if (stillToPlan > 0) {
                int degreeOfFreedom = scheduler.getDegreesOfFreedom(course);

                log.debug("The course has a degree of freedom of {}", degreeOfFreedom);
                resDTO =
                        courseConverter.convertEntityToCourseToPlanResDTO(
                                course, stillToPlan, degreeOfFreedom);
                coursesToPlan.add(resDTO);
                log.debug(
                        "Added course with ID '{}' that has to be still planned '{}' times,"
                                + " and has the degree of freedom of '{}' to the result list",
                        course.getId(),
                        stillToPlan,
                        degreeOfFreedom);
            }
        }
        return coursesToPlan;
    }
}
