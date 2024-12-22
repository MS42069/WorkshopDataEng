import { ConflictProblem } from "../../../models/conflict-error";

interface ConflictProblemProps {
  problem: ConflictProblem;
}

const ConflictProblemDisplay = ({ problem }: ConflictProblemProps) => {
  return (
    <>
      {problem.description}
      <br />

      {!!problem.conflictedEmployees.length && <br />}
      {!!problem.conflictedEmployees.length &&
        `Involvierte Mitarbeiter: ${problem.conflictedEmployees
          .map(({ firstname, lastname }) => `${firstname} ${lastname}`)
          .join(", ")}`}

      {!!problem.conflictedRooms.length && <br />}
      {!!problem.conflictedRooms.length &&
        `Involvierte Räume: ${problem.conflictedRooms
          .map(({ name }) => `${name}`)
          .join(", ")}`}

      {!!problem.conflictedSemesters.length && <br />}
      {!!problem.conflictedSemesters.length &&
        `Involvierte Semester: ${problem?.conflictedSemesters
          .map(
            ({ extensionName, degree: { name, studyRegulation } }) =>
              `${name} ${extensionName} ${studyRegulation}`
          )
          .join(", ")}`}
      <br />
    </>
  );
};

export default ConflictProblemDisplay;
