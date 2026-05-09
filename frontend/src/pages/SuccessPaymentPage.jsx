import { useLocation } from "react-router-dom";

function SuccessPage() {

  const location = useLocation();

  const payment = location.state;

  return (

    <div>

      <h2>🎉 Payment Successful</h2>

      <p>Order ID: {payment?.orderId}</p>

      <p>Amount Paid: ₹ {payment?.amount}</p>

      <p>Transaction ID: {payment?.razorpayPaymentId}</p>

    </div>
  );
}

export default SuccessPage;