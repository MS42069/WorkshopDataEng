import { useSelector } from "react-redux";
import { RootState } from "../redux/store";
import { Navigate } from "react-router";

interface AuthGuardProps {
  loggedInComponent?: JSX.Element;
  loggedOutComponent?: JSX.Element;
  redirectTo: string;
}

function AuthGuard({
  loggedInComponent,
  loggedOutComponent,
  redirectTo,
}: AuthGuardProps) {
  const { isAuthenticated } = useSelector(
    ({ authReducer }: RootState) => authReducer
  );

  if (loggedInComponent) {
    if (!isAuthenticated) {
      return <Navigate to={redirectTo} replace />;
    }

    return loggedInComponent;
  }

  if (loggedOutComponent) {
    if (isAuthenticated) {
      return <Navigate to={redirectTo} replace />;
    }

    return loggedOutComponent;
  }

  return <Navigate to={redirectTo} replace />;
}

export default AuthGuard;
