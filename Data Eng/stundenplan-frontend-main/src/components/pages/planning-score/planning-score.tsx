import { fetchScore } from "../../../shared/requests/score.requests";
import { useEffect, useRef, useState } from "react";
import PageLayout from "../../../shared/components/page-layout";
import {
  DegreeScore,
  DegreeSemesterScore,
  DegreeSemesterWeekScore,
  EmployeeEvaluation,
  ScoreAPI,
} from "../../../models/score";
import "./planning-score.css";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  LinearProgress,
  Typography,
} from "@mui/material";
import { fetchTimeslots } from "../../../shared/requests/timeslot";
import { TimeslotAPI } from "../../../models/timeslots";
import { getWeekdayTextOfString } from "../../../enums/weekday.enum";
import Spinner from "../../../shared/components/spinner";

type ScoreElement = {
  name: string;
  score: number | undefined;
};

type ScoreWeightElement = {
  name: string;
  score: number | undefined;
  weight: number;
  desc: string;
};

function DegreeScoreComponent(score: DegreeScore) {
  return (
    <Accordion sx={{ backgroundColor: "#DCDCDC" }}>
      <AccordionSummary>
        <Typography sx={{ width: "100%" }}>
          <ScoreBarComponent
            score={score.totalDegreeScore}
            name={score.degreeName + " " + score.degreeRegulation}
          />
        </Typography>
      </AccordionSummary>
      <AccordionDetails>
        {score.degreeSemesterScores.map((degreeSemesterScore) => {
          return DegreeSemesterScoreComponent(degreeSemesterScore);
        })}
      </AccordionDetails>
    </Accordion>
  );
}

function DegreeSemesterScoreWeightComponent(score: ScoreWeightElement) {
  return (
    <Accordion sx={{ backgroundColor: "#C8C8C8" }}>
      <AccordionSummary>
        <Typography sx={{ width: "100%" }}>
          <ScoreBarComponent
            score={score.score}
            name={score.name}
          ></ScoreBarComponent>
        </Typography>
      </AccordionSummary>
      <AccordionDetails>
        <WeightBarComponent
          name={score.desc}
          score={score.weight}
        ></WeightBarComponent>
      </AccordionDetails>
    </Accordion>
  );
}

function DegreeSemesterScoreComponent(score: DegreeSemesterScore) {
  return (
    <Accordion sx={{ backgroundColor: "#D0D0D0" }}>
      <AccordionSummary>
        <Typography sx={{ width: "100%" }}>
          <ScoreBarComponent
            score={score.totalScore}
            name={"Semester " + score.semesterNumber}
          ></ScoreBarComponent>
        </Typography>
      </AccordionSummary>
      <AccordionDetails
        sx={{ display: "flex", flexDirection: "column", gap: "5px" }}
      >
        {score.weeks.map((week) => {
          return DegreeSemesterWeekScoreCompontent(week);
        })}
      </AccordionDetails>
    </Accordion>
  );
}

