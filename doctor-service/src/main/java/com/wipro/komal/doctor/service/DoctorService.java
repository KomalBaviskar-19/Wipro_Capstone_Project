package com.wipro.komal.doctor.service;
import com.wipro.komal.doctor.entity.Doctor;
import org.springframework.data.domain.Page;
import java.util.List;
public interface DoctorService {
  Doctor addDoctor(Doctor d);
  Page<Doctor> list(int page,int size);
  Doctor get(Long id);
  Doctor update(Long id, Doctor d);
  void delete(Long id);
  List<Doctor> findByAvailability(String status);
}
