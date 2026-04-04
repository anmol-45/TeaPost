function ProductCard({ product }) {
  return (
    <div style={{
      border: "1px solid #ddd",
      padding: "16px",
      borderRadius: "8px",
      width: "250px"
    }}>
      <h3>{product.name}</h3>
      <p>{product.description}</p>
      <p><strong>₹ {product.price}</strong></p>
      <p>⭐ {product.rating}</p>
      <p>Stock: {product.stock}</p>

      <button>Add to Cart</button>
    </div>
  );
}

export default ProductCard;