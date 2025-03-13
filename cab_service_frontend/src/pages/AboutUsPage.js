  import React from 'react';
  import '../style/AboutUsPage.css';
  import Navbar from '../components/Navbar';

  function AboutUsPage() {
    return (
  <div>
      <Navbar />
    
      <div className="aboutus-container"> 
        <h1>About Us</h1>
        <p>
        Welcome to MegaCityCab! We are committed to providing fast, reliable, and affordable transportation solutions for all our customers.
        Whether you're heading to work, traveling for leisure, or need a ride to the airport, we've got you covered.
        </p>
        <h2>Our Mission</h2>
        <p>
          Our mission is to offer the most convenient, safe, and affordable rides to our customers while ensuring top-quality service. 
          We aim to revolutionize urban transportation and make it accessible for everyone, everywhere.
        </p>
        <h2>Our Values</h2>
        <ul>
          <li><strong>Safety First:</strong> We ensure all rides are safe, with thoroughly vetted drivers and regular vehicle maintenance.</li>
          <li><strong>Customer Satisfaction:</strong> Our customers are our top priority. We strive to meet their needs and exceed their expectations.</li>
          <li><strong>Integrity:</strong> We pride ourselves on transparency, honesty, and providing reliable services at fair prices.</li>
        </ul>
        <div className="contact-info">
          <h3>Contact Us:</h3>
          <p>Email: megacitycab@gmail.com</p>
          <p>Phone: 011-1234567</p>
        </div>
      </div>
  </div>
    );
  }

  export default AboutUsPage;
