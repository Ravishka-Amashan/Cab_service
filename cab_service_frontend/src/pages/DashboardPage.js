import React, { useState } from 'react';
import axios from 'axios';
import Navbar from '../components/Navbar';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../style/DashboardPage.css';
import api from '../api';

function DashboardPage() {
  const [formData, setFormData] = useState({
    pickupLocation: '',
    dropoffLocation: '',
    // date: '',
    // time: '',
    carType: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await api.post('/api/reservations', formData);
      console.log(response)

      if (response.status === 201) {
        console.log('Booking Successful:', response.data);
        alert('Booking Successful!');

        // Reset form after submission
        setFormData({
          pickupLocation: '',
          dropoffLocation: '',
          // date: '',
          // time: '',
          carType: '',
        });
      }
    } catch (error) {
      console.error('Booking failed:', error.response?.data || error.message);
      alert('Booking failed. Please try again.');
    }
  };

  return (
    <div className="dashboard-container">
      <Navbar />
      <Header />

      <div className="dashboard-content">
        {/* Reservations Section */}
        <div className="reservations-section">
          <h2>Manage Your Reservations</h2>
          <p>
            "Take control of your travel plans with ease. Our seamless reservation system allows you to book, modify, or cancel your 
            rides effortlessly. Whether you're planning a daily commute or a special trip, we ensure a hassle-free experience with 
            real-time updates and secure booking. Ride with comfort, arrive with confidence."
          </p>
        </div>

        {/* Booking Form Section */}
        <div className="booking-section">
          <h3>Make a Booking</h3>
          <form onSubmit={handleSubmit} className="booking-form">
            <div className="form-group">
              <label htmlFor="pickupLocation">Pickup Location</label>
              <input
                type="text"
                id="pickupLocation"
                name="pickupLocation"
                value={formData.pickupLocation}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="dropoffLocation">Dropoff Location</label>
              <input
                type="text"
                id="dropoffLocation"
                name="dropoffLocation"
                value={formData.dropoffLocation}
                onChange={handleChange}
                required
              />
            </div>

            {/* <div className="form-group">
              <label htmlFor="date">Date</label>
              <input
                type="date"
                id="date"
                name="date"
                value={formData.date}
                onChange={handleChange}
                required
              />
            </div> */}

            {/* <div className="form-group">
              <label htmlFor="time">Time</label>
              <input
                type="time"
                id="time"
                name="time"
                value={formData.time}
                onChange={handleChange}
                required
              />
            </div> */}

            <div className="form-group">
              <label htmlFor="carType">Car Type</label>
              <select
                id="carType"
                name="carType"
                value={formData.carType}
                onChange={handleChange}
                required
              >
                <option value="">Select Car Type</option>
                <option value="Car">Car</option>
                <option value="Van">Van</option>
                <option value="Luxury Vehicle">Luxury Vehicle</option>
              </select>
            </div>

            <button type="submit">Submit Booking</button>
          </form>
        </div>
      </div>

      <Footer />
    </div>
  );
}

export default DashboardPage;
