package cz.cvut.rsp.help.school.dto.semester;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SemesterSubjectStudentDto {

    @JsonProperty("semester_subject_id")
    private Long semesterSubjectId;

    @JsonProperty("student_id")
    private Long studentId;

    private String mark;

}
