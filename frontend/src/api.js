// src/api.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // hoặc URL backend của bạn
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;
