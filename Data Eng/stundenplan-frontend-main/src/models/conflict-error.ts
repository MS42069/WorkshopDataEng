export interface ConflictProblem {
  description: string;
  conflictedEmployees: {
    id: string;
    abbreviation: string;
    firstname: string;
    lastname: string;
  }[];
  conflictedRooms: {
    id: string;
    name: string;
    abbreviation: string;
  }[];
  conflictedSemesters: {
    id: string;
    extensionName: string;
    degree: {
      id: string;
      name: string;
      shortName: string;
      semesterAmount: number;
      schoolType: string;
      studyRegulation: number;
    };
  }[];
}

export interface ConflictError {
  problems: ConflictProblem[];
}
