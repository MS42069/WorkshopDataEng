import { EmployeeAPI } from "./employee";
import { SubjectSemesterAPI } from "./subjectSemester";

export interface LecturesForPlanFilter {
  employee?: EmployeeAPI | null;
  subject?: SubjectSemesterAPI | null;
}