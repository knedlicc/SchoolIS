package cz.cvut.rsp.help.school.model.semester;

public enum Credit {

    UNKNOWN("UNKNOWN"),
    CREDIT("CREDIT"),
    NO_CREDIT("NO_CREDIT");

    private final String name;

    Credit(String name) {
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
