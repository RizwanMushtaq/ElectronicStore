import { createContext, useContext } from "react";

interface AuthContextType {
  userId: string;
}

export const AuthContext = createContext<AuthContextType>({
  userId: "dummyUserId",
});

export const useAuthContext = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuthContext must be used within an AuthProvider");
  }
  return context;
};
