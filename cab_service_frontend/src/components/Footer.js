import React from 'react';
import '../style/Footer.css';

function Footer() {
  return (
    <footer className="footer">
      <p>&copy; 2025 Your Company. All rights reserved.</p>
      <p>
        <a href="/terms">Terms of Service</a> | <a href="/privacy">Privacy Policy</a>
      </p>
    </footer>
  );
}

export default Footer;
