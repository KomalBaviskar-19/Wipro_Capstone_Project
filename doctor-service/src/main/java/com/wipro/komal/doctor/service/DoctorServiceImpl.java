package com.wipro.komal.doctor.service;
import com.wipro.komal.doctor.entity.Doctor;
import com.wipro.komal.doctor.entity.enums.AvailabilityStatus;
import com.wipro.komal.doctor.exception.DoctorNotFoundException;
import com.wipro.komal.doctor.repo.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
  private final DoctorRepository repo;

  @Override public Doctor addDoctor(Doctor d){ return repo.save(d); }
  @Override public Page<Doctor> list(int page,int size){ return repo.findAll(PageRequest.of(page,size)); }
  @Override public Doctor get(Long id){ return repo.findById(id).orElseThrow(()->new DoctorNotFoundException("Doctor "+id+" not found")); }
  @Override public Doctor update(Long id, Doctor d){ Doctor cur=get(id); d.setId(cur.getId()); return repo.save(d); }
  @Override public void delete(Long id){ Doctor cur=get(id); repo.delete(cur); }
  @Override public List<Doctor> findByAvailability(String status){ AvailabilityStatus s; try{s=AvailabilityStatus.valueOf(status.toUpperCase());}catch(Exception e){ throw new IllegalArgumentException("Invalid status"); } return repo.findByAvailabilityStatus(s); }
}
