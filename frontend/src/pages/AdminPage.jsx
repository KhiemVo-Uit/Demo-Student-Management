import { Link } from 'react-router-dom';

function AdminPage() {
  return (
    <div>
      <h1>Admin Page</h1>
      <Link to="/admin/students">Danh sách sinh viên</Link>
    </div>
  );
}

export default AdminPage;
