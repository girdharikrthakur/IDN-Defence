import { motion } from "framer-motion";
import React from "react";
import "./IndependanceDay.css";

const IndependenceDay = () => {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-b from-blue-500 to-green-500">
      <header className="w-full text-center py-4 bg-gradient-to-r from-orange-600 to-green-600">
        <h1 className="text-4xl font-bold text-white">
          Happy Independence Day!
        </h1>
      </header>

      <main className="flex flex-col items-center justify-center mt-8">
        {/* Indian Flag Animation */}
        <div className="relative flex flex-col items-center justify-center">
          {/* Orange Band */}
          <motion.div
            className="w-32 h-8 bg-orange-600 rounded-t-md"
            animate={{ y: [0, 10, 0] }}
            transition={{ repeat: Infinity, duration: 1.5 }}
          ></motion.div>

          {/* White Band */}
          <div className="w-32 h-8 bg-white"></div>

          {/* Green Band */}
          <motion.div
            className="w-32 h-8 bg-green-600 rounded-b-md"
            animate={{ y: [0, -10, 0] }}
            transition={{ repeat: Infinity, duration: 1.5 }}
          ></motion.div>

          {/* Ashoka Chakra */}
          <div className="absolute top-[30%] left-[48%] w-6 h-6 bg-blue-600 rounded-full flex items-center justify-center">
            <div className="w-4 h-4 border-2 border-white rounded-full"></div>
          </div>
        </div>

        <p className="text-white text-lg mt-6">Celebrate freedom and unity!</p>
      </main>
    </div>
  );
};

export default IndependenceDay;