function DegreeSemesterWeekScoreCompontent(score: DegreeSemesterWeekScore) {
  return (
    <Accordion sx={{ backgroundColor: "#D0D0D0" }}>
      <AccordionSummary>
        <Typography sx={{ width: "100%" }}>
          <ScoreBarComponent
            score={score.totalScore.score}
            name={"Woche " + score.week}
          ></ScoreBarComponent>
        </Typography>
      </AccordionSummary>
      <AccordionDetails
        sx={{ display: "flex", flexDirection: "column", gap: "5px" }}
      ></AccordionDetails>
      <DegreeSemesterScoreWeightComponent
        score={score.freeDayScore.score}
        name={"Freie Tage in der Woche"}
        weight={Math.round(
          (score.freeDayScore.weight / score.totalScore.weight) * 100
        )}
        desc={score.freeDayScore.desc}
      ></DegreeSemesterScoreWeightComponent>
      <DegreeSemesterScoreWeightComponent
        score={score.shortDayScore.score}
        name={"Tage mit nur einer Veranstaltung"}
        weight={Math.round(
          (score.shortDayScore.score / score.totalScore.score) * 100
        )}
        desc={score.shortDayScore.desc}
      ></DegreeSemesterScoreWeightComponent>
      <DegreeSemesterScoreWeightComponent
        score={score.onlineToOfflineConnectionScore.score}
        name={"Direkte Online zu Offine Verbindungen"}
        weight={Math.round(
          (score.onlineToOfflineConnectionScore.weight /
            score.totalScore.weight) *
            100
        )}
        desc={score.onlineToOfflineConnectionScore.desc}
      ></DegreeSemesterScoreWeightComponent>
      <DegreeSemesterScoreWeightComponent
        score={score.lastTimeslotScore.score}
        name={"Veranstaltung im letzen Zeitslot"}
        weight={Math.round(
          (score.lastTimeslotScore.weight / score.totalScore.weight) * 100
        )}
        desc={score.lastTimeslotScore.desc}
      ></DegreeSemesterScoreWeightComponent>
      <DegreeSemesterScoreWeightComponent
        score={score.firstTimeslotScore.score}
        name={"Veranstaltung im ersten Zeitslot"}
        weight={Math.round(
          (score.firstTimeslotScore.weight / score.totalScore.weight) * 100
        )}
        desc={score.firstTimeslotScore.desc}
      ></DegreeSemesterScoreWeightComponent>
      <DegreeSemesterScoreWeightComponent
        score={score.longGapsScore.score}
        name={"Lange Pausen zwischen Veranstaltungen"}
        weight={Math.round(
          (score.longGapsScore.weight / score.totalScore.weight) * 100
        )}
        desc={score.longGapsScore.desc}
      ></DegreeSemesterScoreWeightComponent>
    </Accordion>
  );
}

