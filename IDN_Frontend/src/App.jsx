import { Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import About from "./pages/About";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import CompleteRegistration from "./pages/CompleteRegistration";
import Dashboard from "./pages/Dashboard";
import ArticlePage from "./pages/ArticlePage";
import Layout from "./components/Layout";

function App() {
  return (
    <>
      <div>
        <Routes>
          <Route element={<Layout />}>
            <Route path="/article/:id" element={<ArticlePage />} />
            <Route path="/" element={<Home />} />
            <Route path="/about" element={<About />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route
              path="/complete-registration"
              element={<CompleteRegistration />}
            />
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
