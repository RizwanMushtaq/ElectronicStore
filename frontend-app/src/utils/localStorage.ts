import type { LoginResponse } from "../auth/login";

export const saveLoginResponseToLocalStorage = (
  LoginResponse: LoginResponse
): void => {
  localStorage.setItem("jwt", LoginResponse.token);
  localStorage.setItem(
    "refreshToken",
    JSON.stringify(LoginResponse.refreshToken)
  );
  localStorage.setItem("user", JSON.stringify(LoginResponse.user));
};