function EmployeeSemesterScoreComponent(
  employeeScore: EmployeeEvaluation,
  index: number
) {
  const weeks = [
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
    22, 23, 24,
  ];
  return (
    <Accordion key={index} sx={{ backgroundColor: "#DCDCDC" }}>
      <AccordionSummary>
        <Typography sx={{ width: "100%" }}>
          <ScoreBarComponent
            score={employeeScore.score}
            name={employeeScore.abbreviation}
          ></ScoreBarComponent>
        </Typography>
      </AccordionSummary>
      <AccordionDetails>
        {employeeScore.timeslotEvaluationHard.length > 0 ? (
          <Accordion
            key={index + "timeslotHard"}
            sx={{ backgroundColor: "#F0F0F0" }}
          >
            <AccordionSummary>
              <Typography sx={{ width: "100%" }}>
                <ScoreBarComponent
                  score={Math.round(
                    employeeScore.timeslotEvaluationHard.reduce(
                      (prevVal, currVal) => {
                        return prevVal + currVal.harmedConstraints.length;
                      },
                      0
                    )
                  )}
                  name={"Anzahl der harten Zeitslot Constraints"}
                ></ScoreBarComponent>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              {weeks.map((week) => {
                const subList = employeeScore.timeslotEvaluationHard.filter(
                  (e) => e.week == week
                );
                return subList.length == 0 ? (
                  <></>
                ) : (
                  <Accordion
                    key={index + "week" + "timeslotHard"}
                    sx={{ backgroundColor: "#F0F0F0" }}
                  >
                    <AccordionSummary>
                      <Typography sx={{ width: "100%" }}>
                        <ScoreBarComponent
                          score={subList[0].harmedConstraints.length}
                          name={`Woche ${week}`}
                        ></ScoreBarComponent>
                      </Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                      <div style={{ marginLeft: "15px" }}>
                        <div
                          style={{
                            marginBottom: "15px",
                            marginTop: "15px",
                          }}
                        >
                          Fehlgeschlagene harte Constraints
                        </div>
                        {subList[0].harmedConstraints.map(
                          (hConstraint, harmedConstraintIndex) => {
                            return (
                              <Typography
                                key={index + "weich" + harmedConstraintIndex}
                                sx={{
                                  backgroundColor: "#F0F000",
                                  padding: "12px",
                                  borderRadius: "5px",
                                  marginBottom: "5px",
                                }}
                              >
                                <div>
                                  Am{" "}
                                  {getWeekdayTextOfString(hConstraint.weekday)}{" "}
                                  von {hConstraint.startTime} Uhr bis{" "}
                                  {hConstraint.endTime} Uhr. Grund:{" "}
                                  {hConstraint.reason}
                                </div>
                              </Typography>
                            );
                          }
                        )}
                      </div>
                    </AccordionDetails>
                  </Accordion>
                );
              })}
            </AccordionDetails>
          </Accordion>
        ) : (
          <></>
        )}
        {employeeScore.breaksBetweenEvaluation.length > 0 ? (
          <Accordion key={index} sx={{ backgroundColor: "#F0F0F0" }}>
            <AccordionSummary>
              <Typography sx={{ width: "100%" }}>
                <ScoreBarComponent
                  score={Math.round(
                    employeeScore.breaksBetweenEvaluation.reduce(
                      (prevVal, currVal) => {
                        return prevVal + currVal.score;
                      },
                      0
                    ) / employeeScore.breaksBetweenEvaluation.length
                  )}
                  name={"Pausen zwischen Vorlesungen"}
                ></ScoreBarComponent>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              {weeks.map((week) => {
                const subList = employeeScore.breaksBetweenEvaluation.filter(
                  (e) => e.week == week
                );
                return subList.length == 0 ? (
                  <></>
                ) : (
                  <Accordion
                    key={index + "week" + "breaksBetweenEval"}
                    sx={{ backgroundColor: "#F0F0F0" }}
                  >
                    <AccordionSummary>
                      <Typography sx={{ width: "100%" }}>
                        <ScoreBarComponent
                          score={subList[0].score}
                          name={`Woche ${week}`}
                        ></ScoreBarComponent>
                      </Typography>
                    </AccordionSummary>
                  </Accordion>
                );
              })}
            </AccordionDetails>
          </Accordion>
        ) : (
          <></>
        )}

        {employeeScore.distributionEvaluation.length > 0 ? (
          <Accordion
            key={index + "distributionEval"}
            sx={{ backgroundColor: "#F0F0F0" }}
          >
            <AccordionSummary>
              <Typography sx={{ width: "100%" }}>
                <ScoreBarComponent
                  score={Math.round(
                    employeeScore.distributionEvaluation.reduce(
                      (prevVal, currVal) => {
                        return prevVal + currVal.score;
                      },
                      0
                    ) / employeeScore.distributionEvaluation.length
                  )}
                  name={"Verteilung der Veranstaltungen über die Woche"}
                ></ScoreBarComponent>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              {weeks.map((week) => {
                const subList = employeeScore.distributionEvaluation.filter(
                  (e) => e.week == week
                );
                return subList.length == 0 ? (
                  <></>
                ) : (
                  <Accordion
                    key={index + "week" + "distributionEval"}
                    sx={{ backgroundColor: "#F0F0F0" }}
                  >
                    <AccordionSummary>
                      <Typography sx={{ width: "100%" }}>
                        <ScoreBarComponent
                          score={subList[0].score}
                          name={`Woche ${week}`}
                        ></ScoreBarComponent>
                      </Typography>
                    </AccordionSummary>
                  </Accordion>
                );
              })}
            </AccordionDetails>
          </Accordion>
        ) : (
          <></>
        )}
        {employeeScore.freeDayEvaluation.length > 0 ? (
          <Accordion
            key={index + "freeDayEval"}
            sx={{ backgroundColor: "#F0F0F0" }}
          >
            <AccordionSummary>
              <Typography sx={{ width: "100%" }}>
                <ScoreBarComponent
                  score={
                    employeeScore.freeDayEvaluation.reduce(
                      (prevVal, currVal) => {
                        return prevVal + currVal.score;
                      },
                      0
                    ) / employeeScore.freeDayEvaluation.length
                  }
                  name={"Einen vorlesungsfreien Tag in der Woche"}
                ></ScoreBarComponent>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              {weeks.map((week) => {
                const subList = employeeScore.freeDayEvaluation.filter(
                  (e) => e.week == week
                );
                return subList.length == 0 ? (
                  <></>
                ) : (
                  <Accordion
                    key={index + "week" + "freeDayEval"}
                    sx={{ backgroundColor: "#F0F0F0" }}
                  >
                    <AccordionSummary>
                      <Typography sx={{ width: "100%" }}>
                        <ScoreBarComponent
                          score={subList[0].score}
                          name={`Woche ${week}`}
                        ></ScoreBarComponent>
                      </Typography>
                    </AccordionSummary>
                    {subList[0].score >= 100 ? (
                      <></>
                    ) : (
                      <AccordionDetails>
                        {subList[0].score == 0
                          ? "Der Mitarbeitende hat keinen freien Tag in dieser Woche."
                          : `Der ${getWeekdayTextOfString(
                              subList[0].constraint.favoriteDay
                            )} ist als freier Tag gewünscht, es wurde jedoch ein anderer Tag frei gegeben.`}
                      </AccordionDetails>
                    )}
                  </Accordion>
                );
              })}
            </AccordionDetails>
          </Accordion>
        ) : (
          <></>
        )}
        {employeeScore.lunchBreakEvaluation.length > 0 ? (
          <Accordion key={index} sx={{ backgroundColor: "#F0F0F0" }}>
            <AccordionSummary>
              <Typography sx={{ width: "100%" }}>
                <ScoreBarComponent
                  score={Math.round(
                    employeeScore.lunchBreakEvaluation.reduce(
                      (prevVal, currVal) => {
                        return prevVal + currVal.score;
                      },
                      0
                    ) / employeeScore.lunchBreakEvaluation.length
                  )}
                  name={"Mittagspause"}
                ></ScoreBarComponent>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              {weeks.map((week) => {
                const subList = employeeScore.lunchBreakEvaluation.filter(
                  (e) => e.week == week
                );
                return subList.length == 0 ? (
                  <></>
                ) : (
                  <Accordion
                    key={index + "week" + "breaksEval"}
                    sx={{ backgroundColor: "#F0F0F0" }}
                  >
                    <AccordionSummary>
                      <Typography sx={{ width: "100%" }}>
                        <ScoreBarComponent
                          score={subList[0].score}
                          name={`Woche ${week}`}
                        ></ScoreBarComponent>
                      </Typography>
                    </AccordionSummary>
                    {subList[0].score >= 100 ? (
                      <></>
                    ) : (
                      <AccordionDetails>
                        <div style={{ marginLeft: "15px" }}>
                          <div style={{ marginBottom: "15px" }}>
                            Fehlgeschlagene Tage
                          </div>

                          {subList[0].harmedDays.map(
                            (hWeekday, hWeekdayIndex) => {
                              return (
                                <Typography
                                  key={index + "" + hWeekdayIndex}
                                  sx={{
                                    backgroundColor: "#F0F000",
                                    padding: "12px",
                                    borderRadius: "5px",
                                    marginBottom: "5px",
                                  }}
                                >
                                  <div>Weekday {hWeekday}</div>
                                </Typography>
                              );
                            }
                          )}
                        </div>
                      </AccordionDetails>
                    )}
                  </Accordion>
                );
              })}
            </AccordionDetails>
          </Accordion>
        ) : (
          <></>
        )}
        {employeeScore.subsequentTimeslotsEvaluation.length > 0 ? (
          <Accordion
            key={index + "subsequentEval"}
            sx={{ backgroundColor: "#F0F0F0" }}
          >
            <AccordionSummary>
              <Typography sx={{ width: "100%" }}>
                <ScoreBarComponent
                  score={Math.round(
                    employeeScore.subsequentTimeslotsEvaluation.reduce(
                      (prevVal, currVal) => {
                        return prevVal + currVal.score;
                      },
                      0
                    ) / employeeScore.subsequentTimeslotsEvaluation.length
                  )}
                  name={
                    "Maximale Anzahl von Zeitslots ('75 Minuten'), die aufeinanderfolgend mit Veranstaltungen belegt"
                  }
                ></ScoreBarComponent>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              {weeks.map((week) => {
                const subList =
                  employeeScore.subsequentTimeslotsEvaluation.filter(
                    (e) => e.week == week
                  );
                return subList.length == 0 ? (
                  <></>
                ) : (
                  <Accordion
                    key={index + "week" + "subsequentEval"}
                    sx={{ backgroundColor: "#F0F0F0" }}
                  >
                    <AccordionSummary>
                      <Typography sx={{ width: "100%" }}>
                        <ScoreBarComponent
                          score={subList[0].score}
                          name={`Woche ${week}`}
                        ></ScoreBarComponent>
                      </Typography>
                    </AccordionSummary>
                    {subList[0].score >= 100 ? (
                      <></>
                    ) : (
                      <AccordionDetails>
                        <div style={{ marginLeft: "15px" }}>
                          <div style={{ marginBottom: "15px" }}>
                            Fehlgeschlagene Tage
                          </div>
                          {subList[0].harmedDays.map(
                            (hWeekday, hWeekdayIndex) => {
                              return (
                                <Typography
                                  key={index + "" + hWeekdayIndex}
                                  sx={{
                                    backgroundColor: "#F0F000",
                                    padding: "12px",
                                    borderRadius: "5px",
                                    marginBottom: "5px",
                                  }}
                                >
                                  <div>Weekday {hWeekday}</div>
                                </Typography>
                              );
                            }
                          )}
                        </div>
                      </AccordionDetails>
                    )}
                  </Accordion>
                );
              })}
            </AccordionDetails>
          </Accordion>
        ) : (
          <></>
        )}
        {employeeScore.timeslotEvaluationSoft.length > 0 ? (
          <Accordion
            key={index + "timeslotSoft"}
            sx={{ backgroundColor: "#F0F0F0" }}
          >
            <AccordionSummary>
              <Typography sx={{ width: "100%" }}>
                <ScoreBarComponent
                  score={Math.round(
                    employeeScore.timeslotEvaluationSoft.reduce(
                      (prevVal, currVal) => {
                        return prevVal + currVal.score;
                      },
                      0
                    ) / employeeScore.timeslotEvaluationSoft.length
                  )}
                  name={"Bewertung der Zeitslot Constraints"}
                ></ScoreBarComponent>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              {weeks.map((week) => {
                const subList = employeeScore.timeslotEvaluationSoft.filter(
                  (e) => e.week == week
                );
                return subList.length == 0 ? (
                  <></>
                ) : (
                  <Accordion
                    key={index + "week" + "subsequentEval"}
                    sx={{ backgroundColor: "#F0F0F0" }}
                  >
                    <AccordionSummary>
                      <Typography sx={{ width: "100%" }}>
                        <ScoreBarComponent
                          score={subList[0].score}
                          name={`Woche ${week}`}
                        ></ScoreBarComponent>
                      </Typography>
                    </AccordionSummary>
                    {subList[0].score >= 100 ? (
                      <></>
                    ) : (
                      <AccordionDetails>
                        <div style={{ marginLeft: "15px" }}>
                          <div
                            style={{
                              marginBottom: "15px",
                              marginTop: "15px",
                            }}
                          >
                            Fehlgeschlagene weiche Constraints
                          </div>
                          {subList[0].harmedConstraints.map(
                            (hConstraint, harmedConstraintIndex) => {
                              return (
                                <Typography
                                  key={index + "weich" + harmedConstraintIndex}
                                  sx={{
                                    backgroundColor: "#F0F000",
                                    padding: "12px",
                                    borderRadius: "5px",
                                    marginBottom: "5px",
                                  }}
                                >
                                  <div>
                                    Am{" "}
                                    {getWeekdayTextOfString(
                                      hConstraint.weekday
                                    )}{" "}
                                    von {hConstraint.startTime} Uhr bis{" "}
                                    {hConstraint.endTime} Uhr.
                                  </div>
                                </Typography>
                              );
                            }
                          )}
                        </div>
                      </AccordionDetails>
                    )}
                  </Accordion>
                );
              })}
            </AccordionDetails>
          </Accordion>
        ) : (
          <></>
        )}
      </AccordionDetails>
    </Accordion>
  );
}

