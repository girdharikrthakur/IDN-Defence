import { useState } from "react";
import Sidebar from "./SideBar.jsx";
import Navbar from "./NavBar.jsx";
import { Outlet } from "react-router-dom";
import Footer from "./Footer.jsx";
import SecNavBar from "./SecNavBar.jsx";

function Layout() {
  const [isOpen, setIsOpen] = useState(false);

  const toggle = () => setIsOpen(!isOpen);

  return (
    <>
      <Navbar toggle={toggle} />
      <SecNavBar />
      <Sidebar isOpen={isOpen} toggle={toggle} />

      <div className="p-4">
        <Outlet />
      </div>
      <Footer />
    </>
  );
}

export default Layout;
