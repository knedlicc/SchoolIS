package cz.cvut.rsp.help.school.dto.Person;

import cz.cvut.rsp.help.school.model.Person;

public class PersonDto extends AbstractPersonDto{

    public static PersonDto from(Person person) {
        final PersonDto personDto = new PersonDto();
        personDto.setFrom(person);
        return personDto;
    }
}
