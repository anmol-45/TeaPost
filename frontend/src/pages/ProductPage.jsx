import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function ProductPage() {

  const [products, setProducts] = useState([]);
  const navigate = useNavigate(); 

  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8081/api/v1/products", {
      headers: {
        "Authorization": `Bearer ${token}`
      }
    })
      .then(res => res.json())
      .then(data => {
        console.log("Products:", data);
        setProducts(data.content || []);
      })
      .catch(err => console.error(err));

  }, []);

  // ✅ Add to cart
  const handleAddToCart = (productId) => {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    fetch("http://localhost:8081/api/v1/cart/items", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify({
        userId: userId,
        productId: productId,
        quantity: 1
      })
    })
      .then(res => res.json())
      .then(data => {
        console.log("Added to cart:", data);
        alert("✅ Added to cart!");
      })
      .catch(err => {
        console.error(err);
        alert("❌ Failed to add to cart");
      });
  };

  return (
    <div style={{ padding: "20px" }}>

      {/* ✅ Header with Cart button */}
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <h2>Products</h2>

        <button
          onClick={() => navigate("/cart")}
          style={{
            padding: "8px 12px",
            backgroundColor: "black",
            color: "white",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer"
          }}
        >
          Go to Cart 🛒
        </button>
      </div>

      {/* ✅ Product Grid */}
      <div
        style={{
          display: "flex",
          flexWrap: "wrap",
          gap: "20px",
          marginTop: "20px"
        }}
      >
        {products.map((product) => (
          <div
            key={product.productId}
            style={{
              border: "1px solid #ddd",
              padding: "16px",
              borderRadius: "10px",
              width: "250px",
              boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
              backgroundColor: "#fff"
            }}
          >
            <h3>{product.name}</h3>

            <p style={{ fontSize: "14px", color: "#555" }}>
              {product.description}
            </p>

            <p><strong>₹ {product.price}</strong></p>

            <p>⭐ {product.rating || "No rating"}</p>

            <p style={{ color: product.stock > 0 ? "green" : "red" }}>
              {product.stock > 0 ? `In Stock: ${product.stock}` : "Out of Stock"}
            </p>

            <button
              onClick={() => handleAddToCart(product.productId)}
              style={{
                marginTop: "10px",
                padding: "8px 12px",
                backgroundColor: "#007bff",
                color: "white",
                border: "none",
                borderRadius: "5px",
                cursor: "pointer"
              }}
            >
              Add to Cart
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ProductPage;