import { useNavigate } from "react-router-dom";
import { login } from "../services/authService";

function LoginPage() {
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    const username = e.target.username.value;
    const password = e.target.password.value;

    try {
      const { token, role, userDTO } = await login(username, password);
      localStorage.setItem("token", token);
      localStorage.setItem("role", role);
      localStorage.setItem("user", JSON.stringify(userDTO));

      // ✅ Điều hướng trang dựa theo role
      if (role === "ROLE_ADMIN") {
        navigate("/admin");
      } else {
        navigate("/Student");
      }
    } catch (err) {
      console.error("Login error:", err.response?.data || err.message);
      alert("Đăng nhập thất bại");
    }
  };

  return (
    <form onSubmit={handleLogin}>
      <input name="username" placeholder="Username" />
      <input name="password" type="password" placeholder="Password" />
      <button type="submit">Login</button>
    </form>
  );
}

export default LoginPage;
