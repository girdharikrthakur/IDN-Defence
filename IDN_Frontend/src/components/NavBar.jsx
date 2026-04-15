import { Link } from "react-router-dom";

function Navbar({ toggle }) {
  return (
    <div className="fixed top-0 z-40 w-full">
      <div className="flex  bg-black">
        <div className="flex gap-4 p-4 text-white w-[50%]">
          <button onClick={toggle} className="text-2xl">
            ☰
          </button>

          <Link className="font-black text-2xl text-center" to="/">
            IDN
          </Link>
        </div>
        <div className="flex p-4 text-white justify-end w-[50%]">
          <nav className="flex gap-4">
            <button className="bg-gray-600 h-8 w-14 rounded-sm text-center items-center">
              <Link to="/login">Login</Link>
            </button>
            <button>
              <Link to="/signup">Sign Up</Link>
            </button>
          </nav>
        </div>
      </div>
    </div>
  );
}

export default Navbar;
