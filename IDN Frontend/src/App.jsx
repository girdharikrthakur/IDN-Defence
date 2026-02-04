import { Routes, Route } from "react-router-dom";
import BlogPage from "./BlogPage.jsx";
import PostDetail from "./PostDetail.jsx";
import Header from "./Header.jsx";
import Footer from "./Footer.jsx";

const App = () => {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<BlogPage />} />
        <Route path="/news/:id" element={<PostDetail />} />
      </Routes>
      <Footer />
    </>
  );
};

export default App;
