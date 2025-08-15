import type { ReactNode } from "react";

import Login from "../pages/Login";

interface AuthGuardProps {
  children: ReactNode;
}
const AuthGuard: React.FC<AuthGuardProps> = ({ children }) => {
  const token = localStorage.getItem("jwt");

  if (!token) {
    return <Login></Login>;
  }

  return <>{children}</>;
};
export default AuthGuard;
