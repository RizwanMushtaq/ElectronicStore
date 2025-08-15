export interface User {
  id: string;
  username: string;
  name: string;
  email: string;
  gender: string;
  about: string;
  imageName: string;
  roles: Role[];
}
export interface Role {
  id: string;
  name: string;
}
export interface LoginResponse {
  user: User;
  token: string;
  refreshToken: RefreshToken;
}

export interface RefreshToken {
  id: number;
  token: string;
  expiryDate: Date;
}

export interface LoginPayload {
  username: string;
  password: string;
}

export const login = async (loginPayload: LoginPayload): Promise<Response> => {
  const apiUrl = import.meta.env.VITE_API_BASE_URL + "/auth/login";
  return await fetch(apiUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(loginPayload),
  });
};
