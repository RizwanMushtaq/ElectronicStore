import { useNavigate } from "react-router";

const Header: React.FC = () => {
  const navigate = useNavigate();

  return (
    <header className="shadow-md">
      <div className="container mx-auto flex justify-between items-center py-4 px-6">
        <h1 onClick={() => navigate("/")} className="text-2xl font-bold">
          Electronic-Store
        </h1>

        <button
          onClick={() => (window.location.href = "/login")}
          className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition"
        >
          Login
        </button>
      </div>
    </header>
  );
};

export default Header;
