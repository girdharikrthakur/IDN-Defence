import { Link, useNavigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

function Navbar({ toggle }) {
  const [user, setUser] = useState({
    loggedIn: false,
    username: null,
    roles: [],
    isAdmin: false,
  });

  const navigate = useNavigate();
  const location = useLocation();

  // ✅ Fetch user
  const fetchUser = async () => {
    try {
      const token = localStorage.getItem("accessToken");
      const res = await axios.get("/api/me", {
        headers: {
          Authorization: token ? `Bearer ${token}` : "",
        },
      });

      setUser(res.data);
    } catch (err) {
      console.error(err);

      setUser({
        loggedIn: false,
        username: null,
        roles: [],
        isAdmin: false,
      });
    }
  };

  useEffect(() => {
    // eslint-disable-next-line react-hooks/set-state-in-effect
    fetchUser();
  }, [location.pathname]); // 🔥 IMPORTANT

  // ✅ Logout
  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    setUser({
      loggedIn: false,
      username: null,
      roles: [],
      isAdmin: false,
    });
    navigate("/home");
  };

  const isDashboardPage = location.pathname === "/dashboard";

  return (
    <div className="fixed top-0 z-40 w-full">
      <div className="flex bg-black">
        {/* LEFT */}
        <div className="flex gap-4 p-4 text-white w-[50%]">
          <button onClick={toggle} className="text-2xl">
            ☰
          </button>

          <Link className="font-black text-2xl text-center" to="/">
            IDN
          </Link>
        </div>

        {/* RIGHT */}
        <div className="flex p-4 text-white justify-end w-[50%]">
          <nav className="flex gap-4 items-center">
            {/* ✅ Dashboard */}
            {user.loggedIn && user.isAdmin && !isDashboardPage && (
              <Link
                to="/dashboard"
                className="bg-blue-600 h-8 px-3 rounded-sm flex items-center"
              >
                Dashboard
              </Link>
            )}

            {/* ✅ NOT logged in */}
            {!user.loggedIn && (
              <>
                <Link
                  to="/login"
                  className="bg-gray-600 h-8 w-14 rounded-sm flex items-center justify-center"
                >
                  Login
                </Link>

                <Link to="/signup">Sign Up</Link>
              </>
            )}

            {/* ✅ Logged in */}
            {user.loggedIn && (
              <>
                <span className="text-sm text-gray-300">{user.username}</span>

                <button
                  onClick={handleLogout}
                  className="bg-red-600 h-8 px-3 rounded-sm"
                >
                  Logout
                </button>
              </>
            )}
          </nav>
        </div>
      </div>
    </div>
  );
}

export default Navbar;
