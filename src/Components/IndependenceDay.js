import { motion } from "framer-motion";
import React from "react";
import "./IndependanceDay.css";
const IndependenceDay = () => {
  return (
    <div className="main-container">
      {/* Video Background */}
      <video
        className="background-video"
        src="/background.mp4"
        autoPlay
        loop
        muted
      />

      {/* Header */}
      <header className="header">
        <h1 className="header-title">Happy Republic Day</h1>
      </header>

      {/* Main Content */}
      <main className="content">
        {/* Indian Flag Animation */}
        <div className="flag-container">
          <motion.div
            className="flag-stripe orange"
            animate={{ y: [0, 10, 0] }}
            transition={{ repeat: Infinity, duration: 1.5 }}
          ></motion.div>
          <div className="flag-stripe white"></div>
          <motion.div
            className="flag-stripe green"
            animate={{ y: [0, -10, 0] }}
            transition={{ repeat: Infinity, duration: 1.5 }}
          ></motion.div>
          <div className="chakra-container">
            <div className="chakra"></div>
          </div>
        </div>

        <p className="message">Celebrate freedom and unity!</p>
      </main>
    </div>
  );
};

export default IndependenceDay;
