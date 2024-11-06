import React from "react";
import { Link, useNavigate } from "react-router-dom";
import "./NavBar.css";

export default function NavBar() {

    const navigate = useNavigate();
    const isAdmin = localStorage.jwt && localStorage.roles.includes('ADMIN');
    const isUser = localStorage.jwt && localStorage.roles.includes('USER') && !localStorage.roles.includes('ADMIN');
    const isLoggedIn = localStorage.jwt && localStorage.roles.includes('USER') || localStorage.jwt &&localStorage.roles.includes('ADMIN');

    function handleSignOut() {
        localStorage.clear();
        navigate("/login");
    }
    console.log(localStorage);
    
  return (
    <nav className="navbar">
      {/* {<h2 className="navbar-logo">Solar Watch</h2>} */}
      <ul className="navbar-links">
        <li> 
        {isUser && <Link to="/admin">Search Solar Report</Link>}
        </li>
        <li>
            {isAdmin && <Link to="/admin">Create Solar Report</Link>}
        </li>
        <li> {isLoggedIn ? 
                <button onClick={handleSignOut}>Sign out</button>
                :
                <Link to="/login">Login</Link>
            }
        </li>
        <li>
        {!isLoggedIn && <Link to="/registration">Register</Link>}
        </li>
      </ul>
    </nav>
  );
};
