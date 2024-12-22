import React, { useEffect } from "react";
import "./App.css";
import Login from "./components/pages/login/login";
import { AppDispatch, RootState } from "./redux/store";
import { Navigate, Route, Routes } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import { csrfTokenThunk } from "./redux/csrf.reducer";
import { userInformationRequest } from "./redux/auth.reducer";
import { unregisterRequest } from "./redux/spinner.reducer";
import AuthGuard from "./guards/auth.guard";
import MainLayout from "./components/elements/main-layout";
import CustomTimetable from "./components/pages/custom-timetable/custom-timetable";
import {
  aboutRoute,
  preferenceProfileRoute,
  timetableRoute,
  loginRoute
} from "./config/routes";
import About from "./components/pages/aboute-page/about";
import ProfilePage from "./components/pages/profile-page/profile-page";

function App() {
  const dispatch = useDispatch<AppDispatch>();
  const { isAuthenticated } = useSelector(
    (state: RootState) => state.authReducer
  );

  useEffect(() => {
    dispatch(csrfTokenThunk());
    dispatch(userInformationRequest()).finally(() => {
      dispatch(unregisterRequest());
    });
  }, [dispatch]);
  return (
    <div className="full-size center-content">
      <Routes>
        {isAuthenticated && (
          <Route
            element={
              <AuthGuard
                loggedInComponent={<MainLayout />}
                redirectTo={loginRoute}
              />
            }
          >
            {<Route path={aboutRoute} element={<About />} />}
            {<Route path={preferenceProfileRoute} element={<ProfilePage />} />}
            {<Route path={timetableRoute} element={<CustomTimetable />} />}
            {/**Not finished, to be implemented later 
            {<Route path={coursePreferenceRoute} element={<CourseProfilePage/>}/>}
            {<Route path={courseTimetableRoute} element={<CourseTimetable/>}/>}
            */}
            {<Route path="*" element={<Navigate to={aboutRoute} />} />}
          </Route>
        )}
        <Route
          path={loginRoute}
          element={
            <AuthGuard loggedOutComponent={<Login />} redirectTo={aboutRoute} />
          }
        />
        <Route path="*" element={<Navigate to={loginRoute} />} />
      </Routes>
      {/*<Spinner isLoading={pendingRequests > 0} />*/}
    </div>
  );
}

export default App;
