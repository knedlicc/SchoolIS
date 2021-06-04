package cz.cvut.rsp.help.school.model.subject;

public enum SubjectCompletion {

    CREDIT("CREDIT"),
    EXAM("EXAM"),
    CREDIT_EXAM("CREDIT_EXAM");

    private final String name;

    SubjectCompletion(String name) {
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
