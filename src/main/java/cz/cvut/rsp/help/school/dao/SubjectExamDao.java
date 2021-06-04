package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.model.semester.SubjectExam;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectExamDao extends AbstractDao<SubjectExam>{

    protected SubjectExamDao() {
        super(SubjectExam.class);
    }

}
