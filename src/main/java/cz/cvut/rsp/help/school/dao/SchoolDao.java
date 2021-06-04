package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.model.School;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolDao extends AbstractDao<School> {

    public SchoolDao() {
        super(School.class);
    }

}
