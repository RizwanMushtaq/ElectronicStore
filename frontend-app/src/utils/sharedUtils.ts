import { getAuthToken } from "./localStorage";

export const getHeadersWithAuthorization = () => {
  const token = getAuthToken() || "dummyToken";
  return {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };
};
