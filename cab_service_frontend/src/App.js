import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ReservationPage from './pages/ReservationPage'; // Import ReservationPage
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import DriverDashboard from './pages/DriverDashboard';
import AboutUsPage from './pages/AboutUsPage';
import ContactUs from './pages/ContactUs';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/reservation" element={<ReservationPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/aboutuspage" element={<AboutUsPage />} />
        <Route path="/driverdashboard" element={<DriverDashboard />} />
        <Route path="/contact" element={<ContactUs />} />
      </Routes>
    </Router>
  );
}

export default App;
