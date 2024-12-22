package com.fhwedel.softwareprojekt.v1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employee_app_availabilities")
@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeAppAvailability {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @NotNull
    LocalDateTime startDate;

    @Column
    @NotNull
    LocalDateTime endDate;
}
