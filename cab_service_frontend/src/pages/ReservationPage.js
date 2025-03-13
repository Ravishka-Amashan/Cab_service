import React, { useState } from 'react';
import axios from 'axios';
import '../style/ReservationPage.css';
import Navbar from '../components/Navbar';

function ReservationPage() {
  const [pickupLocation, setPickupLocation] = useState('');
  const [dropoffLocation, setDropoffLocation] = useState('');
  const [reservationDate, setReservationDate] = useState('');
  const [customerName, setCustomerName] = useState('');
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleReservationSubmit = async (e) => {
    e.preventDefault();

    // Ensure all fields are filled
    if (!pickupLocation || !dropoffLocation || !reservationDate || !customerName) {
      setError('All fields are required!');
      return;
    }

    try {
      const reservationData = {
        pickupLocation,
        dropoffLocation,
        reservationDate,
        customerName
      };

      // Make POST request to create a reservation
      const response = await axios.post('http://localhost:9090/api/reservations', reservationData);

      // On success, show success message
      setSuccessMessage('Reservation created successfully!');
      setError('');
    } catch (err) {
      setError('Error creating reservation. Please try again later.');
      setSuccessMessage('');
    }
  };

  return (
  <div>
    <Navbar />
    <div className="reservation-container">
      <h1>Make a Reservation</h1>
      <form onSubmit={handleReservationSubmit}>
        <div>
          <label>Customer Name:</label>
          <input
            type="text"
            value={customerName}
            onChange={(e) => setCustomerName(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Pickup Location:</label>
          <input
            type="text"
            value={pickupLocation}
            onChange={(e) => setPickupLocation(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Dropoff Location:</label>
          <input
            type="text"
            value={dropoffLocation}
            onChange={(e) => setDropoffLocation(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Reservation Date:</label>
          <input
            type="datetime-local"
            value={reservationDate}
            onChange={(e) => setReservationDate(e.target.value)}
            required
          />
        </div>
        <button type="submit">Create Reservation</button>
      </form>

      {error && <div className="error">{error}</div>}
      {successMessage && <div className="success">{successMessage}</div>}
    </div>
  </div>

  );
}

export default ReservationPage;
