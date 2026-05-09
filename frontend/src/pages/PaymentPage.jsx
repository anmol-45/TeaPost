import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

function PaymentPage() {
  const location = useLocation();
  const navigate = useNavigate();

  const order = location.state?.order;

  if (!order) {
    return <h3>No order found</h3>;
  }

  const orderId = order.orderId || order.id || "unknown";
  const total = order.totalAmount ?? order.total ?? "N/A";

  const handlePayment = async () => {

  try {

    // STEP 1 -> Create payment/order in backend

        const token = localStorage.getItem("token");


    const response = await axios.post(
      "http://localhost:8081/api/v1/payments",
      {
        orderId: orderId,
        paymentMode: "ONLINE"
      },
      {
    headers: {
      Authorization: `Bearer ${token}`
    }
  }
    );

    const data = response.data;

    // STEP 2 -> Razorpay options

    const options = {

      key: data.razorpayKey,

      amount: data.amount * 100,

      currency: "INR",

      name: "TeaPost",

      description: "Tea Order Payment",

      order_id: data.razorpayOrderId,

      handler: async function (response) {

        // SUCCESS CALLBACK

        await axios.post(
          "http://localhost:8081/api/v1/payments/callback",
          {
            orderId: orderId,
            status: "SUCCESS",
                razorpayPaymentId: response.razorpay_payment_id,

    razorpayOrderId: response.razorpay_order_id,

    razorpaySignature: response.razorpay_signature
          },
          {
    headers: {
      Authorization: `Bearer ${token}`
    }
  }
        );

        navigate("/success", {
  state: {
    orderId: orderId,
    amount: data.amount,
    razorpayPaymentId: response.razorpay_payment_id
  }
});
      },

      prefill: {
        name: "Anmol",
        email: "test@test.com",
        contact: "9999999999"
      },

      theme: {
        color: "#3399cc"
      }
    };

    const razor = new window.Razorpay(options);

    razor.open();

  } catch (error) {

    console.error(error);

    alert("Payment Failed");
  }
};

  return (
    <div>
      <h2>Payment Page 💳</h2>

      <p>Order ID: {orderId}</p>
      <p>Total Amount: ₹ {total}</p>

      <button onClick={handlePayment}>
        Pay Now
      </button>
    </div>
  );
}

export default PaymentPage;