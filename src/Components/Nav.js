import React from "react";
import "./Nav.css";

const Nav = () => {
  return (
    <nav className="bg-blue-700 text-white py-4 px-6 flex justify-between items-center">
      <div class className="text-xl font-bold">
        IDN Defence
      </div>
      <ul className="flex space-x-6">
        <li>
          <a href="#home" className="hover:text-gray-300">
            Home
          </a>
        </li>
        <li>
          <a href="#about" className="hover:text-gray-300">
            About
          </a>
        </li>
        <li>
          <a href="#contact" className="hover:text-gray-300">
            Contact
          </a>
        </li>
      </ul>
    </nav>
  );
};

export default Nav;
