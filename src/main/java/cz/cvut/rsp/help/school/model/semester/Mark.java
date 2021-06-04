package cz.cvut.rsp.help.school.model.semester;

public enum Mark {

    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    NO_MARK("NO_MARK");

    private final String name;

    Mark(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
