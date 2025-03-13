import React, { useState } from 'react';
import Navbar from '../components/Navbar';
import Header from '../components/Header';
import '../style/ContactUs.css';

function ContactUs() {
  return (
<div>
   <Navbar />
    <div className="contact-container">
      <h2>Contact Us</h2>
      <p>We’d love to hear from you! Reach out using the details below.</p>
      
      <div className="contact-details">
        <p><strong>Email:</strong> support@cabservice.com</p>
        <p><strong>Phone:</strong> +1 234 567 890</p>
        <p><strong>Address:</strong> 123 Transport Ave, City, Country</p>
      </div>

      <form className="contact-form">
        <label>Name:</label>
        <input type="text" placeholder="Enter your name" required />
        
        <label>Email:</label>
        <input type="email" placeholder="Enter your email" required />
        
        <label>Message:</label>
        <textarea placeholder="Type your message" required></textarea>
        
        <button type="submit">Send Message</button>
      </form>
    </div>
</div>
  );
}
export default ContactUs;