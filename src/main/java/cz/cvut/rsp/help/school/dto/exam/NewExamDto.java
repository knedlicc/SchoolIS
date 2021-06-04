package cz.cvut.rsp.help.school.dto.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import cz.cvut.rsp.help.school.model.semester.SubjectExam;

import java.time.LocalDateTime;

public class NewExamDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    private int capacity;

    private String note;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public static NewExamDto from(SubjectExam subjectExam) {
        NewExamDto newExamDto = new NewExamDto();
        newExamDto.capacity = subjectExam.getCapacity();
        newExamDto.localDateTime = subjectExam.getLocalDateTime();
        newExamDto.note = subjectExam.getNote();
        return newExamDto;
    }
}
