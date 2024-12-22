package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionsDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.CourseRelation;
import com.fhwedel.softwareprojekt.v1.model.CourseTimeslot;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.IdentifiableEntity;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.RoomCombination;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.Timetable;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.model.WorkTime;
import com.fhwedel.softwareprojekt.v1.model.constraints.EmployeeTimeslotConstraint;
import com.fhwedel.softwareprojekt.v1.repository.CourseRelationRepository;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import com.fhwedel.softwareprojekt.v1.repository.DegreeSemesterRepository;
import com.fhwedel.softwareprojekt.v1.repository.EmployeeRepository;
import com.fhwedel.softwareprojekt.v1.repository.RoomRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimeslotRepository;
import com.fhwedel.softwareprojekt.v1.repository.TimetableRepository;
import com.fhwedel.softwareprojekt.v1.repository.WeekEventRepository;
import com.fhwedel.softwareprojekt.v1.repository.constraints.EmployeeTimeslotConstraintRepository;
import com.fhwedel.softwareprojekt.v1.scheduler.conflict.CheckedEvent;
import com.fhwedel.softwareprojekt.v1.scheduler.conflict.Conflict;
import com.fhwedel.softwareprojekt.v1.util.RelationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * The Scheduler class is responsible for managing and scheduling classes, courses, rooms, and other
 * resources within a timetable system.
 *
 * <p>It implements the ClassScheduler interface, providing methods for scheduling and managing
 * various aspects of the timetable system.
 *
 * <p>This class uses scheduling algorithms and constraints to optimize the allocation of resources
 * such as rooms, timeslots, and employees to courses and classes.
 */
@RequiredArgsConstructor
@Slf4j
public class Scheduler implements ClassScheduler {

    /**
     * employeeScheduleLimitations stores for the given Employee all the already scheduled events
     * and non-working hours (timeslots in which the employee does not wish too work)
     */
    private final Map<UUID, Curriculum<Employee>> employeeScheduleLimitations =
            new ConcurrentHashMap<>();

    /**
     * degreeSemesterScheduleLimitations ScheduleLookUp stores for the given degree semester all the
     * already scheduled events
     */
    private final Map<UUID, Curriculum<DegreeSemester>> degreeSemesterScheduleLimitations =
            new ConcurrentHashMap<>();

    /**
     * roomScheduleLimitations stores for the given rooms all the already scheduled events and
     * blocking events
     */
    private final Map<UUID, Curriculum<Room>> roomScheduleLimitations = new ConcurrentHashMap<>();

    /**
     * roomScheduleLimitations stores for the given rooms all the already scheduled events and
     * blocking events
     */
    private final Map<UUID, Curriculum<Course>> courseScheduleLimitations =
            new ConcurrentHashMap<>();

    /**
     * courseOptions stores for the given course all the possible options
     */
    private final Map<UUID, Options> courseOptions = new ConcurrentHashMap<>();

    /**
     * roomClaims stores for the given room the associated set of claims
     */
    private final Map<UUID, RoomClaims> roomClaims = new ConcurrentHashMap<>();

    /**
     * The UUID identifying the timetable associated with this Scheduler.
     */
    @NotNull
    private final UUID timetableID;

    /**
     * Repository for managing Course entities in the timetable system.
     */
    @NotNull
    private final CourseRepository courseRepository;

    /**
     * Repository for managing Room entities in the timetable system.
     */
    @NotNull
    private final RoomRepository roomRepository;

    /**
     * Repository for managing Timeslot entities in the timetable system.
     */
    @NotNull
    private final TimeslotRepository timeslotRepository;

    /**
     * Repository for managing WeekEvent entities in the timetable system.
     */
    @NotNull
    private final WeekEventRepository weekEventRepository;

    /**
     * Repository for managing Employee entities in the timetable system.
     */
    @NotNull
    private final EmployeeRepository employeeRepository;

    /**
     * Repository for managing DegreeSemester entities in the timetable system.
     */
    @NotNull
    private final DegreeSemesterRepository degreeSemesterRepository;

    /**
     * Repository for managing CourseRelation entities in the timetable system.
     */
    @NotNull
    private final CourseRelationRepository courseRelationRepository;

    @NotNull
    private final TimetableRepository timetableRepository;

    private final EmployeeTimeslotConstraintRepository employeeTimeslotConstraintRepository;

    {
        // Initialize converter
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        //        this.converter = new Converter(modelMapper);
    }

