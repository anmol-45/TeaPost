import { useState } from "react";
import { useNavigate } from "react-router-dom";

function SignupPage() {

  const navigate = useNavigate();

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSignup = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8081/api/v1/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          firstName: firstName,
          lastName: lastName,
          email: email,
          password: password
        })
      });

      const data = await response.text(); // your API returns String

      console.log("Signup Response:", data);

      alert("Signup successful! Please login.");

      // redirect to login page
      navigate("/");

    } catch (error) {
      console.error(error);
      alert("Signup failed!");
    }
  };

  return (
    <div>
      <h2>Signup</h2>

      <form onSubmit={handleSignup}>

        <input
          type="text"
          placeholder="Enter FirstName"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />
        <br /><br />
        <input
          type="text"
          placeholder="Enter LastName"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />

        <br /><br />

        <input
          type="email"
          placeholder="Enter Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <br /><br />

        <input
          type="password"
          placeholder="Enter Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <br /><br />

        <button type="submit">Signup</button>

      </form>

      <br />

      <p>
        Already have an account?{" "}
        <button onClick={() => navigate("/")}>Login</button>
      </p>

    </div>
  );
}

export default SignupPage;