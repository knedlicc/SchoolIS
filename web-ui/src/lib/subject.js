/**
 * @typedef {Object} Subject
 * @property {number} id
 * @property {number} school
 * @property {SubjectTutor[]} tutors
 * @property {SubjectAssignment[]} assignments
 * @property {string} code
 * @property {string} name
 * @property {number} credits
 * @property {SubjectType} type
 * @property {string|undefined} typeText
 * @property {SubjectCompletion} completion
 * @property {string|undefined} completionText
 */

/**
 * @typedef {string} SubjectType
 * @readonly
 * @enum {SubjectType}
 */
export const TYPES = {
  OPTIONAL: "Volitelný",
  MANDATORY: "Povinně volitelný",
  COMPULSORY: "Povinný",
};
Object.freeze(TYPES);

/**
 * @param {Subject} subject
 * @returns {Subject}
 */
export function typeToText(subject) {
  subject.typeText = TYPES[subject.type];
  return subject;
}

/**
 * @typedef {string} SubjectCompletion
 * @readonly
 * @enum {SubjectCompletion}
 */
export const COMPLETION = {
  CREDIT: "Zápočet",
  EXAM: "Zkouška",
  CREDIT_EXAM: "Zápočet + Zkouška",
};
Object.freeze(COMPLETION);

/**
 * @param {Subject} subject
 * @returns {Subject}
 */
export function completionToText(subject) {
  subject.completionText = COMPLETION[subject.completion];
  return subject;
}
