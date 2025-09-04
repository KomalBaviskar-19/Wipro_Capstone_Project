package com.wipro.komal.doctor.repo;
import com.wipro.komal.doctor.entity.Doctor;
import com.wipro.komal.doctor.entity.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
  List<Doctor> findByAvailabilityStatus(AvailabilityStatus status);
}
