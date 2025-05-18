import React, { useEffect } from "react";
import { logout } from "../authService";
import { useNavigate } from "react-router-dom";

function Logout() {
  const navigate = useNavigate();

  useEffect(() => {
    logout(); // Xóa token trong localStorage
    navigate("/"); // Quay về trang login
  }, [navigate]);

  return <p>Đang đăng xuất...</p>;
}

export default Logout;
