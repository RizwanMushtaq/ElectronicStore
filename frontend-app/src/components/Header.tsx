import { useNavigate } from "react-router";

const Header: React.FC = () => {
  const navigate = useNavigate();

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
        <button
          onClick={() => navigate("/login")}
          className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition cursor-pointer"
        >
          Login
        </button>
      </div>
    </header>
  );
};

export default Header;
