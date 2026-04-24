import { Routes, Route } from "react-router-dom";

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
            <Route path="/search" element={<SearchPage />} />
          </Route>
        </Routes>
      </div>
    </>
  );
}

export default App;
