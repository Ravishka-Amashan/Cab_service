import React from 'react';
import { Link } from 'react-router-dom';
import '../style/Navbar.css';

function Navbar() {
  return (
    <nav className="navbar">
      <ul>
        <li>
          <Link to="/aboutuspage">About Us</Link>
        </li>
        <li>
          <Link to="/register">Register</Link>  
        </li>
        <li>
          <Link to="/contact">Contact Us</Link>
        </li>
        <li>
          <Link to="/reservation">ReservationPage</Link>
        </li>
        <li>
          <Link to="/login" className="login-button">Login</Link>
        </li>
      </ul>
    </nav>
  );
}

export default Navbar;
