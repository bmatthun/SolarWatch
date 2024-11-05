import { Outlet } from "react-router-dom";

import Header from "../Header";
import "./Layout.css";
import NavBar from "../NavBar";

const Layout = () => (
  <div className="Layout">
    <NavBar /> 
    {/*<Header />*/}
    <Outlet />
  </div>
);

export default Layout;
