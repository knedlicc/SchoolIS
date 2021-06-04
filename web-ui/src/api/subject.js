import { completionToText, typeToText } from "../lib/subject";
import { afetch } from "../utils/fetch";

/**
 * @returns {Promise<import("../lib/subject").Subject[]>}
 */
export function listSubjects() {
  return afetch("API_ENDPOINT/v1/subjects")
    .then((res) => res.json())
    .catch(() => [])
    .then((subjects) =>
      subjects.map((s) => {
        typeToText(s);
        completionToText(s);
        return s;
      })
    );
}

/**
 * @param {number} id
 * @returns {Promise<import("../lib/subject").Subject>}
 */
export function getSubject(id) {
  return afetch("API_ENDPOINT/v1/subjects/" + id)
    .then((res) => res.json())
    .then((s) => {
      typeToText(s);
      completionToText(s);
      return s;
    });
}

/**
 * @returns {Promise<import("../lib/subject").Subject[]>}
 */
export function listSemesterSubjects() {
  return afetch("API_ENDPOINT/v1/subjects/semester")
    .then((res) => res.json())
    .then((subjects) =>
      subjects.map((s) => {
        typeToText(s);
        completionToText(s);
        return s;
      })
    );
}

/**
 * @param {number} id Subject id
 * @returns {Promise<Response>}
 */
export function enrollIntoSubject(id) {
  return afetch("API_ENDPOINT/v1/subjects/" + id + "/enroll");
}

export function removeSemesterStudentFromSemesterSubject(id) {
  return afetch("API_ENDPOINT/v1/semester/subjects/" + id, {
    method: "DELETE",
  });
}

/**
 * @param {number} id
 * @returns {Promise<any>}
 */
export function listStudentsForSubject(id) {
  return afetch("API_ENDPOINT/v1/subjects/" + id + "/students")
    .then((res) => res.json())
    .catch((e) => {
      console.error(e);
      return [];
    });
}

/**
 * @returns {Promise<any>}
 */
export function listAllStudents() {
  return afetch("API_ENDPOINT/v1/users/students")
    .then((res) => res.json())
    .catch((e) => {
      console.error(e);
      return [];
    });
}

/**
 * @returns {Promise<any>}
 */
export function addMarkToStudent(semesterSubjectId, studentId, mark) {
  return afetch("API_ENDPOINT/v1/semester_subject_student", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      semester_subject_id: semesterSubjectId,
      student_id: studentId,
      mark,
    }),
  }).then((res) => res.json());
}

export function addSemesterStudentToSemesterSubject(
  semesterSubjectId,
  studentId
) {
  return afetch(
    "API_ENDPOINT/v1/semester/subjects/" + semesterSubjectId + "/" + studentId,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
}

export function removeSemesterStudentFromSemesterSubjectByEmployee(
  semesterSubjectId,
  studentId
) {
  return afetch(
    "API_ENDPOINT/v1/semester/subjects/" + semesterSubjectId + "/" + studentId,
    {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
}
