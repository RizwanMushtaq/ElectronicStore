import type { LoginResponse, RefreshToken, User } from "../auth/login";
import { AUTH_TOKEN_KEY, REFRESH_TOKEN_KEY, USER_KEY } from "./appConstants";

export const saveLoginResponseToLocalStorage = (
  LoginResponse: LoginResponse
): void => {
  localStorage.setItem(AUTH_TOKEN_KEY, LoginResponse.token);
  localStorage.setItem(
    REFRESH_TOKEN_KEY,
    JSON.stringify(LoginResponse.refreshToken)
  );
  localStorage.setItem(USER_KEY, JSON.stringify(LoginResponse.user));
};

export const getAuthToken = (): string | null => {
  return localStorage.getItem(AUTH_TOKEN_KEY);
};

export const getRefreshToken = (): RefreshToken | null => {
  const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY);

  if (!refreshToken) {
    return null;
  }

  return JSON.parse(refreshToken) as RefreshToken;
};

export const getUser = (): User | null => {
  const user = localStorage.getItem(USER_KEY);

  if (!user) {
    return null;
  }

  return JSON.parse(user);
};

export const clearLocalStorage = (): void => {
  localStorage.clear();
};