function ScoreBarComponent(scoreElement: ScoreElement) {
  return (
    <div>
      <div className="description">
        {scoreElement.name}: {scoreElement.score}
      </div>
      <LinearProgress
        sx={{
          backgroundColor: "white",
          "& .MuiLinearProgress-bar": {
            backgroundColor: `${ColoredScoreBar(scoreElement.score)}`,
          },
        }}
        variant="determinate"
        value={scoreElement.score}
      />
    </div>
  );
}

function WeightBarComponent(scoreElement: ScoreElement) {
  return (
    <div>
      <div className="explanation">
        {scoreElement.score} Prozent des Semesterscores
      </div>
      <LinearProgress
        sx={{
          backgroundColor: "white",
          "& .MuiLinearProgress-bar": {
            backgroundColor: `Gray`,
          },
          marginBottom: `5px`,
        }}
        variant="determinate"
        value={scoreElement.score}
      />
      <div className="explanation">{scoreElement.name}</div>
    </div>
  );
}

function ColoredScoreBar(score: number | undefined) {
  if (score && score > 70) return "green";
  else if (score && score > 40) return "orange";
  else return "red";
}

function PlanningScore() {
  const [score, setScores] = useState<ScoreAPI>();
  const [timeslots, setTimeslots] = useState<TimeslotAPI[]>();
  const [isLoading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    fetchScore().then((response) => {
      setScores(response.data);
      fetchTimeslots().then((response) => {
        console.log(response);
        setTimeslots(timeslots);
        setLoading(false);
      });
    });
  }, []);
  return (
    <PageLayout title="Übersicht Score ">
      <Spinner isLoading={isLoading} />
      <ScoreBarComponent
        score={score?.score}
        name={"Gesamtscore"}
      ></ScoreBarComponent>
      <Accordion sx={{ backgroundColor: "#F0F0F0" }}>
        <AccordionSummary>
          <Typography sx={{ width: "100%" }}>
            <ScoreBarComponent
              score={score?.studentEvaluationScoreDTO.totalScore}
              name={"Gesamtscore Studenten"}
            ></ScoreBarComponent>
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          {score?.studentEvaluationScoreDTO?.degreeScores.map((degreeScore) => {
            return DegreeScoreComponent(degreeScore);
          })}
        </AccordionDetails>
      </Accordion>
      <Accordion sx={{ backgroundColor: "#F0F0F0" }}>
        <AccordionSummary>
          <Typography sx={{ width: "100%" }}>
            <ScoreBarComponent
              score={score?.employeeEvaluationScoreDTO.totalScore}
              name={"Gesamtscore Dozenten"}
            ></ScoreBarComponent>
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          {score?.employeeEvaluationScoreDTO?.employeeEvaluations.map(
            (employeeScore, index) => {
              return EmployeeSemesterScoreComponent(employeeScore, index);
            }
          )}
        </AccordionDetails>
      </Accordion>
    </PageLayout>
  );
}

export default PlanningScore;
