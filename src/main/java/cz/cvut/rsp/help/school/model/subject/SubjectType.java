package cz.cvut.rsp.help.school.model.subject;

public enum SubjectType {

    OPTIONAL("OPTIONAL"),
    MANDATORY("MANDATORY"),
    COMPULSORY("COMPULSORY");

    private final String name;

    SubjectType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
