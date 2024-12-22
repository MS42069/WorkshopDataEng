package com.fhwedel.softwareprojekt.v1.scheduler;

import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionDTO;
import com.fhwedel.softwareprojekt.v1.dto.schedule.OptionsDTO;
import com.fhwedel.softwareprojekt.v1.model.Course;
import com.fhwedel.softwareprojekt.v1.model.DegreeSemester;
import com.fhwedel.softwareprojekt.v1.model.Employee;
import com.fhwedel.softwareprojekt.v1.model.Room;
import com.fhwedel.softwareprojekt.v1.model.Timeslot;
import com.fhwedel.softwareprojekt.v1.model.WeekEvent;
import com.fhwedel.softwareprojekt.v1.repository.CourseRepository;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.transaction.annotation.Transactional;

/**
 * Represents a collection of scheduling options for a specific course. These options define
 * possible combinations of timeslots and rooms where events for the associated course can be
 * scheduled.
 */
@RequiredArgsConstructor
@Slf4j
public class Options extends Schedule<Option> {
    /** Stores every weekevent for which the associated course has been scheduled to */
    private final Set<WeekEvent> alreadyScheduled = new HashSet<>();

    /** Associated course which this option belongs to */
    private final Course course;

    /** Set of UUIDs representing associated courses that are scheduled before this.course. */
    @Getter final Set<UUID> coursesBefore = new HashSet<>();

    /** Set of UUIDs representing associated courses that are scheduled after this.course. */
    @Getter final Set<UUID> coursesAfter = new HashSet<>();