    /**
     * Static-Factory method that creates a new instance of the Scheduler and initiates the
     * initialization, which loads the relevant data from the database and constructs the internal
     * state of the Scheduler.
     *
     * @param timetableID         ID of the timetable for which a scheduler is to be created.
     * @param courseRepository    course repository
     * @param roomRepository      room repository
     * @param timeslotRepository  timeslot repository
     * @param weekEventRepository week event repository
     * @return initialized scheduler instance which is based on persisted data of the given
     * timetable
     */
    public static Scheduler initialize(
            UUID timetableID,
            CourseRepository courseRepository,
            RoomRepository roomRepository,
            TimeslotRepository timeslotRepository,
            WeekEventRepository weekEventRepository,
            EmployeeRepository employeeRepository,
            DegreeSemesterRepository degreeSemesterRepository,
            CourseRelationRepository courseRelationRepository,
            TimetableRepository timetableRepository,
            EmployeeTimeslotConstraintRepository employeeTimeslotConstraintRepository) {
        Scheduler newInstance =
                new Scheduler(
                        timetableID,
                        courseRepository,
                        roomRepository,
                        timeslotRepository,
                        weekEventRepository,
                        employeeRepository,
                        degreeSemesterRepository,
                        courseRelationRepository,
                        timetableRepository,
                        employeeTimeslotConstraintRepository);

        newInstance.initialize();
        return newInstance;
    }

    /**
     * Initializes the scheduler with rooms, courses, and events from the database for the current
     * timetable.
     *
     * @return {@code true} if initialization is successful, {@code false} otherwise.
     */
    synchronized boolean initialize() {
        List<Room> rooms = roomRepository.findByTimetableId(timetableID);
        //                converter.roomsTos(roomRepository.findByTimetableId(timetableID));

        for (Room r : rooms) {
            this.addRoom(r);
        }

        // Convert Database Entry for every course to use only
        // non relational  properties
        List<Course> courses = courseRepository.findByTimetableId(timetableID);
        //                converter.coursesTos(courseRepository.findByTimetableId(timetableID));

        // For a given course UUID get all course uuid which are related before
        Map<UUID, Set<UUID>> courseRelationBefore = new HashMap<>();

        // For a given course UUID get all course uuid which are related after
        Map<UUID, Set<UUID>> courseRelationAfter = new HashMap<>();

        Set<UUID> temp = new HashSet<>();
        for (Course c : courses) {
            temp.clear();
            this.addCourse(c);

            temp =
                    c.getCourseRelationsA()
                            .stream()
                            .filter(cr -> cr.getRelationType() == RelationType.A_MUST_BE_BEFORE_B)
                            .map(courseRelation -> courseRelation.getCourseB()
                                    .getId())
                            .collect(Collectors.toSet());
            Set<UUID> coursesWhichAreAfter =
                    courseRelationAfter.getOrDefault(c.getId(), new HashSet<>());
            coursesWhichAreAfter.addAll(temp);
            courseRelationAfter.put(c.getId(), coursesWhichAreAfter);

            for (UUID u : temp) {
                Set<UUID> coursesWhichArebefore =
                        courseRelationBefore.getOrDefault(u, new HashSet<>());
                coursesWhichArebefore.add(c.getId());
                courseRelationBefore.put(u, coursesWhichArebefore);
            }
        }

        /*
        Load the Before/After Relation for every Course
         */
        for (Course c : courses) {
            Options opt = this.courseOptions.get(c.getId());
            opt.coursesBefore.addAll(courseRelationBefore.getOrDefault(c.getId(), new HashSet<>()));
            opt.coursesAfter.addAll(courseRelationAfter.getOrDefault(c.getId(), new HashSet<>()));
        }

        List<WeekEvent> events = weekEventRepository.findByTimetableId(timetableID);
        log.info(
                "Initializing scheduler: queried all already planned events {}",
                events.stream()
                        .map(Schedule::logRepresentation)
                        .toList());
        for (WeekEvent event : events) {
            if (event.getWeek() == null) {
                Timetable timetable =
                        this.timetableRepository.findById(this.timetableID)
                                .isPresent()
                                ? this.timetableRepository.findById(this.timetableID)
                                .get()
                                : null;
                if (timetable == null) {
                    return false;
                }
                for (int i = 1; i < timetable.getNumberOfWeeks(); i++) {
                    event.setWeek(i);
                    this.scheduleEvent(event, true);
                }
            } else {
                this.scheduleEvent(event, true);
            }
        }

        return true;
    }

