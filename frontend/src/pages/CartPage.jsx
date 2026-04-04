import { useEffect, useState, useRef } from "react";

function CartPage() {
  const [cart, setCart] = useState(null);

  const debounceTimers = useRef({}); // store timers per item

  const UPDATE_DELAY = 1000; // ⏱ configurable delay

  const fetchCart = () => {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    fetch(`http://localhost:8081/api/v1/cart/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then((data) => {
        setCart(data);
      })
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    fetchCart();
  }, []);

  const handleRemove = (itemId) => {
    const token = localStorage.getItem("token");

    fetch(`http://localhost:8081/api/v1/cart/items/${itemId}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(() => {
        alert("Item removed");
        fetchCart();
      })
      .catch((err) => console.error(err));
  };

  // ✅ Debounced API call
  const updateQuantityDebounced = (itemId, newQty) => {
    const token = localStorage.getItem("token");

    if (newQty < 1) return;

    console.log("Debounce triggered for:", itemId, "Qty:", newQty);

    if (debounceTimers.current[itemId]) {
      clearTimeout(debounceTimers.current[itemId]);
    }

    debounceTimers.current[itemId] = setTimeout(() => {
      console.log("API CALL FIRING for item:", itemId);

      fetch(`http://localhost:8081/api/v1/cart/items/${itemId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          quantity: newQty,
        }),
      })
        .then((res) => res.json())
        .then((data) => {
          console.log("✅ Updated:", data);
          fetchCart();
        })
        .catch((err) => console.error("❌ Update failed:", err));
    }, UPDATE_DELAY);
  };

  // ✅ Instant UI update + debounce trigger
  const handleQuantityChange = (itemId, change) => {
    setCart((prevCart) => {
      const updatedItems = prevCart.items.map((item) => {
        if (item.cartItemId === itemId) {
          const newQty = item.quantity + change;
          if (newQty < 1) return item;

          // trigger debounce API
          updateQuantityDebounced(itemId, newQty);

          return { ...item, quantity: newQty };
        }
        return item;
      });

      return { ...prevCart, items: updatedItems };
    });
  };
  const handleCheckout = () => {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    fetch("http://localhost:8081/api/v1/orders", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        userId: userId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        console.log("Order placed:", data);
        alert(`✅ Order placed! ID: ${data.orderId}`);

        fetchCart(); // cart should now be empty
      })
      .catch((err) => {
        console.error(err);
        alert("❌ Order failed");
      });
  };

  if (!cart || !cart.items) {
    return <h3>Loading cart...</h3>;
  }

  return (
    <div style={{ padding: "20px" }}>
      <h2>Your Cart 🛒</h2>

      {cart.items.length === 0 ? (
        <p>Cart is empty</p>
      ) : (
        <>
          {cart.items.map((item) => (
            <div
              key={item.cartItemId}
              style={{
                border: "1px solid #ddd",
                padding: "12px",
                marginBottom: "10px",
                borderRadius: "8px",
              }}
            >
              <h4>{item.productName}</h4>
              <p>Price: ₹ {item.price}</p>

              {/* ✅ Quantity Controls */}
              <div
                style={{ display: "flex", alignItems: "center", gap: "10px" }}
              >
                <button
                  onClick={() => handleQuantityChange(item.cartItemId, -1)}
                >
                  ➖
                </button>

                <span>{item.quantity}</span>

                <button
                  onClick={() => handleQuantityChange(item.cartItemId, 1)}
                >
                  ➕
                </button>
              </div>

              <button
                onClick={() => handleRemove(item.cartItemId)}
                style={{
                  marginTop: "10px",
                  backgroundColor: "red",
                  color: "white",
                  border: "none",
                  padding: "6px 10px",
                  borderRadius: "5px",
                  cursor: "pointer",
                }}
              >
                Remove
              </button>
            </div>
          ))}

          <h3>Total: ₹ {cart.totalAmount}</h3>

          <button
            onClick={handleCheckout}
            style={{
              marginTop: "20px",
              padding: "10px 15px",
              backgroundColor: "green",
              color: "white",
              border: "none",
              borderRadius: "5px",
              cursor: "pointer",
            }}
          >
            Place Order 🧾
          </button>
        </>
      )}
    </div>
  );
}

export default CartPage;
