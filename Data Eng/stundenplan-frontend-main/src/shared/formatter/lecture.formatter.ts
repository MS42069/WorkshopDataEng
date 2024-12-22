import { LectureForPlanAPI } from "../../models/lecture";

export const unplannedListFormat = (course: LectureForPlanAPI | undefined) => {
  if (!course || typeof course.courseType === "string") {
    return "";
  }

  return `${course.name} (${course.courseType.name})`;
};
