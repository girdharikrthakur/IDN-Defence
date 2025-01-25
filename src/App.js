import React from "react";

import "./index.css";
import IndependenceDay from "./Components/IndependenceDay";
import Nav from "./Components/Nav";
import Popup from "./Components/Popup";
import "./index.css";

const App = () => {
  return (
    <div>
      {/* Navigation Bar */}
      <Nav />
      {/* Add the popup component */}
      <Popup />
      {/* Main Content */}
      <IndependenceDay />
    </div>
  );
};

export default App;
