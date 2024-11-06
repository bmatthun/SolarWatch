import { Outlet } from "react-router-dom";

import Header from "../Header/Header";
import "./Layout.css";
import NavBar from "../NavBar/NavBar";

const Layout = () => (
  <div className="Layout">
    {<Header />}
    <NavBar /> 
    <Outlet />
  </div>
);

export default Layout;
