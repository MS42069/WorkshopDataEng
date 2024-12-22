package com.fhwedel.softwareprojekt.v1.controller.timetable;

import com.fhwedel.softwareprojekt.v1.service.CasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller class for CAS functionality related to timetables. */
@RestController
@RequestMapping("/v1/timetable/{timetableId}/cas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "cas", description = "Everything about cas functionality")
public class CasController {

    /** The CAS service for exporting timetables to XML format. */
    private final CasService casService;

    @Operation(
            summary = "Export the current timetable to XML",
            description = "Returns a String containing the XML data to import the timetable in CAS")
    @Parameter(name = "timetableId", description = "ID of the timetable to return the XML for")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successful fetch"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Timetable not found",
                        content = @Content)
            })
    @GetMapping(path = "/export")
    public ResponseEntity<?> exportToCas(@PathVariable("timetableId") UUID id) {
        Resource resource = casService.exportToCasXML(id);
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
        log.info("Timetable {} exported to XML-File {}", id, resource.getFilename());
        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM.toString()))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
