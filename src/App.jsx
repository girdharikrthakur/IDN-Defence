import { Routes, Route } from "react-router-dom";

import Home from "./Pages/Home.jsx";
import About from "./Pages/About.jsx";
import Login from "./Pages/Login.jsx";
import SignUp from "./Pages/SignUp.jsx";
import CompleteRegistration from "./Pages/CompleteRegistration.jsx";
import Dashboard from "./Pages/Dashboard.jsx";
import ArticlePage from "./Pages/ArticlePage.jsx";
import Layout from "./components/Layout.jsx";

function App() {
  return (
    <>
      <div className="bg-gray-100">
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
