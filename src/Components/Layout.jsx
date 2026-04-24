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
    <div className="min-h-screen flex flex-col">
      <Navbar toggle={toggle} />
      <SecNavBar />
      <Sidebar isOpen={isOpen} toggle={toggle} />
      <div className="flex-1">
        <Outlet />
      </div>
      <Footer />
    </div>
  );
}

export default Layout;
