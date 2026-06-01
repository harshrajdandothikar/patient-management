package com.pm.patientservice.kafka;

import com.pm.patientservice.model.Patient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.event.PatientEvent;

import static com.pm.patientservice.exception.GlobalExceptionHandler.log;

@Service
public class kafkaProducer
{
    private final KafkaTemplate<String,byte[]> kafkaTemplate;

    public kafkaProducer(KafkaTemplate<String,byte[]> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient)
    {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT CREATED")
                .build();

        try
        {
            kafkaTemplate.send("patient", event.toByteArray());
        }
        catch (Exception e)
        {
            log.error("Error sending PatientCreated event to kafka:{} ",event);
            e.printStackTrace();
        }
    }
}