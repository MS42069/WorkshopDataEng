package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.model.*;
import com.fhwedel.softwareprojekt.v1.repository.*;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for copying elements between timetables. This service provides methods to copy
 * various elements, including rooms, timeslots, employees, courses, degrees, degree semesters,
 * course relations, course timeslots, room combinations, week events, and special events, from one
 * timetable to another.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CopyService {

    /** Repository for managing Special Events. */
    private final SpecialEventRepository specialEventRepository;

    /** Repository for managing Room Combinations. */
    private final RoomCombinationRepository roomCombinationRepository;

    /** Repository for managing Degree Semesters. */
    private final DegreeSemesterRepository degreeSemesterRepository;

    /** Repository for managing Degrees. */
    private final DegreeRepository degreeRepository;

    /** Repository for managing Course Timeslots. */
    private final CourseTimeslotRepository courseTimeslotRepository;

    /** Repository for managing Week Events. */
    private final WeekEventRepository weekEventRepository;

    /** Repository for managing Course Relations. */
    private final CourseRelationRepository courseRelationRepository;

    /** Repository for managing Courses. */
    private final CourseRepository courseRepository;

    /** Repository for managing Work Times. */
    private final WorkTimeRepository workTimeRepository;

    /** Repository for managing Employees. */
    private final EmployeeRepository employeeRepository;

    /** Repository for managing Timeslots. */
    private final TimeslotRepository timeslotRepository;

    /** Repository for managing Rooms. */
    private final RoomRepository roomRepository;

    /**
     * Copies all elements from one timetable to another.
     *
     * @param copyTo The target timetable to copy to.
     * @param copyFrom The source timetable to copy from.
     */
    @Transactional
    public void copy(Timetable copyTo, Timetable copyFrom) {
        copyPartial(copyTo, copyFrom, true, true, true, true, true, true, true, true);
    }

    /**
     * Copies selected elements from one timetable to another.
     *
     * @param copyTo The target timetable to copy to.
     * @param copyFrom The source timetable to copy from.
     * @param b_employee Boolean flag indicating whether to copy employees.
     * @param b_room Boolean flag indicating whether to copy rooms.
     * @param b_course Boolean flag indicating whether to copy courses.
     * @param b_degree Boolean flag indicating whether to copy degrees.
     * @param b_degreesemester Boolean flag indicating whether to copy degree semesters.
     * @param b_timeslot Boolean flag indicating whether to copy timeslots.
     * @param b_weekevent Boolean flag indicating whether to copy week events.
     * @param b_special_event Boolean flag indicating whether to copy special events.
     */
    @Transactional
    public void copyPartial(
            Timetable copyTo,
            Timetable copyFrom,
            boolean b_employee,
            boolean b_room,
            boolean b_course,
            boolean b_degree,
            boolean b_degreesemester,
            boolean b_timeslot,
            boolean b_weekevent,
            boolean b_special_event) {

        // Das Ziel leeren
        cleanTimetable(copyTo);

        // Raum kopieren
        if (b_room) {
            List<Room> rooms = roomRepository.findByTimetableId(copyFrom.getId());
            for (Room source : rooms) {
                Room room = copyRoom(source, copyTo);
                roomRepository.save(room);
            }
        }

        // Timeslots kopieren
        if (b_timeslot) {
            List<Timeslot> timeslots = timeslotRepository.findByTimetableId(copyFrom.getId());
            for (Timeslot source : timeslots) {
                Timeslot timeslot = copyTimeslot(source, copyTo);
                timeslotRepository.save(timeslot);
            }
        }

        // Mitarbeiter kopieren
        List<Employee> newEmployees = null;
        if (b_employee) {
            List<Employee> employees = employeeRepository.findByTimetable(copyFrom);
            newEmployees = new ArrayList<>();
            for (Employee source : employees) {
                Employee employee = copyEmployee(source, copyTo);
                employee = employeeRepository.save(employee);
                newEmployees.add(employee);
            }
            // wird kopiert, wenn Mitarbeiter und Timeslots beide kopiert werden
            if (b_timeslot) {
                // Worktimes
                int i = 0;
                for (Employee employee : employees) {
                    List<WorkTime> worktimes =
                            workTimeRepository.findByEmployeeId(employee.getId());
                    List<WorkTime> newWorktimes = new ArrayList<>();
                    for (WorkTime source : worktimes) {
                        WorkTime workTime = copyWorkTime(source, copyTo);
                        workTime = workTimeRepository.save(workTime);
                        newWorktimes.add(workTime);
                    }
                    newEmployees.get(i).setWorkTimes(newWorktimes);
                    i++;
                }
            }
        }
        // Kurse kopieren
        List<Course> newCourses = null;
        if (b_course) {
            // Courses
            List<Course> courses = courseRepository.findByTimetable(copyFrom);
            newCourses = new ArrayList<>();
            for (Course source : courses) {
                Course course = copyCourse(source, copyTo, b_employee);
                course = courseRepository.save(course);
                newCourses.add(course);
            }
        }

        // dem Mitarbeiter Kurse zuordnen
        if (b_employee && b_course) {
            // Employee Kurse setzen
            for (Employee employee : newEmployees) {
                for (Course course : newCourses) {
                    if (course.getLecturers().contains(employee)) {
                        employee.getCourses().add(course);
                    }
                }
            }
        }
        // Kursrelationen setzen
        if (b_course) {
            List<CourseRelation> courseRelations =
                    courseRelationRepository.findAll().stream()
                            .filter(r -> r.getCourseA().getTimetable().equals(copyFrom))
                            .filter(r -> r.getCourseB().getTimetable().equals(copyFrom))
                            .toList();
            List<CourseRelation> newCourseRelations = new ArrayList<>();
            for (CourseRelation source : courseRelations) {
                CourseRelation courseRelation = copyCourseRelation(source, copyTo);
                courseRelation = courseRelationRepository.save(courseRelation);
                newCourseRelations.add(courseRelation);
            }

            // CourseRelationA setzen, wo in allen CourseRelations dieser Kurs CourseA ist
            // CourseRelationB setzen, wo in allen CourseRelations dieser Kurs CourseB ist
            for (CourseRelation courseRelation : newCourseRelations) {
                courseRelation.getCourseA().getCourseRelationsA().add(courseRelation);
                courseRelation.getCourseB().getCourseRelationsB().add(courseRelation);
            }
        }
        // CourseTimeslots mit kopieren, falls Timeslots und Kurse kopiert wurden
        if (b_course && b_timeslot) {
            List<CourseTimeslot> ctsList =
                    courseTimeslotRepository.findAll().stream()
                            .filter(r -> r.getTimeslot().getTimetable().equals(copyFrom))
                            .filter(r -> r.getCourse().getTimetable().equals(copyFrom))
                            .toList();
            List<CourseTimeslot> newCourseTimeslots = new ArrayList<>();
            for (CourseTimeslot source : ctsList) {
                CourseTimeslot cts = copyCourseTimeslot(source, copyTo);
                cts = courseTimeslotRepository.save(cts);
                newCourseTimeslots.add(cts);
            }

            // CourseTimeslots den Kursen hinzufügen
            for (Course course : newCourses) {
                for (CourseTimeslot courseTimeslot : newCourseTimeslots) {
                    if (course.equals(courseTimeslot.getCourse())) {
                        course.getCourseTimeslots().add(courseTimeslot);
                    }
                }
            }
        }
        // week-events kopieren
        List<WeekEvent> newWeekEvent;
        // Weekevent darf nur kopiert werden, wenn auch Course kopiert wird
        if (b_weekevent && b_course) {
            // Course_Events / WeekEvents
            List<WeekEvent> weekEvents = weekEventRepository.findByTimetableId(copyFrom.getId());
            newWeekEvent = new ArrayList<>();
            for (WeekEvent source : weekEvents) {
                WeekEvent weekEvent = copyWeekEvent(source, copyTo, b_timeslot, b_room);
                weekEvent = weekEventRepository.save(weekEvent);
                newWeekEvent.add(weekEvent);
            }
            // weekEvents den Kursen hinzufügen
            for (Course course : newCourses) {
                for (WeekEvent weekEvent : newWeekEvent) {
                    if (course.equals(weekEvent.getCourse())) {
                        course.getWeekEvents().add(weekEvent);
                    }
                }
            }
        }

        // Degree kopieren
        List<Degree> newDegrees = null;
        if (b_degree) {
            List<Degree> degrees = degreeRepository.findByTimetable(copyFrom);
            newDegrees = new ArrayList<>();
            for (Degree source : degrees) {
                Degree degree = copyDegree(source, copyTo);
                degree = degreeRepository.save(degree);
                newDegrees.add(degree);
            }
        }

        // degreeSemester kopieren
        List<DegreeSemester> newDegreeSemesters = null;
        if (b_degreesemester) {
            List<DegreeSemester> degreeSemesters =
                    degreeSemesterRepository.findByTimetable(copyFrom);
            newDegreeSemesters = new ArrayList<>();
            for (DegreeSemester source : degreeSemesters) {
                DegreeSemester degreeSemester =
                        copyDegreeSemester(source, copyTo, b_degree, b_course);
                degreeSemester = degreeSemesterRepository.save(degreeSemester);
                newDegreeSemesters.add(degreeSemester);
            }
        }

        // DegreeSemester zu Degree hinzufügen
        if (b_degree && b_degreesemester) {
            for (Degree degree : newDegrees) {
                for (DegreeSemester degreeSemester : newDegreeSemesters) {
                    if (degreeSemester.getDegree().equals(degree)) {
                        degree.getSemesters().add(degreeSemester);
                    }
                }
            }
        }

        // DegreeSemester zu Courses hinzufügen
        if (b_degreesemester && b_course) {
            for (Course course : newCourses) {
                for (DegreeSemester degreeSemester : newDegreeSemesters) {
                    if (degreeSemester.getCourses().contains(course)) {
                        course.getSemesters().add(degreeSemester);
                    }
                }
            }
        }
        // RoomCombinations
        if (b_course && b_room) {
            List<RoomCombination> roomCombinations =
                    roomCombinationRepository.findAll().stream()
                            .filter(r -> r.getCourse().getTimetable().equals(copyFrom))
                            // testen, ob ausreicht
                            .toList();
            List<RoomCombination> newRoomCombination = new ArrayList<>();
            for (RoomCombination source : roomCombinations) {
                RoomCombination roomCombination = copyRoomCombination(source, copyTo);
                roomCombination = roomCombinationRepository.save(roomCombination);
                newRoomCombination.add(roomCombination);
            }

            // Roomcombinations zu course hinzufügen
            for (Course course : newCourses) {
                for (RoomCombination roomCombination : newRoomCombination) {
                    if (roomCombination.getCourse().equals(course)) {
                        course.getSuitedRooms().add(roomCombination);
                    }
                }
            }
        }
        // specialEvents
        if (b_special_event) {
            List<SpecialEvent> specialEvents =
                    specialEventRepository.findByTimetableId(copyFrom.getId());
            List<SpecialEvent> newSpecialEvents = new ArrayList<>();
            for (SpecialEvent source : specialEvents) {
                SpecialEvent specialEvent = copySpecialEvent(source, copyTo);
                specialEvent = specialEventRepository.saveAndFlush(specialEvent);
                newSpecialEvents.add(specialEvent);
            }

            // SpecialEvents zu TargetTimetable hinzufügen
            copyTo.setSpecialEvents(newSpecialEvents);
        }
    }

    /**
     * Cleans the specified timetable by deleting associated data such as week events, special
     * events, course relations, course timeslots, room combinations, courses, degree semesters,
     * degrees, work times, employees, rooms, and timeslots.
     *
     * @param timetable The timetable to be cleaned.
     */
    private void cleanTimetable(Timetable timetable) {
        weekEventRepository.deleteByTimetableId(timetable.getId());
        specialEventRepository.deleteByTimetableId(timetable.getId());

        // Die courseRelations löschen, wo die Kurse zum Timetable gehören
        List<CourseRelation> courseRelations =
                courseRelationRepository.findAll().stream()
                        .filter(r -> r.getCourseA().getTimetable().equals(timetable))
                        .filter(r -> r.getCourseB().getTimetable().equals(timetable))
                        .toList();
        courseRelationRepository.deleteAll(courseRelations);

        List<CourseTimeslot> ctsList =
                courseTimeslotRepository.findAll().stream()
                        .filter(r -> r.getTimeslot().getTimetable().equals(timetable))
                        .filter(r -> r.getCourse().getTimetable().equals(timetable))
                        .toList();
        courseTimeslotRepository.deleteAll(ctsList);

        List<RoomCombination> roomCombinations =
                roomCombinationRepository.findAll().stream()
                        .filter(r -> r.getCourse().getTimetable().equals(timetable))
                        .toList();
        roomCombinationRepository.deleteAll(roomCombinations);

        courseRepository.deleteByTimetableId(timetable.getId());
        degreeSemesterRepository.deleteByTimetable(timetable);
        degreeRepository.deleteByTimetableId(timetable.getId());

        List<WorkTime> workTimes =
                workTimeRepository.findAll().stream()
                        .filter(r -> r.getEmployee().getTimetable().equals(timetable))
                        .filter(r -> r.getTimeslot().getTimetable().equals(timetable))
                        .toList();
        workTimeRepository.deleteAll(workTimes);

        employeeRepository.deleteByTimetableId(timetable.getId());
        roomRepository.deleteByTimetableId(timetable.getId());
        timeslotRepository.deleteByTimetableId(timetable.getId());
    }

    /**
     * Creates a copy of the provided room for a given timetable.
     *
     * @param room The room to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @return A copied room.
     */
    private Room copyRoom(Room room, Timetable timetable) {
        Room copy = new Room();
        copy.setRoomType(room.getRoomType());
        copy.setTimetable(timetable);
        copy.setName(room.getName());
        copy.setIdentifier(room.getIdentifier());
        copy.setAbbreviation(room.getAbbreviation());
        copy.setCapacity(room.getCapacity());
        return copy;
    }

    /**
     * Creates a copy of the provided timeslot for a given timetable.
     *
     * @param ts The timeslot to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @return A copied timeslot.
     */
    private Timeslot copyTimeslot(Timeslot ts, Timetable timetable) {
        Timeslot copy = new Timeslot();
        copy.setIndex(ts.getIndex());
        copy.setTimetable(timetable);
        copy.setEndTime(ts.getEndTime());
        copy.setStartTime(ts.getStartTime());
        return copy;
    }

    /**
     * Creates a copy of the provided employee for a given timetable.
     *
     * @param employee The employee to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @return A copied employee.
     */
    private Employee copyEmployee(Employee employee, Timetable timetable) {
        Employee copy = new Employee();
        copy.setTimetable(timetable);
        copy.setAbbreviation(employee.getAbbreviation());
        copy.setEmployeeType(employee.getEmployeeType());
        copy.setLastname(employee.getLastname());
        copy.setFirstname(employee.getFirstname());
        return copy;
    }

    /**
     * Creates a copy of the provided work time for a given timetable.
     *
     * @param workTime The work time to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @return A copied work time.
     */
    private WorkTime copyWorkTime(WorkTime workTime, Timetable timetable) {
        WorkTime copy = new WorkTime();
        copy.setWeekday(workTime.getWeekday());
        copy.setTimeslot(findTimeslotInTT(workTime.getTimeslot(), timetable));
        copy.setEmployee(findEmployeeInTT(workTime.getEmployee(), timetable));
        return copy;
    }

    /**
     * Creates a copy of the provided course for a given timetable.
     *
     * @param course The course to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @param copyEmployee Indicates whether to copy associated employees.
     * @return A copied course.
     */
    private Course copyCourse(Course course, Timetable timetable, boolean copyEmployee) {
        List<Employee> employees = new ArrayList<>();
        if (copyEmployee) {
            for (Employee employee : course.getLecturers()) {
                employees.add(findEmployeeInTT(employee, timetable));
            }
        }
        Course copy = new Course();
        copy.setTimetable(timetable);
        copy.setAbbreviation(course.getAbbreviation());
        copy.setBlockSize(course.getBlockSize());
        copy.setCasID(course.getCasID());
        copy.setCourseType(course.getCourseType());
        copy.setDescription(course.getDescription());
        copy.setName(course.getName());
        copy.setSlotsPerWeek(course.getSlotsPerWeek());
        copy.setWeeksPerSemester(course.getWeeksPerSemester());

        copy.setLecturers(employees);
        copy.setCourseRelationsA(new ArrayList<>());
        copy.setCourseRelationsB(new ArrayList<>());
        return copy;
    }

    /**
     * Creates a copy of the provided course timeslot for a given timetable.
     *
     * @param cts The course timeslot to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @return A copied course timeslot.
     */
    private CourseTimeslot copyCourseTimeslot(CourseTimeslot cts, Timetable timetable) {
        CourseTimeslot copy = new CourseTimeslot();
        copy.setTimeslot(findTimeslotInTT(cts.getTimeslot(), timetable));
        copy.setCourse(findCourseInTT(cts.getCourse(), timetable));
        copy.setWeekday(cts.getWeekday());
        return copy;
    }

    /**
     * Creates a copy of the provided course relation for a given timetable.
     *
     * @param courseRelation The course relation to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @return A copied course relation.
     */
    private CourseRelation copyCourseRelation(CourseRelation courseRelation, Timetable timetable) {
        CourseRelation copy = new CourseRelation();
        copy.setCourseA(findCourseInTT(courseRelation.getCourseA(), timetable));
        copy.setCourseB(findCourseInTT(courseRelation.getCourseB(), timetable));
        copy.setRelationType(courseRelation.getRelationType());
        return copy;
    }

    /**
     * Creates a copy of the provided week event for a given timetable.
     *
     * @param weekEvent The week event to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @param copyTimeslot Indicates whether to copy associated timeslots.
     * @param copyRoom Indicates whether to copy associated rooms.
     * @return A copied week event.
     */
    private WeekEvent copyWeekEvent(
            WeekEvent weekEvent, Timetable timetable, boolean copyTimeslot, boolean copyRoom) {
        WeekEvent copy = new WeekEvent();
        copy.setWeek(weekEvent.getWeek());
        copy.setWeekday(weekEvent.getWeekday());

        copy.setCourse(findCourseInTT(weekEvent.getCourse(), timetable));

        copy.setTimetable(timetable);

        // week_events_timeslots
        List<Timeslot> timeslots = new ArrayList<>();
        if (copyTimeslot) {
            for (Timeslot timeslot : weekEvent.getTimeslots()) {
                timeslots.add(findTimeslotInTT(timeslot, timetable));
            }
        }
        // week_events_rooms
        List<Room> rooms = new ArrayList<>();
        if (copyRoom) {
            for (Room room : weekEvent.getRooms()) {
                rooms.add(findRoomInTT(room, timetable));
            }
        }
        copy.setTimeslots(timeslots);
        copy.setRooms(rooms);
        return copy;
    }

    /**
     * Creates a copy of the provided degree for a given timetable.
     *
     * @param deg The degree to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @return A copied degree.
     */
    private Degree copyDegree(Degree deg, Timetable timetable) {
        Degree copy = new Degree();
        copy.setTimetable(timetable);
        copy.setSchoolType(deg.getSchoolType());
        copy.setSemesters(new ArrayList<>());
        copy.setShortName(deg.getShortName());
        copy.setName(deg.getName());
        copy.setStudyRegulation(deg.getStudyRegulation());
        copy.setSemesterAmount(deg.getSemesterAmount());
        return copy;
    }

    /**
     * Creates a copy of the provided degree semester for a given timetable.
     *
     * @param degS The degree semester to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @param copyDegree Indicates whether to copy the associated degree.
     * @param copyCourse Indicates whether to copy associated courses.
     * @return A copied degree semester.
     */
    private DegreeSemester copyDegreeSemester(
            DegreeSemester degS, Timetable timetable, boolean copyDegree, boolean copyCourse) {
        List<Course> courses = new ArrayList<>();
        if (copyCourse) {
            for (Course course : degS.getCourses()) {
                courses.add(findCourseInTT(course, timetable));
            }
        }
        DegreeSemester copy = new DegreeSemester();
        copy.setTimetable(timetable);
        copy.setAttendees(degS.getAttendees());
        if (copyDegree) {
            copy.setDegree(findDegreeInTT(degS.getDegree(), timetable));
        }
        copy.setSemesterNumber(degS.getSemesterNumber());
        copy.setExtensionName(degS.getExtensionName());
        copy.setCourses(courses);
        return copy;
    }

    /**
     * Creates a copy of the provided room combination for a given timetable.
     *
     * @param roomCombination The room combination to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @return A copied room combination.
     */
    private RoomCombination copyRoomCombination(
            RoomCombination roomCombination, Timetable timetable) {
        RoomCombination copy = new RoomCombination();
        List<Room> rooms = new ArrayList<>();
        for (Room room : roomCombination.getRooms()) {
            rooms.add(findRoomInTT(room, timetable));
        }
        copy.setCourse(findCourseInTT(roomCombination.getCourse(), timetable));
        copy.setRooms(rooms);

        return copy;
    }

    /**
     * Creates a copy of the provided special event for a given timetable.
     *
     * @param specialEvent The special event to be copied.
     * @param timetable The timetable to which the copy belongs.
     * @return A copied special event.
     */
    private SpecialEvent copySpecialEvent(SpecialEvent specialEvent, Timetable timetable) {
        SpecialEvent copy = new SpecialEvent();
        copy.setStartDate(specialEvent.getStartDate());
        copy.setEndDate(specialEvent.getEndDate());
        copy.setSpecialEventType(specialEvent.getSpecialEventType());
        copy.setTimetable(timetable);
        return copy;
    }

    /**
     * Function to find a copy of a room from another timetable For deep-copies
     *
     * @param entity the Room
     * @param tt the timetable
     * @return returns the room entity from the other timetable
     */
    private Room findRoomInTT(Room entity, Timetable tt) {
        List<Room> rooms =
                roomRepository.findByTimetableId(tt.getId()).stream()
                        .filter(r -> r.getAbbreviation().equals(entity.getAbbreviation()))
                        .filter(
                                r ->
                                        (r.getRoomType() == null && entity.getRoomType() == null)
                                                || r.getRoomType() != null
                                                        && r.getRoomType()
                                                                .equals(entity.getRoomType()))
                        .filter(r -> r.getCapacity().equals(entity.getCapacity()))
                        .filter(r -> r.getName().equals(entity.getName()))
                        .filter(r -> r.getIdentifier().equals(entity.getIdentifier()))
                        .toList();
        // Es sollte genau ein Eintrag existieren
        assert (rooms.size() == 1);
        return rooms.get(0);
    }

    /**
     * Finds and returns a timeslot from another timetable based on its attributes. Used for deep
     * copies.
     *
     * @param entity The timeslot to be found.
     * @param tt The target timetable.
     * @return The timeslot entity from the other timetable.
     */
    private Timeslot findTimeslotInTT(Timeslot entity, Timetable tt) {
        List<Timeslot> timeslots =
                timeslotRepository.findByTimetableId(tt.getId()).stream()
                        .filter(r -> r.getIndex().equals(entity.getIndex()))
                        .filter(r -> r.getStartTime().equals(entity.getStartTime()))
                        .filter(r -> r.getEndTime().equals(entity.getEndTime()))
                        .toList();

        assert (timeslots.size() == 1);
        return timeslots.get(0);
    }

    /**
     * Finds and returns an employee from another timetable based on their attributes. Used for deep
     * copies.
     *
     * @param entity The employee to be found.
     * @param tt The target timetable.
     * @return The employee entity from the other timetable.
     */
    private Employee findEmployeeInTT(Employee entity, Timetable tt) {
        List<Employee> employees =
                employeeRepository.findByTimetable(tt).stream()
                        .filter(r -> r.getEmployeeType().equals(entity.getEmployeeType()))
                        .filter(r -> r.getAbbreviation().equals(entity.getAbbreviation()))
                        .filter(r -> r.getLastname().equals(entity.getLastname()))
                        .filter(r -> r.getFirstname().equals(entity.getFirstname()))
                        .toList();

        assert (employees.size() == 1);
        return employees.get(0);
    }

    /**
     * Finds and returns a course from another timetable based on its attributes. Used for deep
     * copies.
     *
     * @param entity The course to be found.
     * @param tt The target timetable.
     * @return The course entity from the other timetable.
     */
    private Course findCourseInTT(Course entity, Timetable tt) {
        List<Course> courses =
                courseRepository.findByTimetable(tt).stream()
                        .filter(r -> r.getAbbreviation().equals(entity.getAbbreviation()))
                        .filter(r -> r.getBlockSize().equals(entity.getBlockSize()))
                        .filter(r -> r.getCasID().equals(entity.getCasID()))
                        .filter(
                                r ->
                                        (r.getCourseType() == null
                                                        && entity.getCourseType() == null)
                                                || r.getCourseType() != null
                                                        && r.getCourseType()
                                                                .equals(entity.getCourseType()))
                        .filter(r -> r.getDescription().equals(entity.getDescription()))
                        .filter(r -> r.getName().equals(entity.getName()))
                        .filter(r -> r.getSlotsPerWeek().equals(entity.getSlotsPerWeek()))
                        .filter(r -> r.getWeeksPerSemester().equals(entity.getWeeksPerSemester()))
                        .toList();

        assert (courses.size() == 1);
        return courses.get(0);
    }

    /**
     * Finds and returns a degree from another timetable based on its attributes. Used for deep
     * copies.
     *
     * @param entity The degree to be found.
     * @param tt The target timetable.
     * @return The degree entity from the other timetable.
     */
    private Degree findDegreeInTT(Degree entity, Timetable tt) {
        List<Degree> degrees =
                degreeRepository.findByTimetable(tt).stream()
                        .filter(r -> r.getName().equals(entity.getName()))
                        .filter(r -> r.getShortName().equals(entity.getShortName()))
                        .filter(
                                r ->
                                        (r.getSchoolType() == null
                                                        && entity.getSchoolType() == null)
                                                || r.getSchoolType() != null
                                                        && r.getSchoolType()
                                                                .equals(entity.getSchoolType()))
                        .filter(r -> r.getSemesterAmount().equals(entity.getSemesterAmount()))
                        .filter(r -> r.getStudyRegulation().equals(entity.getStudyRegulation()))
                        .toList();

        assert (degrees.size() == 1);
        return degrees.get(0);
    }
}
