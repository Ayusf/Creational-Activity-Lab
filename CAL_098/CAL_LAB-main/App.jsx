import React, { useEffect, useState } from 'react';
import CourierWebsite from './components/CourierWebsite';

function App() {
  const [message, setMessage] = useState('');

  // useEffect(() => {
  //   fetch('http://localhost:8080/Demo1/HelloServlet')  // Your servlet URL
  //     .then(response => response.json())
  //     .then(data => {
  //       console.log("Response:", data);
  //       setMessage(data.message);
  //     })
  //     .catch(error => console.error('Fetch error:', error));
  // }, []);

  return (
    <div>
      {/* <h1>Servlet Response:</h1> */}
      <p>{message}</p>
      <CourierWebsite/>
    </div>
  );
}

export default App;
