package com.pm.patientservice.contoller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient" , description = "API for managing patients")
public class PatientController
{
    @Autowired
    private  PatientService patientService;

    @GetMapping
    @Operation(summary = "get patients")
   public ResponseEntity<List<PatientResponseDTO>> getPatients()
    {
        List<PatientResponseDTO> patient = patientService.getPatients();
        return ResponseEntity.ok(patient);

    }

    @PostMapping
    @Operation(summary = "create a new patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class })@RequestBody PatientRequestDTO patientRequestDTO)
    {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a patient")
    public ResponseEntity<PatientResponseDTO> updatePatient( @PathVariable UUID id,@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO)
    {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id,patientRequestDTO);
        return ResponseEntity.ok(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a patient")
    public ResponseEntity<PatientResponseDTO> deletePatient(@PathVariable UUID id)
    {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
