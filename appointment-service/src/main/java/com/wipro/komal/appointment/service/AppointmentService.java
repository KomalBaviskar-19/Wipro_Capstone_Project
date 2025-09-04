package com.wipro.komal.appointment.service;
import com.wipro.komal.appointment.entity.Appointment;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Map;

public interface AppointmentService {
  Appointment create(Appointment appt);
  Page<Appointment> list(int page,int size);
  Appointment get(Long id);
  Appointment update(Long id, Appointment appt);
  void delete(Long id);
  Map<String,Object> getDetails(Long id);
  List<Appointment> byDoctor(Long doctorId);
  List<Appointment> byPatient(Long patientId);
}
