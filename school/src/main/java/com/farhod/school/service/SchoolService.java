package com.farhod.school.service;

import com.farhod.school.config.StudentClient;
import com.farhod.school.dto.FullSchoolResponse;
import com.farhod.school.models.School;
import com.farhod.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository repository;
    private final StudentClient client;

    public void saveSchool(School school) {
        repository.save(school);
    }

    public List<School> findAllSchools() {
        return repository.findAll();
    }

    public FullSchoolResponse findSchoolsWithStudents(Integer schoolId) {
        var school =repository.findById(schoolId)
                .orElse(
                    School.builder()
                            .name("NOT FOUND")
                            .email("NOT FOUND")
                            .build()
                );
        var students = client.findAllStudentsBySchool(schoolId);
        return FullSchoolResponse.builder()
                .email(school.getEmail())
                .name(school.getName())
                .students(students)
                .build();
    }
}
