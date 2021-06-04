package cz.cvut.rsp.help.school.dto;

import cz.cvut.rsp.help.school.model.EntityInterface;

public abstract class BaseDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFrom(EntityInterface enitty) {
        this.setId(enitty.getId());
    }

}
