import React, { useEffect, useState } from "react";
import api from "../api"; // axios instance đã setup token

function UserList() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/api/users")
      .then(res => {
        console.log("API users response:", res.data);
        setUsers(res.data);
      })
      .catch(err => {
        console.error("Error fetching users:", err);
        setError("Không tải được danh sách người dùng");
      });
  }, []);

  return (
    <div style={{ maxWidth: "700px", margin: "20px auto", fontFamily: "Arial, sans-serif" }}>
      <h2 style={{ textAlign: "center", color: "#333" }}>Danh sách người dùng</h2>
      {error && <p style={{ color: "red", textAlign: "center" }}>{error}</p>}

      <table style={{
        width: "100%",
        borderCollapse: "collapse",
        boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
        margin: "0 auto" // căn giữa bảng
      }}>
        <thead style={{ backgroundColor: "#007BFF", color: "#fff" }}>
          <tr>
            <th style={{ padding: "12px", border: "1px solid #ddd", textAlign: "center" }}>ID</th>
            <th style={{ padding: "12px", border: "1px solid #ddd", textAlign: "center" }}>Username</th>
          </tr>
        </thead>
        <tbody>
          {users.length === 0 && (
            <tr>
              <td colSpan="2" style={{ textAlign: "center", padding: "12px" }}>Không có dữ liệu</td>
            </tr>
          )}
          {users.map(user => (
            <tr key={user.id} style={{ borderBottom: "1px solid #ddd" }}>
              <td style={{ padding: "12px", border: "1px solid #ddd", textAlign: "center" }}>{user.id}</td>
              <td style={{ padding: "12px", border: "1px solid #ddd", textAlign: "center" }}>{user.username}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default UserList;
