package com.hospital.hospital_management.service;

import com.hospital.hospital_management.model.Appointment;
import com.hospital.hospital_management.model.AppointmentStatus;
import com.hospital.hospital_management.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void bookAppointment_shouldSetStatusToPendingAndSave() {
        Appointment appointment = new Appointment();
        appointment.setNotes("Fever and cold");

        when(appointmentRepository.save(any(Appointment.class)))
                .thenAnswer(invocation -> {
                    Appointment saved = invocation.getArgument(0);
                    saved.setId(1L);
                    return saved;
                });

        Appointment result = appointmentService.bookAppointment(appointment);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(AppointmentStatus.PENDING, result.getStatus());
        assertEquals("Fever and cold", result.getNotes());

        verify(appointmentRepository).save(appointment);
    }

    @Test
    void getAppointmentsByPatient_shouldReturnList() {
        when(appointmentRepository.findByPatientId(10L))
                .thenReturn(List.of(new Appointment(), new Appointment()));

        List<Appointment> result = appointmentService.getAppointmentsByPatient(10L);

        assertEquals(2, result.size());
        verify(appointmentRepository).findByPatientId(10L);
    }

    @Test
    void updateAppointment_shouldUpdateStatusAndNotes() {
        Appointment existing = new Appointment();
        existing.setId(1L);
        existing.setStatus(AppointmentStatus.PENDING);
        existing.setNotes("Old notes");

        Appointment updated = new Appointment();
        updated.setStatus(AppointmentStatus.CONFIRMED);
        updated.setNotes("Patient confirmed");

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(existing);

        Appointment result = appointmentService.updateAppointment(1L, updated);

        assertEquals(AppointmentStatus.CONFIRMED, result.getStatus());
        assertEquals("Patient confirmed", result.getNotes());
    }

    @Test
    void updateAppointment_shouldThrowExceptionWhenNotFound() {
        when(appointmentRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.updateAppointment(99L, new Appointment());
        });

        assertEquals("Appointment not found", exception.getMessage());
    }
}
