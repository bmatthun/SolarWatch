import React from 'react'
import ReactDOM from "react-dom/client"
import './index.css'
import RegistrationForm from './pages/RegistrationForm.jsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Layout from './pages/Layout/Layout.jsx'; 
import LoginForm from './pages/LoginForm.jsx';
import SunsetSunrise from './pages/SolarReport.jsx';
import SolarReport from './pages/SolarReport.jsx';
import Admin from './pages/Admin.jsx';



const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    //errorElement: <ErrorPage />,
    children: [
      {
        path: "/registration",
        element: <RegistrationForm />,
      },
      {
        path: "/login",
        element: <LoginForm />
      },
      {
        path: "/search",
        element: <SolarReport />
      },
      {
        path: "/admin",
        element: <Admin />
      }
    ]
  }
])

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
