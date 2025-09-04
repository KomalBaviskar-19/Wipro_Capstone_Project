package com.wipro.komal.appointment.repo;
import com.wipro.komal.appointment.entity.Appointment; import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
  List<Appointment> findByDoctorId(Long doctorId);
  List<Appointment> findByPatientId(Long patientId);
}
