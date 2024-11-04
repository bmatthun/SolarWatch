import { Outlet } from "react-router-dom";

import Header from "../Header";
import "./Layout.css";

const Layout = () => (
  <div className="Layout">
    <Header />
    <Outlet />
  </div>
);

export default Layout;
