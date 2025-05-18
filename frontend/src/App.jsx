import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import AdminPage from "./pages/AdminPage";
import StudentHome from "./pages/StudentHome";
import StudentsPage from './pages/StudentsPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/admin" element={<AdminPage />} />
      <Route path="/Student" element={<StudentHome />} />
      <Route path="/admin/students" element={<StudentsPage />} />
    </Routes>
  );
}

export default App;
