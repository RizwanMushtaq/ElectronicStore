import { useNavigate } from "react-router";

const Login: React.FC = () => {
  const navigate = useNavigate();
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
              type="email"
              placeholder="john"
              className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Password</label>
            <input
              type="password"
              placeholder="••••••••"
              className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <button className="w-full bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition">
            Login
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
