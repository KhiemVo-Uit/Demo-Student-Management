import axios from "axios";

const API_URL = "http://localhost:8080/auth";

export async function login(username, password) {
  const res = await axios.post(`${API_URL}/login`, {
    username,
    password
  });

  // Response là AuthResponse (token, role, userDTO)
  return res.data; // => { token, role, userDTO }
}
