import "./styles.scss";
import Login from "./components/pages/login/login";
import { Navigate, Route, Routes } from "react-router";
import MainLayout from "./shared/main-layout";
import EmployeeOverview from "./components/pages/employee/employee-overview";
import LectureOverview from "./components/pages/lecture/lectures-overview";
import LectureDetailView from "./components/pages/lecture/lecture-detail";
import RoomOverview from "./components/pages/room/rooms-overview";
import TimeslotOverview from "./components/pages/timeslot/timeslot-overview";
import Employee from "./components/pages/employee-detail/employee";
import AuthGuard from "./guards/auth.guard";
import SingleViewRoom from "./components/pages/single-view-room/single-view-room";
import SubjectOverview from "./components/pages/subject/subject-overview";
import DegreeDetail from "./components/pages/subject/degree-detail";
import TimetableOverview from "./components/pages/timetable-general/timetable-overview";
import TimetableUpdate from "./components/pages/timetable-general/timetable-detail";
import PlanningTableWeek from "./components/pages/planning-day/planning-day";
import PlanningScore from "./components/pages/planning-score/planning-score";
import { planningURLPrefix, baseDataURLPrefix } from "./shared/url-prefix";
import { useEffect } from "react";
import { unregisterRequest } from "./redux/spinner.reducer";
import { useDispatch, useSelector } from "react-redux";
import { userInformationRequest } from "./redux/auth.reducer";
import { csrfTokenThunk } from "./redux/csrf.reducer";
import { AppDispatch, RootState } from "./redux/store";
import Spinner from "./shared/components/spinner";
import PlanningEmployee from "./components/pages/planning-employee/planning-employee";
import PlanningLecture from "./components/pages/planning-lecture/planning-lecture";
import PlanningRoom from "./components/pages/planning-room/planning-room";
import TypesCategoriesOverview from "./components/pages/types-categories/types-categories-overview";
import WishesAppActivation from "./components/pages/wishes-app-activation/wishes-app-activation";
import EmployeeCheckWorktime from "./components/pages/employee-check-worktime/employee-check-worktime";

function App() {
  const dispatch = useDispatch<AppDispatch>();
  const { pendingRequests } = useSelector(
    (state: RootState) => state.spinnerReducer
  );
  const { isAuthenticated } = useSelector(
    (state: RootState) => state.authReducer
  );

  useEffect(() => {
    dispatch(csrfTokenThunk());
    dispatch(userInformationRequest()).finally(() => {
      dispatch(unregisterRequest());
    });
  }, []);

  return (
    <div className="full-size center-content">
      <Routes>
        {isAuthenticated && (
          <Route
            element={
              <AuthGuard
                loggedInComponent={<MainLayout />}
                redirectTo="/login"
              />
            }
          >
            {/***************** Timetable selection routes *****************/}
            <Route path="/timetables" element={<TimetableOverview />} />
            <Route path="/timetables/add" element={<TimetableUpdate />} />
            <Route path="/timetables/edit/:id" element={<TimetableUpdate />} />
            {/***************** Planning routes *****************/}

            <Route
              path={planningURLPrefix.concat("/day")}
              element={<PlanningTableWeek />}
            />
            <Route
              path={planningURLPrefix.concat("/employee")}
              element={<PlanningEmployee />}
            />
            <Route
              path={planningURLPrefix.concat("/lecture")}
              element={<PlanningLecture />}
            />
            <Route
              path={planningURLPrefix.concat("/room")}
              element={<PlanningRoom />}
            />
            <Route
              path={planningURLPrefix.concat("/score")}
              element={<PlanningScore />}
            />
            {/***************** Base data routes *****************/}
            <Route
              path={baseDataURLPrefix.concat("/employee")}
              element={<EmployeeOverview />}
            />
            <Route
              path={"/employee-check-worktime"}
              element={<EmployeeCheckWorktime />}
            />
            <Route
              path={baseDataURLPrefix.concat("/employee/add")}
              element={<Employee />}
            />
            <Route
              path={baseDataURLPrefix.concat("/employee/edit/:id")}
              element={<Employee />}
            />
            <Route
              path={baseDataURLPrefix.concat("/lecture")}
              element={<LectureOverview />}
            />
            <Route
              path={baseDataURLPrefix.concat("/lecture/add")}
              element={<LectureDetailView />}
            />
            <Route
              path={baseDataURLPrefix.concat("/lecture/edit/:id")}
              element={<LectureDetailView />}
            />
            <Route
              path={baseDataURLPrefix.concat("/timeslot")}
              element={<TimeslotOverview />}
            />
            <Route
              path={baseDataURLPrefix.concat("/room")}
              element={<RoomOverview />}
            />
            <Route
              path={baseDataURLPrefix.concat("/room/add")}
              element={<SingleViewRoom />}
            />
            <Route
              path={baseDataURLPrefix.concat("/room/edit/:id")}
              element={<SingleViewRoom />}
            />
            <Route
              path={baseDataURLPrefix.concat("/subject")}
              element={<SubjectOverview />}
            />
            <Route
              path={baseDataURLPrefix.concat("/subject/add")}
              element={<DegreeDetail />}
            />
            <Route
              path={baseDataURLPrefix.concat("/subject/edit/:id")}
              element={<DegreeDetail />}
            />
            <Route
              path={"/types-categories/"}
              element={<TypesCategoriesOverview />}
            />
            <Route
              path={"/wishes-app-activation"}
              element={<WishesAppActivation />}
            />
            <Route path="*" element={<Navigate to="/timetables" />} />
          </Route>
        )}
        <Route
          path="/login"
          element={
            <AuthGuard
              loggedOutComponent={<Login />}
              redirectTo="/timetables"
            />
          }
        />
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
      <Spinner isLoading={pendingRequests > 0} />
    </div>
  );
}

export default App;
