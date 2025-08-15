import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import {
  clearLocalStorage,
  getAuthToken,
  getRefreshToken,
  getUser,
} from "../utils/localStorage";

const Header: React.FC = () => {
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = getAuthToken();
    const refreshToken = getRefreshToken();
    const user = getUser();
    const isAuthenticated = !!token && !!refreshToken && !!user;
    setIsLoggedIn(isAuthenticated);
  }, []);

  return (
    <header className="shadow-md">
      <div className="container mx-auto flex justify-between items-center py-4 px-6">
        <h1
          onClick={() => navigate("/")}
          className="text-2xl font-bold hover:text-blue-600 transition cursor-pointer"
        >
          Electronic-Store
        </h1>
        <nav className="space-x-4">
          <a
            onClick={() => navigate("/")}
            className="text-gray-400 hover:text-blue-600 transition cursor-pointer"
          >
            Home
          </a>

          <a
            onClick={() => navigate("/cart")}
            className="text-gray-400 hover:text-blue-600 transition cursor-pointer"
          >
            Cart
          </a>

          <a
            onClick={() => navigate("/orders")}
            className="text-gray-400 hover:text-blue-600 transition cursor-pointer"
          >
            Orders
          </a>
        </nav>
        {isLoggedIn ? (
          <div className="flex items-center space-x-4">
            <span className="text-gray-400">
              {getUser()?.username || "User"}
            </span>
            <button
              onClick={() => {
                clearLocalStorage();
                navigate("/login");
              }}
              className="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition cursor-pointer"
            >
              Logout
            </button>
          </div>
        ) : (
          <button
            onClick={() => navigate("/login")}
            className="text-gray-400 hover:text-blue-600 transition cursor-pointer"
          >
            Login
          </button>
        )}
      </div>
    </header>
  );
};

export default Header;
