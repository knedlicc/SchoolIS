package cz.cvut.rsp.help.school.dao;

import cz.cvut.rsp.help.school.dao.AbstractDao;
import cz.cvut.rsp.help.school.model.semester.Mark;

import org.springframework.stereotype.Repository;

@Repository
public class MarkDao extends AbstractDao<Mark> {

    public MarkDao() {
        super(Mark.class);
    }
}
