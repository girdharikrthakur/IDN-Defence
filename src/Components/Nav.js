// src/components/Nav.js
import React from 'react';
import { Link } from 'react-router-dom';
import './Nav.css';  // Add a CSS file for styling

const Nav = () => {
  return (
    <nav className="nav">
      <h3 className="logo">NewsSite</h3>
      <ul className="nav-links">
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/about">About</Link>
        </li>
        <li>
          <Link to="/contact">Contact</Link>
        </li>
        <li>
          <Link to="/admin">Admin</Link>
        </li>
      </ul>
    </nav>
  );
};

export default Nav;
