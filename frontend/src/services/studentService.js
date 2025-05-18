// src/services/studentService.js
export async function fetchStudents() {
  const token = localStorage.getItem('token');
  const response = await fetch('http://localhost:8080/admin/students', {
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
  if (!response.ok) {
    throw new Error(`Failed to fetch students: ${response.statusText}`);
  }
  const data = await response.json();
  return data;
}

export async function deleteStudent(id) {
  const token = localStorage.getItem('token');
  const response = await fetch(`http://localhost:8080/admin/students/${id}`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
  if (!response.ok) {
    throw new Error(`Failed to delete student: ${response.statusText}`);
  }
  return await response.json();
}
