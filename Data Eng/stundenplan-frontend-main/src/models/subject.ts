export interface SubjectShort {
  // UUID - Value
  id: string;
  abbreviation: string;
  name: string;
  regulation: string;
}

export type SubjectDetail = {
  semester: number;
  schoolType: SchoolType;
} & SubjectShort;

export type SubjectAPI = {
  id: string;
  name: string;
  shortName: string;
  semesterAmount: number;
  schoolType: string | SchoolType;
  studyRegulation: string;
  semesters: [
    {
      id: string;
      semesterNumber: number;
      extensionName: string;
      attendees: number;
    }
  ];
};

export type SchoolType = {
  id: string;
  name: string;
};

export type SubjectSort = Pick<SubjectAPI, "name" | "studyRegulation">;

export type SubjectCreateDetail = Partial<SubjectDetail>;

export type SubjectShortKey = keyof SubjectShort;
export type SubjectDetailKey = keyof SubjectDetail;
