// src/api.js

import axios from 'axios';

// Create an axios instance with the base URL
const api = axios.create({
  baseURL: 'http://localhost:9090', // Backend URL
});

export default api;
