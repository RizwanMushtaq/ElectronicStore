import type { ReactNode } from "react";

import Login from "../pages/Login";
import { isAuthenticatedUser } from "../utils/localStorage";

interface AuthGuardProps {
  children: ReactNode;
}
const AuthGuard: React.FC<AuthGuardProps> = ({ children }) => {
  const isAuthenticated = isAuthenticatedUser();

  if (!isAuthenticated) {
    return <Login></Login>;
  }

  return <>{children}</>;
};
export default AuthGuard;
