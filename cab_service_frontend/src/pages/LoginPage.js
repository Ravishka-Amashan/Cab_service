// src/pages/LoginPage.js

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api'; // Import the configured axios instance
import "../style/LoginPage.css";
import Navbar from '../components/Navbar';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Use the api instance to make the request
      const response = await api.post('/api/auth/login', { username, password });
      const role = response.data.user.role; 
      console.log(role)

      const { token } = response.data;
      localStorage.setItem('token', token);
      if(role === "DRIVER"){
        navigate('/driverdashboard')
      }
      else if(role === "USER"){
        navigate('/dashboard');
      }
    } catch (error) {
      setError('Invalid username or password!');
    }
  };

  const goToRegister = () => {
    navigate('/register'); // Navigate to register page
  };

  return (
  <div>
    <Navbar />
    <div className="login-container">
      <h1>Login Page</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Username:</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>
      <button onClick={goToRegister} className="register-button">Register</button>
    </div>
  </div>
  );
}

export default LoginPage;
