import React, { useState } from "react";
import "./Popup.css"; // CSS for popup styling

const Popup = () => {
  const [showPopup, setShowPopup] = useState(true);

  const closePopup = () => {
    setShowPopup(false);
  };

  return (
    showPopup && (
      <div className="popup-overlay">
        <div className="popup">
          <h2 className="popup-title">⚠️ Under Construction</h2>
          <p className="popup-message">
            This site is currently under construction. Sorry for inconvience!
          </p>
          <button className="popup-close-btn" onClick={closePopup}>
            Close
          </button>
        </div>
      </div>
    )
  );
};

export default Popup;
