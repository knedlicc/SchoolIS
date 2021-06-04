package cz.cvut.rsp.help.school.service;

import cz.cvut.rsp.help.school.dao.SemesterDao;
import cz.cvut.rsp.help.school.exception.EntityNotFoundException;
import cz.cvut.rsp.help.school.model.School;
import cz.cvut.rsp.help.school.model.semester.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SemesterService {

    private final SemesterDao dao;

    @Autowired
    public SemesterService(SemesterDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Semester findLastSemesterInSchool(School school) {
        Long schoolId = school.getId();
        Long semesterId = this.dao.findLastIdInSchool(schoolId)
                .orElseThrow(() -> new EntityNotFoundException(Long.class, "school_id", schoolId));
        return this.dao.find(semesterId)
                .orElseThrow(() -> new EntityNotFoundException(Semester.class, "id", semesterId));

    }
    @Transactional(readOnly = true)
    public Optional<Semester> find(Long id) {
        return dao.find(id);
    }
}
