import { Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import About from "./pages/About";
import Login from "./pages/Login";
import Navbar from "./components/NavBar";
import Footer from "./components/Footer";
import SignUp from "./pages/SignUp";
import CompleteRegistration from "./pages/CompleteRegistration";
import Dashboard from "./pages/Dashboard";

function App() {
  return (
    <>
      <div>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route
            path="/complete-registration"
            element={<CompleteRegistration />}
          />
        </Routes>
        <Footer />
      </div>
    </>
  );
}

export default App;