    /**
     * Schedule the course and updates all intersecting entities associated by: - Employee -
     * degreesSemester - rooms if the event causes a SchedulerException the new event is not being
     * applied to the schedule
     *
     * @param event the to be scheduled event
     * @param force apply changed even though a conflict has been detected
     * @return true if the event effected other courses
     */
    public synchronized boolean scheduleEvent(WeekEvent event, boolean force) {
        assert event.getId() != null;
        WeekEvent newEventRecord = new WeekEvent();
        newEventRecord.setId(event.getId());
        newEventRecord.setWeek(event.getWeek());
        newEventRecord.setWeekday(event.getWeekday());
        newEventRecord.setTimeslots(
                weekEventRepository.getWeekEventGraphTimeslotById(event.getId())
                        .getTimeslots());
        newEventRecord.setRooms(
                weekEventRepository.getWeekEventGraphRoomsById(event.getId())
                        .getRooms());
        newEventRecord.setCourse(
                weekEventRepository.getWeekEventGraphCourseById(event.getId())
                        .getCourse());

        log.info(
                "schedule event={} for course={} , rooms={} and timeslots={}",
                Schedule.logRepresentation(newEventRecord),
                newEventRecord.getCourse(),
                newEventRecord.getRooms(),
                newEventRecord.getTimeslots());

        //        WeekEvent weekEvent = converter.to(this, e);
        Course course = getCourse(newEventRecord.getCourse()
                .getId());

        log.info(
                "course options  course={} lecturers={} semesters={}",
                course.getId(),
                course.getLecturers(),
                course.getSemesters());

        if (this.getOptionsForCourse(course)
                .isEmpty()) {
            this.addCourse(course);
        }
        Options opts = this.getOptionsForCourse(course)
                .get();
        return opts.schedule(this, newEventRecord, this.courseRepository);
    }

    /**
     * Unschedules the course and updates all intersecting entities associated by: - Employee -
     * degreesSemester - rooms if the event causes a SchedulerException the new event is not being
     * applied to the schedule
     *
     * @param event the to be scheduled event
     * @return true if the event effected other courses
     */
    public synchronized boolean unscheduleEvent(WeekEvent event) {
        WeekEvent newEventRecord = new WeekEvent();
        newEventRecord.setId(event.getId());
        newEventRecord.setWeek(event.getWeek());
        newEventRecord.setWeekday(event.getWeekday());
        newEventRecord.setTimeslots(
                weekEventRepository.getWeekEventGraphTimeslotById(event.getId())
                        .getTimeslots());
        newEventRecord.setRooms(
                weekEventRepository.getWeekEventGraphRoomsById(event.getId())
                        .getRooms());
        newEventRecord.setCourse(
                weekEventRepository.getWeekEventGraphCourseById(event.getId())
                        .getCourse());

        //        WeekEvent weekEvent = converter.to(this, e);
        log.info("unscheduling event {}", Schedule.logRepresentation(newEventRecord));

        Course course = event.getCourse();

        if (this.getOptionsForCourse(course)
                .isEmpty()) {
            this.addCourse(course);
        }
        Options opts = this.getOptionsForCourse(course)
                .get();
        return opts.unschedule(this, newEventRecord, courseRepository);
    }

