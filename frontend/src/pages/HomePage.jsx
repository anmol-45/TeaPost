import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function HomePage() {
  const [showPopup, setShowPopup] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    // Timer for popup after 5 seconds
    const timer = setTimeout(() => {
      setShowPopup(true);
    }, 5000); 

    return () => clearTimeout(timer);
  }, []);

  const handleClosePopup = () => {
    setShowPopup(false);
  };

  const handleLogin = () => {
    navigate("/login");
  };

  const handleSignup = () => {
    navigate("/signup");
  };

  return (
    <div style={{ textAlign: "center", padding: "50px" }}>
      <h1>Welcome to TeaPost</h1>
      <p>Your one-stop shop for tea products.</p>
      <button onClick={() => navigate("/products")}>Browse Products</button>

      {showPopup && (
        <div style={{
          position: "fixed",
          top: 0,
          left: 0,
          width: "100%",
          height: "100%",
          backgroundColor: "rgba(0,0,0,0.5)",
          display: "flex",
          justifyContent: "center",
          alignItems: "center"
        }}>
          <div style={{
            backgroundColor: "white",
            padding: "20px",
            borderRadius: "10px",
            textAlign: "center"
          }}>
            <h2>Join TeaPost</h2>
            <p>Sign up or log in to enjoy exclusive offers!</p>
            <button onClick={handleSignup}>Sign Up</button>
            <button onClick={handleLogin}>Log In</button>
            <button onClick={handleClosePopup}>Close</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default HomePage;