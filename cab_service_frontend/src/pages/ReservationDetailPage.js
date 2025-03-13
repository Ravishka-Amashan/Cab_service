import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../style/ReservationDetailPage.css';
import Navbar from '../components/Navbar';

function ReservationDetailPage() {
  const [reservation, setReservation] = useState(null);
  const [error, setError] = useState('');

  // This function simulates fetching reservation details based on a reservation ID (e.g. from URL or user input)
  const fetchReservationDetails = async (reservationId) => {
    try {
      // Assuming there's an API endpoint to get reservation details
      const response = await axios.get(`http://localhost:9090/api/reservations/${reservationId}`);
      setReservation(response.data);
    } catch (err) {
      setError('Error fetching reservation details. Please try again later.');
    }
  };

  useEffect(() => {
    const reservationId = 1; // Replace with dynamic reservation ID (e.g. from URL params)
    fetchReservationDetails(reservationId);
  }, []);

  if (error) {
    return <div className="error">{error}</div>;
  }

  if (!reservation) {
    return <div>Loading...</div>;
  }

  return (
    <div className="reservation-detail-container">
      <Navbar />
      <h1>Reservation Details</h1>
      <div className="reservation-details">
        <div className="reservation-item">
          <h3>Reservation ID:</h3>
          <p>{reservation.id}</p>
        </div>
        <div className="reservation-item">
          <h3>Customer Name:</h3>
          <p>{reservation.customerName}</p>
        </div>
        <div className="reservation-item">
          <h3>Pickup Location:</h3>
          <p>{reservation.pickupLocation}</p>
        </div>
        <div className="reservation-item">
          <h3>Dropoff Location:</h3>
          <p>{reservation.dropoffLocation}</p>
        </div>
        <div className="reservation-item">
          <h3>Reservation Date:</h3>
          <p>{new Date(reservation.reservationDate).toLocaleString()}</p>
        </div>
        <div className="reservation-item">
          <h3>Status:</h3>
          <p>{reservation.status}</p>
        </div>
      </div>
    </div>
  );
}

export default ReservationDetailPage;
