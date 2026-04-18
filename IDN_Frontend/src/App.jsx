import { Routes, Route } from "react-router-dom";

import Home from "./pages/Home.jsx";
import About from "./pages/About.jsx";
import Login from "./pages/Login.jsx";
import SignUp from "./pages/SignUp.jsx";
import CompleteRegistration from "./pages/CompleteRegistration.jsx";
import Dashboard from "./pages/Dashboard.jsx";
import ArticlePage from "./pages/ArticlePage.jsx";
import CategoryPage from "./pages/CategoryPage.jsx";
import Layout from "./Components/Layout";

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
            <Route path="/category/:category" element={<CategoryPage />} />
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
