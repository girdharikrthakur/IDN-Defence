import { useState } from "react";
import Sidebar from "./Sidebar";
import Navbar from "./Navbar";
import { Outlet } from "react-router-dom";
import Footer from "./Footer";
import SecNavBar from "./SecNavBar";

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
