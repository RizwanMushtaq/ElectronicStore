import { useState } from "react";
import { useNavigate } from "react-router";
import { login, type LoginPayload, type LoginResponse } from "../auth/login";
import { saveLoginResponseToLocalStorage } from "../utils/localStorage";

const Login: React.FC = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);

  const handleLogin = async () => {
    console.log("Logging in with", { username, password });

    const loginPayload: LoginPayload = {
      username,
      password,
    };

    try {
      const loginResponse = await login(loginPayload);
      console.log("Login response:", loginResponse);

      if (!loginResponse.ok) {
        setError("Invalid Username or Password");
        return;
      }

      setError(null);
      const responseData: LoginResponse = await loginResponse.json();
      saveLoginResponseToLocalStorage(responseData);
      navigate("/");
    } catch (error) {
      console.error("Login error:", error);
      setError("An error occurred while logging in. Please try again.");
      return;
    }
  };

  return (
    <div className="flex justify-center items-center">
      <div className="shadow-md rounded-lg p-8 w-full max-w-sm">
        <h4
          onClick={() => navigate("/")}
          className="font-light mb-10 text-blue-400 cursor-pointer"
        >
          back to Electronic-Store
        </h4>
        <h2 className="text-2xl font-bold text-center mb-6">Login</h2>
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium mb-1">Username</label>
            <input
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              type="username"
              placeholder="john"
              className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Password</label>
            <input
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              type="password"
              placeholder="••••••••"
              className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <button
            className="w-full bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition"
            onClick={handleLogin}
          >
            Login
          </button>

          {error && <div className="text-red-500 text-sm mt-2">{error}</div>}
        </div>
      </div>
    </div>
  );
};

export default Login;
