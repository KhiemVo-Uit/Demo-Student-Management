import React, { useEffect, useState } from 'react';
import { fetchStudents, deleteStudent } from '../services/studentService';
import {
  Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, Typography, CircularProgress,
  Alert, Pagination, IconButton, Snackbar
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import MuiAlert from '@mui/material/Alert';

function StudentsPage() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [successMsg, setSuccessMsg] = useState('');
  const [openSnackbar, setOpenSnackbar] = useState(false);
  const [deletingId, setDeletingId] = useState(null);

  const [currentPage, setCurrentPage] = useState(1);
  const studentsPerPage = 10;

  useEffect(() => {
    loadStudents();
  }, []);

  const loadStudents = () => {
    setLoading(true);
    fetchStudents()
      .then(data => {
        setStudents(data);
        setLoading(false);
      })
      .catch(err => {
        setError(err.message);
        setLoading(false);
      });
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Bạn có chắc muốn xoá sinh viên này?')) return;

    try {
      setDeletingId(id);
      await deleteStudent(id);
      setStudents(prev => prev.filter(s => s.id !== id));
      setSuccessMsg('Xoá sinh viên thành công');
      setOpenSnackbar(true);
    } catch (err) {
      setError('Xoá sinh viên thất bại: ' + err.message);
    } finally {
      setDeletingId(null);
    }
  };

  const handleChangePage = (event, value) => {
    setCurrentPage(value);
  };

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
  };

  const indexOfLastStudent = currentPage * studentsPerPage;
  const indexOfFirstStudent = indexOfLastStudent - studentsPerPage;
  const currentStudents = students.slice(indexOfFirstStudent, indexOfLastStudent);
  const totalPages = Math.ceil(students.length / studentsPerPage);

  if (loading) {
    return <CircularProgress sx={{ display: 'block', margin: 'auto', mt: 4 }} />;
  }

  if (error) {
    return <Alert severity="error">Lỗi: {error}</Alert>;
  }

  return (
    <TableContainer component={Paper} sx={{ maxWidth: 1000, margin: 'auto', mt: 4 }}>
      <Typography variant="h5" component="h2" sx={{ p: 2, textAlign: 'center' }}>
        Danh sách sinh viên
      </Typography>
      <Table>
        <TableHead>
          <TableRow sx={{ backgroundColor: '#1976d2' }}>
            <TableCell sx={{ color: 'white' }}>ID</TableCell>
            <TableCell sx={{ color: 'white' }}>Họ tên</TableCell>
            <TableCell sx={{ color: 'white' }}>Email</TableCell>
            <TableCell sx={{ color: 'white' }}>Username</TableCell>
            <TableCell sx={{ color: 'white' }}>Hành động</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {currentStudents.length === 0 ? (
            <TableRow>
              <TableCell colSpan={5} align="center">Không có sinh viên nào.</TableCell>
            </TableRow>
          ) : (
            currentStudents.map((student) => (
              <TableRow key={student.id} hover>
                <TableCell>{student.id}</TableCell>
                <TableCell>{student.fullName}</TableCell>
                <TableCell>{student.email}</TableCell>
                <TableCell>{student.username}</TableCell>
                <TableCell>
                  <IconButton
                    color="error"
                    onClick={() => handleDelete(student.id)}
                    disabled={deletingId === student.id}
                  >
                    {deletingId === student.id ? (
                      <CircularProgress size={24} />
                    ) : (
                      <DeleteIcon />
                    )}
                  </IconButton>
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>

      <Pagination
        count={totalPages}
        page={currentPage}
        onChange={handleChangePage}
        color="primary"
        sx={{ display: 'flex', justifyContent: 'center', my: 2 }}
      />

      <Snackbar
        open={openSnackbar}
        autoHideDuration={3000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <MuiAlert onClose={handleCloseSnackbar} severity="success" sx={{ width: '100%' }}>
          {successMsg}
        </MuiAlert>
      </Snackbar>
    </TableContainer>
  );
}

export default StudentsPage;
