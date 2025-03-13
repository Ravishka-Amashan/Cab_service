import React, { useState } from 'react';
import Navbar from '../components/Navbar';
import Header from '../components/Header';
import Footer from '../components/Footer';
import '../style/DriverDashboard.css';

function DriverDashboard() {
  const [assignedRides, setAssignedRides] = useState([
    { id: 1, pickup: 'Downtown', dropoff: 'Airport',  status: 'Pending' },
    { id: 2, pickup: 'Mall', dropoff: 'Hotel',  status: 'Pending' }
  ]);

  const updateStatus = (id, newStatus) => {
    setAssignedRides(prevRides =>
      prevRides.map(ride =>
        ride.id === id ? { ...ride, status: newStatus } : ride
      )
    );
  };

  return (
    <div className="driver-dashboard-container">
      <Navbar />
      <Header />

      <div className="driver-dashboard-content">
        {/* Assigned Rides Section */}
        <div className="assigned-rides-section">
          <h2>Assigned Rides</h2>
          <ul>
            {assignedRides.map(ride => (
              <li key={ride.id} className="ride-item">
                <p><strong>Pickup:</strong> {ride.pickup}</p>
                <p><strong>Dropoff:</strong> {ride.dropoff}</p>
                {/* <p><strong>Time:</strong> {ride.time}</p> */}
                <p><strong>Status:</strong> {ride.status}</p>
                <button onClick={() => updateStatus(ride.id, 'Ongoing')} disabled={ride.status !== 'Pending'}>Start Ride</button>
                <button onClick={() => updateStatus(ride.id, 'Completed')} disabled={ride.status !== 'Ongoing'}>Complete Ride</button>
              </li>
            ))}
          </ul>
        </div>
      </div>
      
      {/* <Footer /> */}
    </div>
  );
}

export default DriverDashboard;