    /**
     * Finds and retrieves all scheduling options for a given course, based on the available
     * timeslots.
     *
     * @param course The course for which to find scheduling options.
     * @return An {@link OptionsDTO} object containing the scheduling options for the course.
     * @throws RuntimeException if scheduling options for the course are not initialized.
     */
    @Override
    public synchronized OptionsDTO findAllOptionsFor(Course course) {
        OptionsDTO result = new OptionsDTO();

        result.setCourse(course.getId());

        List<Timeslot> allTimeslots = timeslotRepository.findByTimetableId(timetableID);
        Map<LocalTime, Timeslot> tsStartTimeLookupMap =
                Interval.mapTimeslotsToStartTime(allTimeslots);

        Options options =
                getOptionsForCourse(course)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                String.format(
                                                        "Options for course %s not initialized",
                                                        course.getName())));

        return options.listOptions(allTimeslots);
    }

    /**
     * Defines a comparator that sorts timeslots in ascending order by their start time.
     *
     * @return A comparator for sorting timeslots by start time in ascending order.
     */
    private Comparator<Timeslot> sortTimeslotsAscByStartTime() {
        // Defines an order that sorts timeslots in ascending order by start time
        return Comparator.comparing(Timeslot::getStartTime);
    }

    /**
     * Checks the admissibility of scheduling a week event and identifies any conflicts with
     * constraints such as course requirements, room availability, employee availability, and degree
     * semester conflicts.
     *
     * @param event The week event to be checked for admissibility.
     * @return A {@link CheckedEvent} object containing information about conflicts, if any.
     */
    @Transactional
    public synchronized CheckedEvent checkAdmissibility(@NotNull WeekEvent event) {
        log.info("checking for admissibility={}", Schedule.logRepresentation(event));
        CheckedEvent result = new CheckedEvent(event);
        Course c = event.getCourse();

        // Check if given amount of timeslots is matching the block size defined for the course
        if (event.getTimeslots()
                .size() != event.getCourse()
                .getBlockSize()) {
            log.info(
                    "timeslots does not match amount of required block size for event={}",
                    Schedule.logRepresentation(event));
            result.addConflict(new Conflict(Constraint.BLOCK_SIZE));
        }

        Options opts =
                this.getOptionsForCourse(c)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                String.format(
                                                        "Scheduler for course %s not initialized",
                                                        c.getName())));

        Course course = this.getCourse(event.getCourse()
                .getId());

        // Check if amount course allows further events
        if (!opts.getAlreadyScheduled()
                .contains(event)
                && opts.getAlreadyScheduled()
                .size()
                >= c.getSlotsPerWeek() * c.getWeeksPerSemester()) {
            log.info("events already all planned={}", Schedule.logRepresentation(event));
            result.addConflict(new Conflict(Constraint.SLOTS_PER_WEEK));
        }

        // check admissibility of course timeslots
        Conflict conflictCourseTimeslots = checkCourseTimeslots(event);
        if (conflictCourseTimeslots != null) {
            result.addConflict(conflictCourseTimeslots);
        }

        Optional<Conflict> conflictEmployeeTimeslotConstraint = checkEmployeeTimeslotConstraint(event, course.getLecturers());
        conflictEmployeeTimeslotConstraint.ifPresent(result::addConflict);

        // Check if amount course allows the provided room combination
        if (!course.getSuitedRooms()
                .isEmpty()
                && course.getSuitedRooms()
                .stream()
                .noneMatch(
                        roomCombination ->
                                roomCombination.getRooms()
                                        .size() == event.getRooms()
                                        .size()
                                        && new HashSet<>(roomCombination.getRooms())
                                        .containsAll(event.getRooms()))) {
            log.info("Invalid room combination");
            result.addConflict(new Conflict(Constraint.ROOM_COMBINATION));
        }

        /*
           For a given interval
           - check if the weekevent has a corresponding timeslots as options
               - n -> cant be scheduled to contraints by employees curriculums
               - t -> check if the time slots are blocked
                   - n -> blocked timeslots

        */

        // If for a weekevent has a corresponding option
        // check if the option isBlocked.
        // if check if the origin is based on the relations of the courses
        Interval interval = Interval.from(event.getTimeslots());
        NavigableSet<Option> options =
                opts.getIntersectingIntervals(event.getWeek(), event.getWeekday(), interval);

        List<Option> filteredOpts =
                options.stream()
                        .filter(
                                i ->
                                        i.getStart()
                                                .get()
                                                .getHour()
                                                == interval.getStart()
                                                .get()
                                                .getHour())
                        .filter(
                                i ->
                                        i.getStart()
                                                .get()
                                                .getMinute()
                                                == interval.getStart()
                                                .get()
                                                .getMinute())
                        .filter(
                                i ->
                                        i.getEnd()
                                                .get()
                                                .getHour()
                                                == interval.getEnd()
                                                .get()
                                                .getHour())
                        .filter(
                                i ->
                                        i.getEnd()
                                                .get()
                                                .getMinute()
                                                == interval.getEnd()
                                                .get()
                                                .getMinute())
                        .toList();

        if (filteredOpts.size() == 1) {
            Option opt = filteredOpts.get(0);
            Set<WeekEvent> contrainedByWeekEvents = opt.getConstrainingEvents();

            if (!contrainedByWeekEvents.isEmpty()) {
                Conflict conflict = new Conflict(Constraint.COURSE_RELATION);
                conflict.getWeekEvents()
                        .addAll(contrainedByWeekEvents);
                result.addConflict(conflict);
            }
        }

        List<Employee> causesConflictWithEmployees = new LinkedList<>();
        for (Employee employee : course.getLecturers()) {
            if (getScheduleForEmployee(employee).isEmpty()) {
                addEmployee(employee);
            }
            Curriculum<Employee> curriculum = this.getScheduleForEmployee(employee)
                    .get();

            if (!curriculum.isTimeslotAdmissable(event, this)) {
                log.debug("Causes conflict with employee {}", employee);
                causesConflictWithEmployees.add(employee);
            }
        }

        if (!causesConflictWithEmployees.isEmpty()) {
            Conflict conflict = new Conflict(Constraint.EMPLOYEE_AVAILABILITY);
            conflict.getEmployees()
                    .addAll(causesConflictWithEmployees);
            result.addConflict(conflict);
        }

        List<DegreeSemester> causesConflictWithDegreeSemester = new LinkedList<>();
        for (DegreeSemester degreeSemester : course.getSemesters()) {
            if (getScheduleForDegreeSems(degreeSemester).isEmpty()) {
                addDegreeSemester(degreeSemester);
            }

            Curriculum<DegreeSemester> curriculum =
                    this.getScheduleForDegreeSems(degreeSemester)
                            .get();

            if (!curriculum.isTimeslotAdmissable(event, this)) { // TODO
                log.debug("Causes conflict with DegreeSemester {}", degreeSemester);
                causesConflictWithDegreeSemester.add(degreeSemester);
            }
        }

        if (!causesConflictWithDegreeSemester.isEmpty()) {
            Conflict conflict = new Conflict(Constraint.DEGREE_SEMESTER_CONFLICT);
            conflict.getDegreeSemesters()
                    .addAll(causesConflictWithDegreeSemester);
            result.addConflict(conflict);
        }

        List<Room> causesConflictWithRooms = new LinkedList<>();
        for (Room r : event.getRooms()) {
            if (getScheduleForRoom(r).isEmpty()) {
                addRoom(r);
            }
            Curriculum<Room> curriculum = this.getScheduleForRoom(r)
                    .get();
            if (!curriculum.isTimeslotAdmissable(event, this)) {
                log.debug("Causes conflict with room {}", r);
                causesConflictWithRooms.add(r);
            }
        }

        if (!causesConflictWithRooms.isEmpty()) {
            Conflict conflict = new Conflict(Constraint.ROOM_AVAILABILITY);
            conflict.getRooms()
                    .addAll(causesConflictWithRooms);
            result.addConflict(conflict);
        }

        log.info(
                "admissibility result for event{}\n  {}",
                Schedule.logRepresentation(event),
                result);

        return result;
    }

    /**
     * Checks if the timeslots of the event to be scheduled are permitted as potential course
     * timeslots.
     *
     * @param event the event to be scheduled
     * @return a conflict if the course timeslot constraint is violated, otherwise null.
     */
    private Conflict checkCourseTimeslots(WeekEvent event) {
        Conflict result = null;
        Course course = event.getCourse();
        if (this.getScheduleLimitationsForCourse(course)
                .isEmpty()) {
            generateCourseLimitations(course);
        }
        Curriculum<Course> curriculum = this.getScheduleLimitationsForCourse(course)
                .get();

        // Check if all timeslots are available and do not overlap with restricted timeslots
        if (!curriculum.areTimeslotsAvailable(
                event.getWeek(), event.getWeekday(), event.getTimeslots())) {
            log.debug(
                    "Timeslots to be scheduled not permitted as course timeslots for this course");
            result = new Conflict(Constraint.COURSE_TIMESLOT);
        }
        return result;
    }

    private Optional<Conflict> checkEmployeeTimeslotConstraint(WeekEvent event, List<Employee> employees) {
        List<Timeslot> eventTimeslots = event.getTimeslots();
        DayOfWeek weekDay = event.getWeekday();


        List<EmployeeTimeslotConstraint> employeeHardConstraints =
                employees.stream()
                        .flatMap(employee -> employeeTimeslotConstraintRepository.findHardConstraintsNotWantedForEmployee(employee.getAbbreviation())
                                .stream())
                        .filter(constraint -> eventTimeslots.stream()
                                .anyMatch(timeslot -> timeslot.getIndex()
                                        .equals(constraint.getTimeslotIndex()))
                                && weekDay.equals(constraint.getWeekday()))
                        .toList();
        if (employeeHardConstraints.isEmpty()) {
            return Optional.empty();
        }
        List<Employee> affectedEmployees = employees.stream()
                .filter(employee -> employeeHardConstraints.stream()
                        .anyMatch(constraint -> constraint.getEmployeeAbbreviation()
                                .equals(employee.getAbbreviation())))
                .toList();
        Conflict conflict = new Conflict(Constraint.EMPLOYEE_TIMESLOT_WISH);
        conflict.getEmployees()
                .addAll(affectedEmployees);
        conflict.getEmployeeTimeslotConstraint()
                .addAll(employeeHardConstraints);
        return Optional.of(conflict);
    }


    /**
     * Retrieves the schedule limitations for a specific course.
     *
     * @param course The course for which to retrieve schedule limitations.
     * @return An {@link Optional} containing the schedule limitations for the course if available,
     * or an empty {@link Optional} if no limitations are set.
     */
    private Optional<Curriculum<Course>> getScheduleLimitationsForCourse(
            @NotNull IdentifiableEntity course) {
        return Optional.ofNullable(this.courseScheduleLimitations.get(course.getId()));
    }

    /**
     * Get the degrees of freedom (scheduling flexibility) for a given course.
     *
     * @param c The course for which to retrieve degrees of freedom.
     * @return The degrees of freedom for the course, or -1 if the information is not available.
     */
    @Transactional
    @Override
    public synchronized int getDegreesOfFreedom(@NotNull Course c) {
        log.info("Get degree of freedom for course '{}'", c.getName());
        Optional<Options> opt = this.getOptionsForCourse(c);
        return opt.map(Options::getDegreesOfFreedom)
                .orElse(-1);
    }

    /**
     * Get the degrees of freedom (scheduling flexibility) for a given course, considering room
     * constraints.
     *
     * @param c The course for which to retrieve degrees of freedom.
     * @return The degrees of freedom for the course, considering room constraints, or -1 if the
     * information is not available.
     */
    @Transactional
    @Override
    public synchronized int getDegreesOfFreedomWithRooms(@NotNull Course c) {
        log.info("Get degree of freedom for course '{}'", c.getName());
        Optional<Options> opt = this.getOptionsForCourse(c);

        return opt.map(Options::getDegreesOfFreedomWithRooms)
                .orElse(-1);
    }

    /**
     * Get the course with the specified UUID.
     *
     * @param courseID The UUID of the course to retrieve.
     * @return The course with the specified UUID, or null if not found.
     */
    public Course getCourse(@NotNull UUID courseID) {
        return this.courseOptions.get(courseID)
                .getCourse();
    }

    /**
     * Add the course to the scheduler
     *
     * @param course course
     * @return true if not already in schedule
     */
    private boolean addCourse(@NotNull Course course) {

        if (this.courseOptions.containsKey(course.getId())) {
            return false;
        }

        generateCourseOptions(course);
        return true;
    }

    /**
     * @param course The course for which to generate scheduling options.
     */
    private void generateCourseOptions(@NotNull Course course) { // handle week
        /*
         Generating the course
         Check if course already in courseOptions
         Check if course in database
         - Get all Entities associated with the course
         - Check if curriculum exists
        */
        course = this.courseRepository.findById(course.getId())
                .get();

        List<CourseRelation> courseRelationAs =
                new ArrayList<>(courseRelationRepository.findByCourseA_Id(course.getId()));
        List<CourseRelation> courseRelationBs =
                new ArrayList<>(courseRelationRepository.findByCourseB_Id(course.getId()));

        course.setCourseRelationsA(courseRelationAs);
        course.setCourseRelationsB(courseRelationBs);

        Set<DayOfWeek> dw = EnumSet.allOf(DayOfWeek.class);

        generateCourseLimitations(course);
        Set<Curriculum<?>> curriculums = new HashSet<>();

        curriculums.add(courseScheduleLimitations.get(course.getId()));

        for (Employee e : course.getLecturers()) {
            this.addEmployee(e);
            this.employeeScheduleLimitations.get(e.getId())
                    .getOwner()
                    .getCourses()
                    .add(course);
            curriculums.add(this.employeeScheduleLimitations.get(e.getId()));
        }

        for (DegreeSemester d : course.getSemesters()) {
            this.addDegreeSemester(d);
            this.degreeSemesterScheduleLimitations
                    .get(d.getId())
                    .getOwner()
                    .getCourses()
                    .add(course);
            curriculums.add(this.degreeSemesterScheduleLimitations.get(d.getId()));
        }

        log.info(
                "create options for course={} got lecturers={} and degreesemester={}",
                course,
                course.getLecturers(),
                course.getSemesters());
        Options options = new Options(course);

        Integer weeksPerSemester = course.getWeeksPerSemester();
        if (weeksPerSemester == null) {
            weeksPerSemester = 0;
        }

        Set<Integer> weeks = new HashSet<>(weeksPerSemester);

        for (int i = 0; i < weeksPerSemester; i++) {
            weeks.add(i + 1);
        }

        options.generateOptions(
                this,
                weeks,
                dw,
                this.timeslotRepository.findByTimetableId(timetableID),
                curriculums);

        this.courseOptions.put(course.getId(), options);
    }

    /**
     * Generates scheduling limitations for a specific course based on work hours and availability.
     *
     * @param course The course for which to generate scheduling limitations.
     */
    private void generateCourseLimitations(@NotNull Course course) {
        Curriculum<Course> curriculum = new Curriculum<>(course);

        List<Timeslot> allTs = this.timeslotRepository.findByTimetableId(timetableID);

        Course cour = this.courseRepository.findById(course.getId())
                .get();

        // we want to:
        // - create a matrix based on every day with every timeslot marked as unavailable
        // - for every work time defined by the employee, we remove the timeslot
        // - with no other work times defined we get a list of time frames which are marked as
        // unavailable
        Map<DayOfWeek, Set<Interval>> availableTimes = new HashMap<>();

        // Create a timetable for which every work hour is being collected
        for (CourseTimeslot w : cour.getCourseTimeslots()) {
            Interval interval = new Interval(w.getTimeslot());

            Set<Interval> intervals = availableTimes.getOrDefault(w.getWeekday(), new HashSet<>());

            intervals.add(interval);
            availableTimes.put(w.getWeekday(), intervals);
        }

        for (DayOfWeek dw : DayOfWeek.values()) {
            Set<Interval> intervals = availableTimes.getOrDefault(dw, new HashSet<>());
            for (Timeslot ts : allTs) {
                Interval interval = new Interval(ts);
                // Only if the timeslot is not being described as a working hour we add
                // the interval as a blocker
                if (intervals.stream()
                        .noneMatch(segments -> segments.isIntersecting(interval))
                        && cour.getWeeksPerSemester() != null) {
                    // Timestlot restrictions apply to all weeks
                    for (int w = 0; w < cour.getWeeksPerSemester(); w++) {
                        curriculum.add(w + 1, dw, new Interval(ts));
                    }
                }
            }
        }

        this.courseScheduleLimitations.put(course.getId(), curriculum);
    }

    /**
     * Adds an employee to the scheduling system and generates scheduling limitations if not already
     * present.
     *
     * @param e The employee to add.
     * @return True if the employee was successfully added, false if the employee is already
     * present.
     */
    private boolean addEmployee(@NotNull Employee e) {
        if (this.employeeScheduleLimitations.containsKey(e.getId())) {
            return false;
        }

        generateEmployeeSchedule(e);
        return true;
    }

    /**
     * Generates scheduling limitations for an employee based on work hours and availability.
     *
     * @param employee The employee for whom to generate scheduling limitations.
     */
    private void generateEmployeeSchedule(@NotNull Employee employee) {
        Curriculum<Employee> curriculum = new Curriculum<>(employee);

        List<Timeslot> allTs = this.timeslotRepository.findByTimetableId(timetableID);
        Timetable timetable =
                this.timetableRepository.findById(this.timetableID)
                        .isPresent()
                        ? this.timetableRepository.findById(this.timetableID)
                        .get()
                        : new Timetable();

        Employee emp = this.employeeRepository.findById(employee.getId())
                .get();

        // we want to:
        // - create a matrix based on every day with every timeslot marked as unavailable
        // - for every work time defined by the employee, we remove the timeslot
        // - with no other work times defined we get a list of time frames which are marked as
        // unavailable
        Map<DayOfWeek, Set<Interval>> worktimes = new HashMap<>();

        // Create a timetable for which every work hour is being collected
        for (WorkTime w : emp.getWorkTimes()) {
            Interval interval = new Interval(w.getTimeslot());

            Set<Interval> intervals = worktimes.getOrDefault(w.getWeekday(), new HashSet<>());

            intervals.add(interval);
            worktimes.put(w.getWeekday(), intervals);
        }

        for (DayOfWeek dw : DayOfWeek.values()) {
            Set<Interval> intervals = worktimes.getOrDefault(dw, new HashSet<>());
            for (Timeslot ts : allTs) {
                Interval interval = new Interval(ts);
                // Only if the timeslot is not being described as a working hour we add
                // the interval as a blocker
                if (intervals.stream()
                        .noneMatch(segments -> segments.isIntersecting(interval))
                        && timetable.getNumberOfWeeks() != null) {
                    for (int w = 0; w < timetable.getNumberOfWeeks(); w++) {
                        // Timestlot restrictions apply to all weeks
                        curriculum.add(w + 1, dw, new Interval(ts));
                    }
                }
            }
        }

        this.employeeScheduleLimitations.put(employee.getId(), curriculum);
    }

    /**
     * Regenerates all associations with the scheduler
     */
    @Transactional
    public synchronized void regenerate() {
        this.employeeScheduleLimitations.clear();
        this.degreeSemesterScheduleLimitations.clear();
        this.courseScheduleLimitations.clear();
        this.courseOptions.clear();
        this.roomClaims.clear();
        this.roomScheduleLimitations.clear();
        initialize();
    }

    /**
     * return the schedule associated with the employee
     *
     * @param e Employee
     * @return schedule with every already scheduled event associated with the employee
     */
    public Optional<Curriculum<Employee>> getScheduleForEmployee(@NotNull IdentifiableEntity e) {
        return Optional.ofNullable(this.employeeScheduleLimitations.get(e.getId()));
    }

    /**
     * return the schedule associated with the degree semester
     *
     * @param ds Employee
     * @return schedule with every already scheduled event associated with degree semester
     */
    public Optional<Curriculum<DegreeSemester>> getScheduleForDegreeSems(
            @NotNull IdentifiableEntity ds) {
        return Optional.ofNullable(this.degreeSemesterScheduleLimitations.get(ds.getId()));
    }

    /**
     * return all options associated with the course
     *
     * @param c associated Course
     * @return a schedule with all possibilities where an event could be considered
     */
    public Optional<Options> getOptionsForCourse(@NotNull IdentifiableEntity c) {
        return Optional.ofNullable(this.courseOptions.get(c.getId()));
    }

    /**
     * return all options associated with the course
     *
     * @param c associated Course
     * @return a schedule with all possibilities where an event could be considered
     */
    public Optional<Options> getOptionsForCourse(@NotNull UUID c) {
        return Optional.ofNullable(this.courseOptions.get(c));
    }

    /**
     * return a busy schedule for the room
     *
     * @param room Room
     * @return schedule with every already scheduled event associated with room
     */
    public Optional<Curriculum<Room>> getScheduleForRoom(@NotNull IdentifiableEntity room) {
        return Optional.ofNullable(this.roomScheduleLimitations.get(room.getId()));
    }

    /**
     * return all claims by cources for the room
     *
     * @param room Room
     * @return schedule with every already scheduled event associated with room
     */
    public Optional<RoomClaims> getClaimsForRoom(@NotNull IdentifiableEntity room) {
        return Optional.ofNullable(this.roomClaims.get(room.getId()));
    }

    /**
     * return all claims by courses for the room
     *
     * @param r Room
     * @return schedule with every already scheduled event associated with room
     */
    Optional<RoomClaims> getClaimsForRoom(@NotNull Room r) {
        return Optional.ofNullable(this.roomClaims.get(r.getId()));
    }

    /**
     * add claim
     *
     * @param r Room
     * @return schedule with every already scheduled event associated with room
     */
    boolean addClaimToRoom(@NotNull Room r, Integer week, DayOfWeek d, @NotNull RoomClaim rc) {
        if (this.getClaimsForRoom(r)
                .isEmpty()) {
            this.addRoom(r);
        }
        return this.getClaimsForRoom(r)
                .get()
                .add(week, d, rc);
    }

    /**
     * Query all available Rooms for the provided course and return the associated allowed
     * combinations if there are any
     *
     * @param course the associated course for a which the rooms should be provided for
     * @param d      day of the week for the given start
     * @param start  start of the allocated time frame
     * @param end    end of the to be allocated time frame
     * @return a set of room combinations for the given time frame
     */
    Set<Set<Room>> getAvailableRooms(
            Course course, Integer week, DayOfWeek d, LocalTime start, LocalTime end) {
        Set<Set<Room>> availableRooms = new HashSet<>();
        log.info("Querying all available rooms: suited rooms {}", course.getSuitedRooms());

        // if course has some rooms associated to it, use them instead
        // if no suitedRooms are defined create room combinations consisting of each room
        // individually
        if (!course.getSuitedRooms()
                .isEmpty()) {
            for (RoomCombination cs : course.getSuitedRooms()) {
                availableRooms.add(new HashSet<>(cs.getRooms()));
            }
        } else {
            for (Curriculum<Room> cs : this.roomScheduleLimitations.values()) {
                if (cs.isTimeslotAvailable(week, d, start, end)) {
                    availableRooms.add(Set.of(cs.getOwner()));
                }
            }
        }

        return availableRooms;
    }

    /**
     * Converts a collection of identifiable entities to a set of their IDs while preserving the
     * order.
     *
     * @param entities The collection of entities to convert.
     * @param <T>      The type of identifiable entity.
     * @return A set of IDs in the same order as the original collection.
     */
    private static <T extends IdentifiableEntity> Set<UUID> convertEntitiesToSetOfIDs(
            Collection<T> entities) {
        return entities.stream()
                .map(IdentifiableEntity::getId)
                // linked hash set to make sure the order is kept
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Converts a collection of identifiable entities to a list of their IDs.
     *
     * @param entities The collection of entities to convert.
     * @param <T>      The type of identifiable entity.
     * @return A list of IDs.
     */
    private static <T extends IdentifiableEntity> List<UUID> convertEntitiesToListOfIDs(
            Collection<T> entities) {
        return entities.stream()
                .map(IdentifiableEntity::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Adds a degree semester to the scheduling system and generates scheduling limitations if not
     * already present.
     *
     * @param ds The degree semester to add.
     * @return True if the degree semester was successfully added, false if it is already present.
     */
    private boolean addDegreeSemester(@NotNull DegreeSemester ds) {
        if (this.degreeSemesterScheduleLimitations.containsKey(ds.getId())) {
            return false;
        }
        generateDegreeSemesterSchedule(ds);
        return true;
    }

    /**
     * Generates scheduling limitations for a degree semester.
     *
     * @param ds The degree semester for which to generate scheduling limitations.
     */
    private void generateDegreeSemesterSchedule(@NotNull DegreeSemester ds) {
        this.degreeSemesterScheduleLimitations.put(ds.getId(), new Curriculum<>(ds));
    }

    /**
     * Adds a room to the scheduling system and generates scheduling limitations if not already
     * present.
     *
     * @param r The room to add.
     * @return True if the room was successfully added, false if it is already present.
     */
    private boolean addRoom(@NotNull Room r) {
        if (this.roomScheduleLimitations.containsKey(r.getId())
                && this.roomClaims.containsKey(r.getId())) {
            return false;
        }
        generateRoomSchedule(r);
        return true;
    }

    /**
     * Generates scheduling limitations for a room.
     *
     * @param room The room for which to generate scheduling limitations.
     */
    private void generateRoomSchedule(@NotNull Room room) {
        this.roomClaims.put(room.getId(), new RoomClaims(room));
        this.roomScheduleLimitations.put(room.getId(), new Curriculum<>(room));
    }
}
