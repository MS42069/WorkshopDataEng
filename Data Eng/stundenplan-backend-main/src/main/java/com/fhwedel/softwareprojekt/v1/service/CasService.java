package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.errorHandling.APIError;
import com.fhwedel.softwareprojekt.v1.model.*;
import com.fhwedel.softwareprojekt.v1.repository.WeekEventRepository;
import java.io.File;
import java.net.MalformedURLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A service for managing CAS (Campus Management System) data export. This service provides
 * functionality to export data to CAS XML format.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CasService {

    /** The path for saving the CAS XML file. */
    private static final String xmlSavePath = "src/main/resources/cas.xml";

    /** Repository for WeekEvent objects. */
    private final WeekEventRepository weekEventRepository;

    /** Service for Timetable operations. */
    private final TimetableService timetableService;

    /** Service for Course operations. */
    private final CourseService courseService;

    /**
     * Export the timetable data to a CAS XML file.
     *
     * @param timetableId The ID of the timetable to export.
     * @return A Resource representing the exported CAS XML file.
     */
    public Resource exportToCasXML(UUID timetableId) {
        Timetable timetable = timetableService.findByID(timetableId);
        List<Course> courses = courseService.findAllCourses(timetableId);
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("gwconnect");
            doc.appendChild(rootElement);

            // supercars element
            Element dataElement = doc.createElement("data");
            rootElement.appendChild(dataElement);

            for (Course course : courses) {
                Element courseStartElement = doc.createElement("element");
                dataElement.appendChild(courseStartElement);
                // setting attribute to element
                Attr attr = doc.createAttribute("table");
                attr.setValue("rwthevent");
                courseStartElement.setAttributeNode(attr);

                Element courseIdElement = doc.createElement("field");
                courseStartElement.appendChild(courseIdElement);
                attr = doc.createAttribute("name");
                attr.setValue("rwev_coursenumber");
                courseIdElement.setAttributeNode(attr);
                attr = doc.createAttribute("value");
                attr.setValue(course.getCasID());
                courseIdElement.setAttributeNode(attr);
                attr = doc.createAttribute("key");
                attr.setValue("1");
                courseIdElement.setAttributeNode(attr);

                Map<Integer, Map<DayOfWeek, List<WeekEvent>>> weekEvents = this.computeWeekEvents(timetable, course);

                for (Integer week : weekEvents.keySet()) {
                    for (DayOfWeek dow : weekEvents.get(week).keySet()) {
                        for (WeekEvent event : weekEvents.get(week).get(dow)) {
                            // This must use `week` and `dow` instead of the `event` ones, since this might be a {DayOfWeek}_PLAN day
                            LocalDate date = timetable.getStartDate().plusWeeks(week - 1).plusDays(
                                dow.ordinal() - timetable.getStartDate().getDayOfWeek().ordinal()
                            );

                            for (Room room : event.getRooms()) {
                                Element appointmentElement = doc.createElement("element");
                                courseStartElement.appendChild(appointmentElement);
                                attr = doc.createAttribute("table");
                                attr.setValue("Appointment");
                                appointmentElement.setAttributeNode(attr);

                                Element keywordElement = doc.createElement("field");
                                appointmentElement.appendChild(keywordElement);
                                attr = doc.createAttribute("name");
                                attr.setValue("keyword");
                                keywordElement.setAttributeNode(attr);
                                attr = doc.createAttribute("value");
                                attr.setValue(course.getName());
                                keywordElement.setAttributeNode(attr);

                                Element startDateElement = doc.createElement("field");
                                appointmentElement.appendChild(startDateElement);
                                attr = doc.createAttribute("name");
                                attr.setValue("start_dt");
                                startDateElement.setAttributeNode(attr);
                                attr = doc.createAttribute("value");
                                attr.setValue(
                                        date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                                                + " "
                                                + getLowestTime(event.getTimeslots()).toString());
                                startDateElement.setAttributeNode(attr);
                                attr = doc.createAttribute("key");
                                attr.setValue("1");
                                startDateElement.setAttributeNode(attr);

                                Element endDateElement = doc.createElement("field");
                                appointmentElement.appendChild(endDateElement);
                                attr = doc.createAttribute("name");
                                attr.setValue("end_dt");
                                endDateElement.setAttributeNode(attr);
                                attr = doc.createAttribute("value");
                                attr.setValue(
                                        date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                                                + " "
                                                + getHighestTime(event.getTimeslots()).toString());
                                endDateElement.setAttributeNode(attr);
                                attr = doc.createAttribute("key");
                                attr.setValue("1");
                                endDateElement.setAttributeNode(attr);

                                Element roomElement = doc.createElement("field");
                                appointmentElement.appendChild(roomElement);
                                attr = doc.createAttribute("name");
                                attr.setValue("rwth_room");
                                roomElement.setAttributeNode(attr);
                                attr = doc.createAttribute("value");
                                attr.setValue(room.getIdentifier());
                                roomElement.setAttributeNode(attr);
                                attr = doc.createAttribute("key");
                                attr.setValue("1");
                                roomElement.setAttributeNode(attr);

                                Element note1Element = doc.createElement("field");
                                appointmentElement.appendChild(note1Element);
                                attr = doc.createAttribute("name");
                                attr.setValue("notes");
                                note1Element.setAttributeNode(attr);
                                attr = doc.createAttribute("value");
                                attr.setValue("Angelegt vom Import");
                                note1Element.setAttributeNode(attr);

                                Element note2Element = doc.createElement("field");
                                appointmentElement.appendChild(note2Element);
                                attr = doc.createAttribute("name");
                                attr.setValue("notes2");
                                note2Element.setAttributeNode(attr);
                                attr = doc.createAttribute("value");
                                attr.setValue("");
                                note2Element.setAttributeNode(attr);

                                Element foreignaccessElement = doc.createElement("foreignaccess");
                                appointmentElement.appendChild(foreignaccessElement);
                                attr = doc.createAttribute("right");
                                attr.setValue("65535");
                                foreignaccessElement.setAttributeNode(attr);

                                Element accessRoomElement = doc.createElement("access");
                                appointmentElement.appendChild(accessRoomElement);
                                attr = doc.createAttribute("name");
                                attr.setValue(room.getIdentifier());
                                accessRoomElement.setAttributeNode(attr);
                                attr = doc.createAttribute("type");
                                attr.setValue("resource");
                                accessRoomElement.setAttributeNode(attr);
                                attr = doc.createAttribute("right");
                                attr.setValue("1");
                                accessRoomElement.setAttributeNode(attr);

                                Element accessUserElement = doc.createElement("access");
                                appointmentElement.appendChild(accessUserElement);
                                attr = doc.createAttribute("name");
                                attr.setValue("va");
                                accessUserElement.setAttributeNode(attr);
                                attr = doc.createAttribute("type");
                                attr.setValue("user");
                                accessUserElement.setAttributeNode(attr);
                                attr = doc.createAttribute("right");
                                attr.setValue("65535");
                                accessUserElement.setAttributeNode(attr);
                            }
                        }
                    }
                }
            }

            doc.setXmlStandalone(true);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
            DOMSource source = new DOMSource(doc);
            File f = new File(xmlSavePath);
            StreamResult result = new StreamResult(f);
            transformer.transform(source, result);
            return new UrlResource(f.toURI());
        } catch (TransformerException | ParserConfigurationException | MalformedURLException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format(APIError.INTERNAL_SERVER_ERROR.getMessage()));
        }
    }

    /**
     * Computes the effective (read: special events handled) week events for a given course in a given timetable
     * @remark Prefer using the index of the outer (week) map and the inner (day) over what the events report as special events may shift them around
     */
    private Map<Integer, Map<DayOfWeek, List<WeekEvent>>> computeWeekEvents(Timetable timetable, Course course) {
        // Keep an immutable track of all events so that multiple special events referencing the same day will behave as expected
        // e.g. Thursday being a day off and Monday being the Thursday -> Monday is _not_ a day off but what the Thursday would have been
        Map<Integer, Map<DayOfWeek, List<WeekEvent>>> weekEventsOrig = weekEventRepository.findByCourse(course)
            .stream()
            // Group all events by their week
            .collect(Collectors.groupingBy(WeekEvent::getWeek))
            .entrySet().stream()
            // Group the list of WeekEvents by their DayOfWeek
            .map(entry -> Map.entry(
                entry.getKey(),
                entry.getValue().stream().collect(Collectors.groupingBy(WeekEvent::getWeekday, Collectors.toUnmodifiableList()))
            ))
            .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

        // This collection will be mutated by special events
        Map<Integer, Map<DayOfWeek, List<WeekEvent>>> weekEvents = weekEventsOrig.entrySet().stream()
            .map(entry -> Map.entry(entry.getKey(), new HashMap<>(entry.getValue())))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        final long startDateEpochDay = timetable.getStartDate().toEpochDay();
        for (SpecialEvent specialEvent : timetable.getSpecialEvents()) {
            for (LocalDate specialDate : specialEvent.getStartDate().datesUntil(specialEvent.getEndDate().plusDays(1)).toList()) {
                int diff = (int)(specialDate.toEpochDay() - startDateEpochDay);
                int week = diff / 7 + 1;
                DayOfWeek day = DayOfWeek.of(diff % 7 + 1);

                // Week of special even is not planned at all (potential planner (human) error)
                if (!weekEvents.containsKey(week)) {
                    continue;
                }

                switch (specialEvent.getSpecialEventType()) {
                    case MONDAY_PLAN: {
                        weekEvents
                            .get(week)
                            .put(day,
                                weekEventsOrig.get(week).get(DayOfWeek.MONDAY)
                            );
                        break;
                    }
                    case TUESDAY_PLAN: {
                        weekEvents
                            .get(week)
                            .put(day,
                                weekEventsOrig.get(week).get(DayOfWeek.TUESDAY)
                            );
                        break;
                    }
                    case WEDNESDAY_PLAN: {
                        weekEvents
                            .get(week)
                            .put(day,
                                weekEventsOrig.get(week).get(DayOfWeek.WEDNESDAY)
                            );
                        break;
                    }
                    case THURSDAY_PLAN: {
                        weekEvents
                            .get(week)
                            .put(day,
                                weekEventsOrig.get(week).get(DayOfWeek.THURSDAY)
                            );
                        break;
                    }
                    case FRIDAY_PLAN: {
                        weekEvents
                            .get(week)
                            .put(day,
                                weekEventsOrig.get(week).get(DayOfWeek.FRIDAY)
                            );
                        break;
                    }
                    case SATURDAY_PLAN: {
                        weekEvents
                            .get(week)
                            .put(day,
                                weekEventsOrig.get(week).get(DayOfWeek.SATURDAY)
                            );
                        break;
                    }
                    case SUNDAY_PLAN: {
                        weekEvents
                            .get(week)
                            .put(day,
                                weekEventsOrig.get(week).get(DayOfWeek.SUNDAY)
                            );
                        break;
                    }
                    case FREE: {
                        weekEvents.get(week).remove(day);
                        break;
                    }
                }
            }
        }

        return weekEvents;
    }

    /**
     * Gets the Lowest Start Time of all given Timeslots
     *
     * @param timeslots the timeslots
     * @return the lowest start time for the start of an appointment
     */
    private LocalTime getLowestTime(List<Timeslot> timeslots) {
        LocalTime lowest = null;
        for (Timeslot timeslot : timeslots) {
            if (lowest == null || timeslot.getStartTime().isBefore(lowest)) {
                lowest = timeslot.getStartTime();
            }
        }
        return lowest;
    }

    /**
     * Gets the Highest End Time of all given Timeslots
     *
     * @param timeslots the timeslots
     * @return the highest end time for the end of an appointment
     */
    private LocalTime getHighestTime(List<Timeslot> timeslots) {
        LocalTime lowest = null;
        for (Timeslot timeslot : timeslots) {
            if (lowest == null || timeslot.getEndTime().isAfter(lowest)) {
                lowest = timeslot.getEndTime();
            }
        }
        return lowest;
    }
}
