import api from "./api";
import { jwtDecode } from "jwt-decode";  // dùng named import đúng cách

const TOKEN_KEY = "jwt_token";

export const login = async (username, password) => {
  const response = await api.post("/auth/login", { username, password });
  const { token } = response.data;
  localStorage.setItem(TOKEN_KEY, token);
  return token;
};

export const logout = () => {
  localStorage.removeItem(TOKEN_KEY);
};

export const getToken = () => localStorage.getItem(TOKEN_KEY);

export const getRole = () => {
  const token = getToken();
  if (!token) return null;

  try {
    const decoded = jwtDecode(token);
    // Token phải chứa claim "role", ví dụ: { "sub": "user1", "role": "ADMIN" }
    const role = decoded.role;
    return role || null;
  } catch (error) {
    console.error("Failed to decode token:", error);
    return null;
  }
};

export const isLoggedIn = () => {
  const token = getToken();
  if (!token) return false;

  try {
    const { exp } = jwtDecode(token);
    if (!exp) return false;

    const now = Date.now() / 1000; // thời gian hiện tại tính bằng giây
    return exp > now;
  } catch (error) {
    return false;
  }
};

// ✅ (Tuỳ chọn) Nếu muốn lấy role từ backend qua API nếu token không chứa role:
export const fetchRoleFromBackend = async () => {
  const token = getToken();
  if (!token) return null;

  try {
    const response = await api.get("/auth/role", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data; // ví dụ: "ADMIN"
  } catch (error) {
    console.error("Lấy role từ backend thất bại:", error);
    return null;
  }
};
