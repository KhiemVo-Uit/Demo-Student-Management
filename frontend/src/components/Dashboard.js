import React, { useEffect, useState } from "react";
import { getRole } from "../authService";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const [role, setRole] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const r = getRole();
    setRole(r);
  }, []);

  const handleLogout = () => {
    navigate("/logout");
  };

  return (
    <div
      style={{
        maxWidth: "400px",
        margin: "50px auto",
        padding: "20px",
        boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
        borderRadius: "10px",
        fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
        textAlign: "center",
        backgroundColor: "#f9f9f9",
      }}
    >
      <h2 style={{ color: "#333", marginBottom: "20px" }}>Dashboard</h2>
      <p style={{ fontSize: "18px", marginBottom: "30px" }}>
        Role của bạn:{" "}
        <span style={{ fontWeight: "bold", color: "#007BFF" }}>
          {role ? role : "Chưa có role"}
        </span>
      </p>
      <button
        onClick={handleLogout}
        style={{
          backgroundColor: "#007BFF",
          color: "#fff",
          border: "none",
          padding: "12px 25px",
          borderRadius: "6px",
          cursor: "pointer",
          fontSize: "16px",
          transition: "background-color 0.3s ease",
        }}
        onMouseEnter={(e) => (e.target.style.backgroundColor = "#0056b3")}
        onMouseLeave={(e) => (e.target.style.backgroundColor = "#007BFF")}
      >
        Đăng xuất
      </button>
    </div>
  );
}

export default Dashboard;
