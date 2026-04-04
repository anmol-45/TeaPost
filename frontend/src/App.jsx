import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import ProductPage from "./pages/ProductPage";
import SignupPage from "./pages/SignUpPage";
import ProtectedRoute from "./components/ProtectedRoute";
import CartPage from "./pages/CartPage";

function App() {
  return (
    <div>
      <h1>TeaPost E-commerce</h1>

      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route
          path="/products"
          element={
            <ProtectedRoute>
              <ProductPage />
            </ProtectedRoute>
          }
        />{" "}
        <Route path="/cart" element={<CartPage />} />
      </Routes>
    </div>
  );
}

export default App;
