import { Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import Home from "./pages/Home";
import ArticlePage from "./pages/ArticlePage";
import About from "./pages/About";
import Login from "./pages/Login";
import Signup from "./pages/SignUp";
import Dashboard from "./pages/Dashboard";
import Contact from "./pages/Contact";
import CompleteRegistration from "./pages/CompleteRegistration";
import CategoryPage from "./pages/CategoryPage";
import OAuthSuccess from "./components/OAuthSuccess";
import Search from "./pages/SearchPage";

function App() {
  return (
    <>
      <div className="bg-gray-100">
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<Home />} />
            <Route path="/article/:id" element={<ArticlePage />} />
            <Route path="/about" element={<About />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/contact" element={<Contact />} />
            <Route
              path="/complete-registration"
              element={<CompleteRegistration />}
            />
            <Route path="/category/:category" element={<CategoryPage />} />
            <Route path="/oauthsuccess" element={<OAuthSuccess />} />
            <Route path="/search" element={<Search />} />
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
