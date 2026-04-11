import { Link } from "react-router-dom";

function Navbar() {
  return (
    <>
      <div className="fixed top-0 z-50 w-full">
        <div className="flex whitespace-nowrap   bg-gray-800">
          <div className="flex gap-4 p-4 bg-gray-800 text-white w-[50%]">
            <Link className="flex jus" to="/">
              Home
              <img src="./a" alt="" />
            </Link>
          </div>
          <div className="flex p-4 text-white justify-end w-[50%]">
            <nav className="flex gap-4">
              <Link to="/about">About</Link>
              <Link to="/login">Login</Link>
              <Link to="/signup">Sign Up</Link>
              <Link to="/dashboard">Dashboard</Link>
            </nav>
          </div>
        </div>
      </div>
    </>
  );
}

export default Navbar;
