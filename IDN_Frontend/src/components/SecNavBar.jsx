import { Link } from "react-router-dom";

export default function SecNavBar() {
  return (
    <>
      <div className="mt-15 p-2 flex flex-wrap gap-8 justify-center bg-white text-gray-700">
        <Link to="/Army">Army</Link>
        <Link to="/navy">Navy</Link>
        <Link to="/airforce">Air Force</Link>
        <Link to="/space">Space</Link>
        <Link to="/india">India</Link>
        <Link to="/world">World</Link>
      </div>
    </>
  );
}
