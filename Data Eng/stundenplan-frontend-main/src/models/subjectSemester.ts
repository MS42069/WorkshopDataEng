// Also referred to as Semester
export type SubjectSemester = {
  // UUID - Value
  id: string;
  name: string;
  semester: number;
  subjectId: string;
  courseIds: string[];
};

export type SubjectSemesterInternal = {
  id: string;
  semesterNumber: number;
  extensionName: string;
  attendees: number;
  degree: string;
  courses: string[];
  new?: boolean;
  edited?: boolean;
};

export type SubjectSemesterSort = Pick<
  SubjectSemesterInternal,
  "degree" | "extensionName" | "semesterNumber"
>;

export type SubjectSemesterAPI = {
  id: string;
  semesterNumber: number;
  extensionName: string;
  attendees: number;
  degree: {
    id: string;
    name: string;
    shortName: string;
    semesterAmount: number;
    schoolType: string;
    studyRegulation: string;
  };
  courses: [
    {
      id: string;
      casID: string;
      name: string;
      abbreviation: string;
      description: string;
      blockSize: number;
      slotsPerWeek: number;
      weeksPerSemester: number;
      courseType: string;
    }
  ];
};