    /**
     * Returns a navigable set of options that intersect with the specified day and time interval.
     *
     * @param day The day of the week
     * @param i The time interval
     * @return A navigable set of intersecting options
     */
    @Override
    public NavigableSet<Option> getIntersectingIntervals(Integer week, DayOfWeek day, Interval i) {
        assert !i.isEmpty();

        Map<DayOfWeek, TreeSet<Option>> weeklySchedule =
                this.schedule.getOrDefault(week, new HashMap<>());
        TreeSet<Option> scheduleOnDay = weeklySchedule.getOrDefault(day, new TreeSet<>());

        return scheduleOnDay.stream()
                .filter(segments -> segments.isIntersecting(i))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Returns a copy of the scheduling map, where each day's set of options is deep-copied.
     *
     * @return A deep copy of the scheduling map
     */
    @Override
    public Map<Integer, Map<DayOfWeek, NavigableSet<Option>>> getSchedule() {
        Map<Integer, Map<DayOfWeek, NavigableSet<Option>>> copiedSchedule = new HashMap<>();

        for (Map.Entry<Integer, Map<DayOfWeek, TreeSet<Option>>> weekEntry : schedule.entrySet()) {
            Integer week = weekEntry.getKey();
            Map<DayOfWeek, TreeSet<Option>> weeklySchedule = weekEntry.getValue();

            Map<DayOfWeek, NavigableSet<Option>> copiedWeeklySchedule = new HashMap<>();
            for (Map.Entry<DayOfWeek, TreeSet<Option>> dayEntry : weeklySchedule.entrySet()) {
                DayOfWeek day = dayEntry.getKey();
                TreeSet<Option> optionsForDay = dayEntry.getValue();

                NavigableSet<Option> cpy = new TreeSet<>();
                for (Option opt : optionsForDay) {
                    cpy.add(Option.copy(opt));
                }

                copiedWeeklySchedule.put(day, cpy);
            }

            copiedSchedule.put(week, copiedWeeklySchedule);
        }

        return copiedSchedule;
    }

    /**
     * Checks if a timeslot is available on the specified day at the given time interval.
     *
     * @param d The day of the week
     * @param start The start time
     * @param end The end time
     * @return True if the timeslot is available, false otherwise
     */
    @Override
    public boolean isTimeslotAvailable(Integer week, DayOfWeek d, LocalTime start, LocalTime end) {
        return !this.getIntersectingIntervals(week, d, new Interval(new Segment(start, end)))
                .isEmpty();
    }

    /**
     * Generates options for scheduling based on provided days, timeslots, and schedules.
     *
     * @param s The scheduler
     * @param days The set of days
     * @param tss The collection of timeslots
     * @param schedules The set of schedules
     * @return True if options are successfully generated, false otherwise
     */
    public boolean generateOptions(
            Scheduler s,
            Set<Integer> weeks,
            Set<DayOfWeek> days,
            Collection<Timeslot> tss,
            Set<? extends Curriculum<?>> schedules) {
        assert this.schedule.isEmpty();

        Map<Integer, Map<DayOfWeek, NavigableSet<Interval>>> schedule =
                Schedule.merge(weeks, days, new HashSet<>(tss), schedules);

        // Group the timeslots if the course if blocksize is greater than one
        if (1 < this.course.getBlockSize()) {
            schedule = Schedule.groupIntervalsInBlocks(schedule, this.course.getBlockSize());
        }

        // For every curriculum
        // check if the provided timeslots is open
        // by
        for (Integer week : schedule.keySet()) {
            Map<DayOfWeek, NavigableSet<Interval>> weekSchedule = schedule.get(week);
            for (DayOfWeek d : weekSchedule.keySet()) {
                for (Interval i : weekSchedule.get(d)) {
                    assert !(i.getStart().isEmpty() || i.getEnd().isEmpty());

                    Set<Set<Room>> availableRooms =
                            s.getAvailableRooms(
                                    this.course, week, d, i.getStart().get(), i.getEnd().get());

                    Option o = new Option(this.course, i, week, d, availableRooms);

                    Set<Room> rooms =
                            availableRooms.stream()
                                    .collect(
                                            HashSet::new,
                                            AbstractCollection::addAll,
                                            AbstractCollection::addAll);

                    for (Room room : rooms) {
                        s.addClaimToRoom(room, week, d, new RoomClaim(room, this.course, i));
                    }

                    this.add(week, d, o);
                }
            }
        }

        return true;
    }

    /**
     * Calculates the number of degrees of freedom for this set of options.
     *
     * @return The number of degrees of freedom
     */
    public int getDegreesOfFreedom() {
        int cnt = 0;

        for (Map<DayOfWeek, TreeSet<Option>> weeklySchedule : this.schedule.values()) {
            for (DayOfWeek d : weeklySchedule.keySet()) {
                for (Option o : weeklySchedule.get(d)) {
                    cnt += o.getDegreesOfFreedom() > 0 ? 1 : 0;
                }
            }
        }

        return cnt;
    }

    /**
     * Calculates the number of degrees of freedom for this set of options with rooms.
     *
     * @return The number of degrees of freedom with rooms
     */
    public int getDegreesOfFreedomWithRooms() {
        int cnt = 0;

        for (Map<DayOfWeek, TreeSet<Option>> weeklySchedule : this.schedule.values()) {
            for (DayOfWeek d : weeklySchedule.keySet()) {
                for (Option o : weeklySchedule.get(d)) {
                    cnt += o.getDegreesOfFreedom();
                }
            }
        }

        return cnt;
    }

    /**
     * Schedules a week event using this set of options.
     *
     * @param s The scheduler
     * @param e The week event
     * @param courseRepository The course repository
     * @return True if the week event is successfully scheduled, false otherwise
     */
    boolean schedule(
            @NotNull Scheduler s,
            @NotNull WeekEvent e,
            @NotNull CourseRepository courseRepository) {
        assert e.getTimeslots().size() != 0;

        Interval interval = new Interval();
        for (Timeslot ts : e.getTimeslots()) {
            log.info(
                    "adding interval segment ={} - {}-> {}",
                    ts.getStartTime(),
                    ts.getEndTime(),
                    interval.addSegment(ts.getStartTime(), ts.getEndTime()));
        }

        log.info("interval size={}", interval.size());
        assert interval.size() != 0;

        for (Segment seg : interval) {
            log.info("interval segment ={} - {}", seg.getStart(), seg.getEnd());
        }

        // Already exists and therefor not scheduled
        if (!alreadyScheduled.add(e)) {
            log.info(
                    " weekevent={} already marked as scheduled in options for options={} alreadyScheduled={}",
                    Schedule.logRepresentation(e),
                    this.getCourse(),
                    this.alreadyScheduled);
            return false;
        }

        for (Curriculum<?> currs : collectEffectedEntities(s, e)) {
            log.info(
                    "adding weekevent={}course={}  to owners={} curriculum ",
                    Schedule.logRepresentation(e),
                    e.getCourse(),
                    currs.getOwner());
            currs.addToSchedule(e);
        }

        Set<UUID> effectedOptions = new HashSet<>();
        effectedOptions.add(this.getCourse().getId());

        for (Room r : e.getRooms()) {
            RoomClaims roomClaims = s.getClaimsForRoom(r).get();
            for (RoomClaim rc : roomClaims.addClaimFor(e)) {
                log.info(
                        "adding course c={} from intersecting room claims for weekevent={}course={} room={} ",
                        rc.getCourse(),
                        Schedule.logRepresentation(e),
                        e.getCourse(),
                        rc.getRoom());
                Optional<Options> opts = s.getOptionsForCourse(rc.getCourse());
                opts.ifPresent(pairs -> effectedOptions.add(pairs.getCourse().getId()));
            }
        }

        this.cascadeConstraints(s, interval, e);

        return cascadingUpdateAddEvent(s, effectedOptions, interval, e, courseRepository);
    }

    /**
     * Allocates a given time interval for a week event caused by a scheduling action. This method
     * identifies intersecting scheduling options for the specified time interval and updates them
     * accordingly. It returns a boolean indicating whether any changes were made to the scheduling
     * options as a result of the allocation.
     *
     * @param s The scheduler responsible for the allocation.
     * @param i The time interval to allocate.
     * @param causedBy The week event that caused the allocation.
     * @return {@code true} if any changes were made to scheduling options, {@code false} otherwise.
     */
    private boolean allocateInterval(Scheduler s, Interval i, WeekEvent causedBy) {
        assert causedBy.getTimeslots().size() != 0;

        log.info(
                "allocating interval={} for effected option={} and weekday={}...",
                i,
                this,
                causedBy.getWeekday());
        NavigableSet<Option> options =
                this.getIntersectingIntervals(causedBy.getWeek(), causedBy.getWeekday(), i);
        log.info(
                "allocating intervals={} for effected option={} and weekday={}...",
                options,
                this,
                causedBy);

        boolean changes = false;
        for (Option o : options) {
            log.info("allocating interval={} for effected option={}...", i, o);
            boolean causedChanges = o.addEvent(causedBy);
            log.info(
                    "caused change={} in interval={} for effected option={}...",
                    causedChanges,
                    i,
                    o);
            changes = changes || causedChanges;
        }

        return changes;
    }

    /**
     * Applies a constraint to the scheduling options based on the given week event and allocated
     * time frame. This method checks the nature of the constraint (before or after) and updates the
     * options accordingly. It returns a boolean indicating whether the constraint was successfully
     * applied.
     *
     * @param event The week event for which the constraint is applied.
     * @param allocatedTimeFrame The allocated time frame for the event.
     * @return {@code true} if the constraint was successfully applied, {@code false} otherwise.
     */
    private boolean applyConstraint(WeekEvent event, Interval allocatedTimeFrame) {
        UUID courseEventID = event.getCourse().getId();
        DayOfWeek eventDay = event.getWeekday();

        /*
          Check the nature of the constraint
          - this.course is before event
             - all options before event are allowed -> all options from the start of the
          - this.course is after event
        */

        boolean constrainCourseBeforeEvent = this.coursesAfter.contains(courseEventID);
        boolean constrainCourseAfterEvent = this.coursesBefore.contains(courseEventID);

        if (constrainCourseBeforeEvent && constrainCourseAfterEvent) {
            return false;
        }

        if (!(constrainCourseBeforeEvent || constrainCourseAfterEvent)) {
            return false;
        }

        for (Pair<DayOfWeek, List<Option>> p : this) {
            if (constrainCourseBeforeEvent) {
                if (p.getFirst().getValue() > eventDay.getValue()) {
                    p.getSecond()
                            .forEach(
                                    opt -> {
                                        opt.addConstraint(event);
                                    });
                } else if (p.getFirst().getValue() == eventDay.getValue()) {
                    p.getSecond().stream()
                            .filter(opt -> !opt.isBefore(allocatedTimeFrame))
                            .forEach(opt -> opt.addConstraint(event));
                }
            } else {
                if (p.getFirst().getValue() < eventDay.getValue()) {
                    p.getSecond()
                            .forEach(
                                    opt -> {
                                        opt.addConstraint(event);
                                    });
                } else if (p.getFirst().getValue() == eventDay.getValue()) {
                    p.getSecond().stream()
                            .filter(opt -> !opt.isAfter(allocatedTimeFrame))
                            .forEach(opt -> opt.addConstraint(event));
                }
            }
        }

        return false;
    }

    /**
     * Cascades constraints to scheduling options associated with courses that are scheduled either
     * before or after the current course represented by this options instance. This method iterates
     * through courses before and after and applies constraints based on the given week event and
     * allocated time frame.
     *
     * @param s The scheduler responsible for constraint application.
     * @param i The allocated time frame for the week event.
     * @param causedBy The week event that caused the constraints.
     * @return {@code true} if constraints were successfully cascaded, {@code false} otherwise.
     */
    private boolean cascadeConstraints(Scheduler s, Interval i, WeekEvent causedBy) {
        for (UUID u : this.coursesBefore) {
            Options opt = s.getOptionsForCourse(u).orElseThrow();
            opt.applyConstraint(causedBy, i);
        }

        for (UUID u : this.coursesAfter) {
            Options opt = s.getOptionsForCourse(u).orElseThrow();
            opt.applyConstraint(causedBy, i);
        }

        return false;
    }

    /**
     * Removes a constraint from the scheduling options based on the given week event and allocated
     * time frame. This method checks the nature of the constraint (before or after) and updates the
     * options accordingly. It returns a boolean indicating whether the constraint was successfully
     * removed.
     *
     * @param event The week event for which the constraint is removed.
     * @param allocatedTimeFrame The allocated time frame for the event.
     * @return {@code true} if the constraint was successfully removed, {@code false} otherwise.
     */
    private boolean removeConstraint(WeekEvent event, Interval allocatedTimeFrame) {
        UUID courseEventID = event.getCourse().getId();
        DayOfWeek eventDay = event.getWeekday();

        /*
          Check the nature of the constraint
          - this.course is before event
             - all options before event are allowed -> all options from the start of the
          - this.course is after event
        */

        boolean constrainCourseBeforeEvent = this.coursesAfter.contains(courseEventID);
        boolean constrainCourseAfterEvent = this.coursesBefore.contains(courseEventID);

        if (constrainCourseBeforeEvent && constrainCourseAfterEvent) {
            return false;
        }

        if (!(constrainCourseBeforeEvent || constrainCourseAfterEvent)) {
            return false;
        }

        for (Pair<DayOfWeek, List<Option>> p : this) {
            if (constrainCourseBeforeEvent) {
                if (p.getFirst().getValue() > eventDay.getValue()) {
                    p.getSecond()
                            .forEach(
                                    opt -> {
                                        opt.removeConstraint(event);
                                    });
                } else if (p.getFirst().getValue() == eventDay.getValue()) {
                    p.getSecond().stream()
                            .filter(opt -> !opt.isBefore(allocatedTimeFrame))
                            .forEach(opt -> opt.removeConstraint(event));
                }
            } else {
                if (p.getFirst().getValue() < eventDay.getValue()) {
                    p.getSecond()
                            .forEach(
                                    opt -> {
                                        opt.removeConstraint(event);
                                    });
                } else if (p.getFirst().getValue() == eventDay.getValue()) {
                    p.getSecond().stream()
                            .filter(opt -> !opt.isAfter(allocatedTimeFrame))
                            .forEach(opt -> opt.removeConstraint(event));
                }
            }
        }

        return false;
    }

    /**
     * Cascades deconstraints to scheduling options associated with courses that are scheduled
     * either before or after the current course represented by this options instance. This method
     * iterates through courses before and after and removes constraints based on the given week
     * event and allocated time frame.
     *
     * @param s The scheduler responsible for deconstraint application.
     * @param i The allocated time frame for the week event.
     * @param causedBy The week event that caused the deconstraints.
     * @return {@code true} if deconstraints were successfully cascaded, {@code false} otherwise.
     */
    private boolean cascadeDeconstraints(Scheduler s, Interval i, WeekEvent causedBy) {
        for (UUID u : this.coursesBefore) {
            Options opt = s.getOptionsForCourse(u).orElseThrow();
            opt.removeConstraint(causedBy, i);
        }

        for (UUID u : this.coursesAfter) {
            Options opt = s.getOptionsForCourse(u).orElseThrow();
            opt.removeConstraint(causedBy, i);
        }

        return false;
    }

    /**
     * Performs a cascading update to add a week event to scheduling options associated with the
     * specified course IDs. This method allocates the given time interval for the week event caused
     * by a scheduling action, starting from the options associated with the provided course IDs and
     * propagating changes to related options. It returns a boolean indicating whether any changes
     * were made during the update.
     *
     * @param s The scheduler responsible for the update.
     * @param options The set of course IDs for options to be updated.
     * @param i The time interval to allocate.
     * @param causedBy The week event that caused the update.
     * @param courseRepository The repository for retrieving course information.
     * @return {@code true} if any changes were made during the update, {@code false} otherwise.
     */
    public static boolean cascadingUpdateAddEvent(
            Scheduler s,
            Set<UUID> options,
            Interval i,
            WeekEvent causedBy,
            CourseRepository courseRepository) {
        assert causedBy.getTimeslots().size() != 0;

        Set<UUID> toBeVisited = new HashSet<>(options);
        Set<UUID> visited = new HashSet<>();

        boolean changes = false;
        while (!toBeVisited.isEmpty()) {
            UUID optID = toBeVisited.iterator().next();
            assert optID != null;
            Options opt =
                    s.getOptionsForCourse(optID)
                            .orElseThrow(
                                    () ->
                                            new RuntimeException(
                                                    String.format(
                                                            "scheduler could not resolve unknown options for course uuid%s",
                                                            optID)));

            log.info("visiting effected option={}...", opt);
            boolean causedChanges = opt.allocateInterval(s, i, causedBy);
            log.info("option={} caused changes={}", opt, causedChanges);

            if (causedChanges) {
                Set<Course> courses = getAssociatedCourses(opt, courseRepository);

                Set<UUID> associatedOptions = new HashSet<>();
                for (Course associatedCourse : courses) {
                    associatedOptions.add(associatedCourse.getId());
                }

                associatedOptions.removeAll(visited);
                toBeVisited.addAll(associatedOptions);
            }

            changes = changes || causedChanges;

            toBeVisited.remove(optID);
            log.info("marking option={} as visited ", opt);
            visited.add(optID);
        }

        return changes;
    }

    /**
     * Unschedules a week event by removing it from the scheduling options associated with this
     * course. This method deallocates the time interval for the specified week event and updates
     * related options accordingly. It returns a boolean indicating whether the week event was
     * successfully unscheduled.
     *
     * @param s The scheduler responsible for the unscheduling.
     * @param e The week event to unschedule.
     * @param repository The repository for retrieving course information.
     * @return {@code true} if the week event was successfully unscheduled, {@code false} otherwise.
     */
    boolean unschedule(
            @NotNull Scheduler s, @NotNull WeekEvent e, @NotNull CourseRepository repository) {
        assert e.getTimeslots().size() != 0;

        Interval interval = new Interval();
        for (Timeslot ts : e.getTimeslots()) {
            log.info(
                    "adding interval segment ={} - {}-> {}",
                    ts.getStartTime(),
                    ts.getEndTime(),
                    interval.addSegment(ts.getStartTime(), ts.getEndTime()));
        }

        log.info("interval size={}", interval.size());
        assert interval.size() != 0;

        // Not existent and therefor not removed
        if (!alreadyScheduled.remove(e)) {
            return false;
        }

        Set<Options> effectedOptions = new HashSet<>();
        effectedOptions.add(this);

        for (Curriculum<?> curriculum : collectEffectedEntities(s, e)) {
            log.info(
                    "removing weekevent=({}) course={}  from owners={} curriculum ",
                    Schedule.logRepresentation(e),
                    e.getCourse(),
                    curriculum.getOwner());
            curriculum.removeFromSchedule(e);
        }

        for (Room r : e.getRooms()) {
            RoomClaims roomClaims = s.getClaimsForRoom(r).get();
            for (RoomClaim rc : roomClaims.removeClaimFor(e)) {
                Optional<Options> opts = s.getOptionsForCourse(rc.getCourse());
                opts.ifPresent(effectedOptions::add);
            }
        }
        this.cascadeDeconstraints(s, interval, e);
        return cascadingUpdateRemoveEvent(s, effectedOptions, interval, e, repository);
    }

    /**
     * Frees a previously allocated time interval for a week event caused by a scheduling action.
     * This method identifies intersecting scheduling options for the specified time interval and
     * updates them accordingly to remove the week event. It returns a boolean indicating whether
     * any changes were made during the freeing process.
     *
     * @param s The scheduler responsible for the freeing process.
     * @param i The time interval to free.
     * @param causedBy The week event that caused the freeing.
     * @return {@code true} if any changes were made during the freeing process, {@code false}
     *     otherwise.
     */
    private boolean freeInterval(Scheduler s, Interval i, WeekEvent causedBy) {
        assert causedBy.getTimeslots().size() != 0;

        NavigableSet<Option> options =
                this.getIntersectingIntervals(causedBy.getWeek(), causedBy.getWeekday(), i);
        boolean changes = false;
        for (Option o : options) {
            log.info("freeing interval={} for effected option={}...", i, o);
            boolean causedChanges = o.removeEvent(causedBy);
            log.info(
                    "caused change={} in day={} interval={} for effected option={}...",
                    causedChanges,
                    causedBy.getWeekday(),
                    i,
                    o);
            changes = changes || causedChanges;
        }
        return changes;
    }

    /**
     * Performs a cascading update to remove a week event from scheduling options associated with
     * the specified course IDs. This method frees the given time interval for the week event,
     * starting from the options associated with the provided course IDs and propagating changes to
     * related options. It returns a boolean indicating whether any changes were made during the
     * update.
     *
     * @param s The scheduler responsible for the update.
     * @param options The set of options to be updated.
     * @param i The time interval to free.
     * @param causedBy The week event that caused the update.
     * @param courseRepository The repository for retrieving course information.
     * @return {@code true} if any changes were made during the update, {@code false} otherwise.
     */
    private static boolean cascadingUpdateRemoveEvent(
            Scheduler s,
            Set<Options> options,
            Interval i,
            WeekEvent causedBy,
            CourseRepository courseRepository) {
        assert causedBy.getTimeslots().size() != 0;

        Set<Options> toBeVisited = new HashSet<>(options);
        Set<Options> visited = new HashSet<>();

        boolean changes = false;
        while (!toBeVisited.isEmpty()) {
            Options opt = toBeVisited.iterator().next();
            boolean causedChanges = opt.freeInterval(s, i, causedBy);
            if (causedChanges) {
                Set<Course> courses = getAssociatedCourses(opt, courseRepository);

                Set<Options> associatedOptions = new HashSet<>();
                for (Course associatedCourse : courses) {
                    associatedOptions.add(s.getOptionsForCourse(associatedCourse).get());
                }

                associatedOptions.removeAll(visited);
                toBeVisited.addAll(associatedOptions);
            }
            changes = changes || causedChanges;

            toBeVisited.remove(opt);
            visited.add(opt);
        }

        return changes;
    }

    /**
     * Retrieves a set of associated courses for the given options, including courses associated
     * with lecturers and degree semesters.
     *
     * @param opt The options for which associated courses are retrieved.
     * @param repository The repository for retrieving course information.
     * @return A set of associated courses.
     */
    private static Set<Course> getAssociatedCourses(
            @NotNull Options opt, @NotNull CourseRepository repository) {
        Set<Course> courses = new HashSet<>();
        Course course =
                repository
                        .findById(opt.getCourse().getId())
                        .orElseThrow(() -> new RuntimeException("unknown course id"));

        for (Employee e : course.getLecturers()) {
            List<Course> alsoLecturersCourse = repository.findAllDistinctByLecturersId(e.getId());
            courses.addAll(alsoLecturersCourse);
            log.info(
                    "option={} caused changes in employee={} effected courses={}",
                    opt,
                    e.getAbbreviation(),
                    e.getCourses());
        }

        for (DegreeSemester ds : course.getSemesters()) {
            log.info("option={} caused changes in ds ={}", opt, ds);
            List<Course> alsoVisitsCourses = repository.findAllDistinctBySemestersId(ds.getId());
            courses.addAll(alsoVisitsCourses);
            log.info(
                    "option={} caused changes in employee={} effected courses={}",
                    opt,
                    ds.getExtensionName(),
                    ds.getCourses());
        }

        return courses;
    }

    /**
     * Collects entities that may be affected by a week event, including employees, degree
     * semesters, and rooms, based on the scheduling options associated with this course.
     *
     * @param s The scheduler responsible for the collection.
     * @param e The week event for which entities are collected.
     * @return A set of affected curriculum entities.
     */
    private Set<Curriculum<?>> collectEffectedEntities(@NotNull Scheduler s, @NotNull WeekEvent e) {
        assert e.getTimeslots().size() != 0;

        Set<Curriculum<?>> curriculums = new HashSet<>();

        // Check for intersecting to check for -> Causes Conflict via Employee
        for (Employee emp : this.course.getLecturers()) {
            curriculums.add(s.getScheduleForEmployee(emp).get());
        }

        // Check for Conflict by looking for intersecting -> Causes Conflict via DegreeSemester
        for (DegreeSemester ds : this.course.getSemesters()) {
            curriculums.add(s.getScheduleForDegreeSems(ds).get());
        }

        // Check for Conflict by looking for intersecting -> Causes Conflict via Rooms
        for (Room rs : e.getRooms()) {
            curriculums.add(s.getScheduleForRoom(rs).get());
        }

        return curriculums;
    }

    /**
     * Retrieves a set of week events that have already been scheduled for this course.
     *
     * @return A set of already scheduled week events.
     */
    public Set<WeekEvent> getAlreadyScheduled() {
        return new HashSet<>(this.alreadyScheduled);
    }

    /**
     * Retrieves the course associated with these scheduling options.
     *
     * @return The associated course.
     */
    public Course getCourse() {
        return this.course;
    }

    /**
     * Lists available scheduling options for this course, excluding those that conflict with
     * already scheduled week events. This method generates an `OptionsDTO` containing scheduling
     * options and their degrees of freedom for the specified list of timeslots.
     *
     * @param timeslots The list of timeslots for which options are generated.
     * @return An `OptionsDTO` containing available scheduling options.
     */
    @Transactional
    public OptionsDTO listOptions(@NotNull List<Timeslot> timeslots) {
        OptionsDTO dto = new OptionsDTO();
        dto.setCourse(this.course.getId());

        List<OptionDTO> allDTOs = new LinkedList<>();

        for (Pair<DayOfWeek, List<Option>> optsForDay : this) {
            List<Option> options = optsForDay.getSecond();
            List<OptionDTO> allOptionsForDay =
                    options.stream()
                            .filter(
                                    opt ->
                                            this.alreadyScheduled.stream()
                                                    .noneMatch(
                                                            event ->
                                                                    event.getWeek() == opt.getWeek()
                                                                            && event.getWeekday()
                                                                                    .equals(
                                                                                            opt
                                                                                                    .getDay())
                                                                            && Interval.from(
                                                                                            event
                                                                                                    .getTimeslots())
                                                                                    .isIntersecting(
                                                                                            opt)
                                                                            && opt
                                                                                    .isSharingRelationToEvent(
                                                                                            event)))
                            .map(segments -> segments.createOptionDTOs(timeslots))
                            .collect(LinkedList::new, LinkedList::addAll, LinkedList::addAll);
            allDTOs.addAll(allOptionsForDay);
        }

        dto.setDegreeOfFreedom(this.getDegreesOfFreedom());
        dto.setOptions(allDTOs);

        return dto;
    }

    /**
     * Returns a string representation of these scheduling options.
     *
     * @return A string representation of the scheduling options.
     */
    public String toString() {
        return String.format("Options[%s]", this.course.getName());
    }

    /**
     * Converts the scheduling options into a three-dimensional matrix representation. The matrix
     * represents the availability of options on different days and timeslots.
     *
     * @param days The list of days for which the matrix is generated.
     * @param tss The list of timeslots for which the matrix is generated.
     * @return A three-dimensional matrix representing option availability.
     */
    public int[][][][] asMatrix(List<Integer> weeks, List<DayOfWeek> days, List<Timeslot> tss) {
        int[][][][] matrix = new int[weeks.size()][][][];

        int weekIndex = 0;
        for (Integer week : weeks) {
            Map<DayOfWeek, TreeSet<Option>> weeklySchedule =
                    this.schedule.getOrDefault(week, new HashMap<>());

            int dayIndex = 0;
            for (DayOfWeek d : days) {
                TreeSet<Option> opts = weeklySchedule.getOrDefault(d, new TreeSet<>());
                matrix[weekIndex][dayIndex] = new int[opts.size()][];

                int optIndex = 0;
                for (Option o : opts) {
                    matrix[weekIndex][dayIndex][optIndex] = o.asVector(tss);
                    optIndex++;
                }
                dayIndex++;
            }
            weekIndex++;
        }
        return matrix;
    }
    /**
     * Converts the scheduling options into a reduced two-dimensional matrix representation. The
     * matrix represents the availability of options on different days and timeslots, with values
     * indicating the count of available options.
     *
     * @param days The list of days for which the matrix is generated.
     * @param tss The list of timeslots for which the matrix is generated.
     * @return A two-dimensional matrix representing option availability counts.
     */
    public int[][][] asReducedMatrix(
            List<Integer> weeks, List<DayOfWeek> days, List<Timeslot> tss) {
        int[][][][] originalMatrix = asMatrix(weeks, days, tss);
        int[][][] reducedMatrix = new int[weeks.size()][days.size()][tss.size()];

        for (int weekIndex = 0; weekIndex < weeks.size(); weekIndex++) {
            for (int dayIndex = 0; dayIndex < days.size(); dayIndex++) {
                for (int tsIndex = 0; tsIndex < tss.size(); tsIndex++) {
                    int sum = 0;
                    for (int optIndex = 0;
                            optIndex < originalMatrix[weekIndex][dayIndex].length;
                            optIndex++) {
                        sum += originalMatrix[weekIndex][dayIndex][optIndex][tsIndex];
                    }
                    reducedMatrix[weekIndex][dayIndex][tsIndex] = sum;
                }
            }
        }

        return reducedMatrix;
    }
    /**
     * Converts the scheduling options into a human-readable string representation of the reduced
     * matrix. The string represents the availability of options on different days and timeslots,
     * with values indicating the count of available options.
     *
     * @param days The list of days for which the string representation is generated.
     * @param tss The list of timeslots for which the string representation is generated.
     * @return A human-readable string representation of option availability counts.
     */
    public String toStringifiedMatrix(
            List<Integer> weeks, List<DayOfWeek> days, List<Timeslot> tss) {
        int[][][] matrixRep = this.asReducedMatrix(weeks, days, tss);
        StringBuilder builder = new StringBuilder();

        for (int weekIndex = 0; weekIndex < weeks.size(); weekIndex++) {
            builder.append("Week ").append(weeks.get(weekIndex)).append(":\n");
            for (int[] vector : matrixRep[weekIndex]) {
                for (int i = 0; i < vector.length; i++) {
                    builder.append(vector[i]);
                    if (i + 1 < vector.length) {
                        builder.append(", ");
                    }
                }
                builder.append("\n");
            }
            if (weekIndex + 1 < weeks.size()) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }
}
