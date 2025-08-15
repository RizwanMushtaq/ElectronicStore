import type { ReactNode } from "react";

import Login from "../pages/Login";
import { getUser, isAuthenticatedUser } from "../utils/localStorage";
import { AuthContext } from "./AuthContext";

interface AuthGuardProps {
  children: ReactNode;
}
const AuthGuard: React.FC<AuthGuardProps> = ({ children }) => {
  const userId: string | undefined = getUser()?.id;
  const isAuthenticated = isAuthenticatedUser();

  if (!isAuthenticated || userId === undefined) {
    return <Login></Login>;
  }

  return (
    <>
      <AuthContext.Provider value={{ userId }}>{children}</AuthContext.Provider>
    </>
  );
};
export default AuthGuard;
