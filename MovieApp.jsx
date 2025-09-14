import React, { useState, useEffect } from 'react';

// Main component for the movie booking app
const App = () => {
  const [movies, setMovies] = useState([]);
  const [bookingHistory, setBookingHistory] = useState([]);
  const [activeTab, setActiveTab] = useState('movies');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalMessage, setModalMessage] = useState('');
  const [adminPassword, setAdminPassword] = useState('');
  const [isAdmin, setIsAdmin] = useState(false);
  
  // This is the base URL for your Spring Boot API. Make sure it's correct.
  const API_BASE_URL = 'http://localhost:8080/movie';

  // State for the new movie form
  const [newMovie, setNewMovie] = useState({
    title: '',
    director: '',
    description: '',
    genre: '',
    date: '',
    location: '',
    totalSeats: 0,
    price: 0
  });

  // Fetch movies from the backend
  const fetchMovies = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`${API_BASE_URL}`);
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();
      setMovies(data);
    } catch (e) {
      console.error("Error fetching movies:", e);
      setError(`Failed to fetch movies. Please ensure your Spring Boot backend is running and the URL is correct (${API_BASE_URL}).`);
    } finally {
      setLoading(false);
    }
  };

  // Fetch booking history (simulated for this version)
  const fetchHistory = async () => {
    // In a real app, this would be a user-specific API call to the backend
    setLoading(true);
    setError(null);
    try {
      // Since your backend doesn't have a user-specific history endpoint yet,
      // we'll just show a placeholder. In a future step, we can update this.
      setBookingHistory([]);
    } catch (e) {
      console.error("Error fetching booking history:", e);
      setError("Failed to fetch booking history.");
    } finally {
      setLoading(false);
    }
  };

  // Handle ticket booking
  const handleBooking = async (movieId, quantity, price) => {
    if (quantity <= 0) {
      setModalMessage('Please select at least one ticket.');
      setModalOpen(true);
      return;
    }
    setLoading(true);
    try {
      const totalPrice = quantity * price;
      const response = await fetch(`${API_BASE_URL}/booking/${movieId}/${quantity}/${totalPrice}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
      });
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      setModalMessage(`Successfully booked ${quantity} ticket(s)! Total price: $${totalPrice}`);
      setModalOpen(true);
      // Refresh movie list to show updated seats
      fetchMovies();
      // In a real app, we would also fetch history here
    } catch (e) {
      console.error("Error booking tickets:", e);
      setModalMessage(`Failed to book tickets. Please try again later. Error: ${e.message}`);
      setModalOpen(true);
    } finally {
      setLoading(false);
    }
  };
  
  // Handle new movie submission
  const handleAddMovie = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await fetch(API_BASE_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newMovie),
      });
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      setModalMessage('Movie added successfully!');
      setModalOpen(true);
      setNewMovie({
        title: '', director: '', description: '', genre: '',
        date: '', location: '', totalSeats: 0, price: 0
      });
      fetchMovies();
    } catch (e) {
      console.error("Error adding movie:", e);
      setModalMessage(`Failed to add movie. Error: ${e.message}`);
      setModalOpen(true);
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteMovie = async (movieId) => {
    setLoading(true);
    try {
      const response = await fetch(`${API_BASE_URL}/${movieId}`, {
        method: 'DELETE',
      });
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      setModalMessage('Movie deleted successfully!');
      setModalOpen(true);
      fetchMovies();
    } catch (e) {
      console.error("Error deleting movie:", e);
      setModalMessage(`Failed to delete movie. Error: ${e.message}`);
      setModalOpen(true);
    } finally {
      setLoading(false);
    }
  };

  const handleAdminLogin = (e) => {
    e.preventDefault();
    if (adminPassword === 'admin') {
      setIsAdmin(true);
      setModalMessage('Admin login successful!');
      setModalOpen(true);
      setAdminPassword('');
    } else {
      setModalMessage('Incorrect password.');
      setModalOpen(true);
    }
  };

  // Fetch movies on initial load
  useEffect(() => {
    fetchMovies();
  }, []);

  // Modal Component
  const Modal = ({ message, onClose }) => (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-gray-900 bg-opacity-75">
      <div className="bg-gray-800 rounded-xl p-6 shadow-2xl max-w-sm w-full text-center border-2 border-green-500">
        <p className="text-white text-lg font-semibold mb-4">{message}</p>
        <button
          onClick={onClose}
          className="w-full px-4 py-2 bg-green-600 hover:bg-green-700 transition-colors duration-200 rounded-lg text-white font-bold"
        >
          OK
        </button>
      </div>
    </div>
  );

  return (
    <div className="bg-gray-900 min-h-screen text-gray-200 font-sans">
      <style>{`
        body {
          font-family: 'Inter', sans-serif;
        }
      `}</style>
      {/* Navbar */}
      <nav className="sticky top-0 z-10 bg-gray-900 shadow-xl">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <h1 className="text-3xl font-bold text-green-500">MovieMania</h1>
          <div className="flex space-x-4">
            <button
              onClick={() => setActiveTab('movies')}
              className={`flex items-center space-x-2 px-4 py-2 rounded-full transition-colors duration-200 ${
                activeTab === 'movies' ? 'bg-green-600 text-white' : 'bg-gray-800 text-gray-300 hover:bg-gray-700'
              }`}
            >
              <span>🎟️</span>
              <span>Movies</span>
            </button>
            <button
              onClick={() => setActiveTab('history')}
              className={`flex items-center space-x-2 px-4 py-2 rounded-full transition-colors duration-200 ${
                activeTab === 'history' ? 'bg-green-600 text-white' : 'bg-gray-800 text-gray-300 hover:bg-gray-700'
              }`}
            >
              <span>📜</span>
              <span>History</span>
            </button>
            <button
              onClick={() => setActiveTab('admin')}
              className={`flex items-center space-x-2 px-4 py-2 rounded-full transition-colors duration-200 ${
                activeTab === 'admin' ? 'bg-green-600 text-white' : 'bg-gray-800 text-gray-300 hover:bg-gray-700'
              }`}
            >
              <span>👤</span>
              <span>Admin</span>
            </button>
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <main className="container mx-auto p-8">
        {loading && <div className="text-center text-xl mt-10">Loading...</div>}
        {error && <div className="text-center text-red-500 mt-10">{error}</div>}
        {!loading && !error && (
          <>
            {/* Movies Tab */}
            {activeTab === 'movies' && (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
                {movies.map(movie => (
                  <div key={movie.id} className="bg-gray-800 rounded-xl shadow-lg p-6 flex flex-col justify-between transform transition-transform duration-300 hover:scale-[1.02] border-2 border-gray-700">
                    <div>
                      <h2 className="text-2xl font-bold text-green-400 mb-2">{movie.title}</h2>
                      <p className="text-sm text-gray-400 mb-1">Directed by: {movie.director}</p>
                      <p className="text-sm text-gray-400 mb-1">Genre: {movie.genre}</p>
                      <p className="text-sm text-gray-400 mb-1">Showing at: {movie.location}</p>
                      <p className="text-sm text-gray-400 mb-1">Date: {movie.date}</p>
                      <p className="text-sm text-gray-400 mb-4">{movie.description}</p>
                      <div className="text-sm font-bold text-gray-300 mb-4">
                        <span className="text-lg text-yellow-400">$</span>{movie.price}
                        <span className="ml-4 text-gray-500">Seats Available: {movie.availableSeats}</span>
                      </div>
                    </div>
                    <div className="mt-4">
                      <div className="flex items-center justify-between mb-2">
                        <input
                          type="number"
                          min="1"
                          max={movie.availableSeats}
                          defaultValue="1"
                          className="w-1/3 px-3 py-2 rounded-lg bg-gray-700 text-gray-200 focus:outline-none focus:ring-2 focus:ring-green-500"
                          id={`tickets-${movie.id}`}
                        />
                        <span className="text-gray-400">tickets</span>
                      </div>
                      <button
                        onClick={() => handleBooking(movie.id, parseInt(document.getElementById(`tickets-${movie.id}`).value), movie.price)}
                        className="w-full px-4 py-2 bg-green-600 hover:bg-green-700 transition-colors duration-200 rounded-lg text-white font-bold"
                      >
                        Book Tickets
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            )}

            {/* History Tab */}
            {activeTab === 'history' && (
              <div className="bg-gray-800 rounded-xl p-6 shadow-lg">
                <h2 className="text-2xl font-bold mb-4 text-green-400">Booking History</h2>
                {bookingHistory.length === 0 ? (
                  <p className="text-gray-400 text-center">No booking history available. Book some tickets to see them here!</p>
                ) : (
                  <ul>
                    {bookingHistory.map((booking, index) => (
                      <li key={index} className="border-b border-gray-700 py-4 last:border-b-0">
                        <p>Movie: {booking.movieTitle}</p>
                        <p>Tickets: {booking.quantity}</p>
                        <p>Total Price: ${booking.totalPrice}</p>
                      </li>
                    ))}
                  </ul>
                )}
              </div>
            )}
            
            {/* Admin Tab */}
            {activeTab === 'admin' && (
              <div className="bg-gray-800 rounded-xl p-6 shadow-lg">
                <h2 className="text-2xl font-bold mb-4 text-green-400">Admin Panel</h2>
                {!isAdmin ? (
                  <form onSubmit={handleAdminLogin} className="space-y-4 max-w-md mx-auto">
                    <input
                      type="password"
                      placeholder="Enter Admin Password"
                      value={adminPassword}
                      onChange={(e) => setAdminPassword(e.target.value)}
                      className="w-full px-4 py-2 rounded-lg bg-gray-700 text-gray-200 focus:outline-none focus:ring-2 focus:ring-green-500"
                    />
                    <button type="submit" className="w-full px-4 py-2 bg-green-600 hover:bg-green-700 transition-colors duration-200 rounded-lg text-white font-bold">
                      Login as Admin
                    </button>
                  </form>
                ) : (
                  <div className="space-y-8">
                    {/* Add New Movie Section */}
                    <div className="bg-gray-700 p-6 rounded-xl shadow-inner">
                      <h3 className="text-xl font-semibold mb-4 text-gray-200">Add New Movie</h3>
                      <form onSubmit={handleAddMovie} className="space-y-4">
                        <input type="text" placeholder="Title" value={newMovie.title} onChange={(e) => setNewMovie({...newMovie, title: e.target.value})} className="w-full px-4 py-2 rounded-lg bg-gray-800 text-gray-200" required />
                        <input type="text" placeholder="Director" value={newMovie.director} onChange={(e) => setNewMovie({...newMovie, director: e.target.value})} className="w-full px-4 py-2 rounded-lg bg-gray-800 text-gray-200" required />
                        <textarea placeholder="Description" value={newMovie.description} onChange={(e) => setNewMovie({...newMovie, description: e.target.value})} className="w-full px-4 py-2 rounded-lg bg-gray-800 text-gray-200" required></textarea>
                        <input type="text" placeholder="Genre" value={newMovie.genre} onChange={(e) => setNewMovie({...newMovie, genre: e.target.value})} className="w-full px-4 py-2 rounded-lg bg-gray-800 text-gray-200" required />
                        <input type="date" placeholder="Date" value={newMovie.date} onChange={(e) => setNewMovie({...newMovie, date: e.target.value})} className="w-full px-4 py-2 rounded-lg bg-gray-800 text-gray-200" required />
                        <input type="text" placeholder="Location" value={newMovie.location} onChange={(e) => setNewMovie({...newMovie, location: e.target.value})} className="w-full px-4 py-2 rounded-lg bg-gray-800 text-gray-200" required />
                        <input type="number" placeholder="Total Seats" value={newMovie.totalSeats} onChange={(e) => setNewMovie({...newMovie, totalSeats: parseInt(e.target.value)})} className="w-full px-4 py-2 rounded-lg bg-gray-800 text-gray-200" required />
                        <input type="number" step="0.01" placeholder="Price" value={newMovie.price} onChange={(e) => setNewMovie({...newMovie, price: parseFloat(e.target.value)})} className="w-full px-4 py-2 rounded-lg bg-gray-800 text-gray-200" required />
                        <button type="submit" className="w-full px-4 py-2 bg-green-600 hover:bg-green-700 rounded-lg text-white font-bold transition-colors">
                          Add Movie
                        </button>
                      </form>
                    </div>

                    {/* Manage Movies Section */}
                    <div className="bg-gray-700 p-6 rounded-xl shadow-inner">
                      <h3 className="text-xl font-semibold mb-4 text-gray-200">Manage Movies</h3>
                      <ul className="space-y-4">
                        {movies.map(movie => (
                          <li key={movie.id} className="flex justify-between items-center bg-gray-800 rounded-lg p-4">
                            <span className="text-gray-300 font-medium">{movie.title}</span>
                            <button
                              onClick={() => handleDeleteMovie(movie.id)}
                              className="px-4 py-2 bg-red-600 hover:bg-red-700 transition-colors duration-200 rounded-lg text-white font-bold"
                            >
                              Delete
                            </button>
                          </li>
                        ))}
                      </ul>
                    </div>
                  </div>
                )}
              </div>
            )}
          </>
        )}
      </main>
      {modalOpen && <Modal message={modalMessage} onClose={() => setModalOpen(false)} />}
    </div>
  );
};

export default App;
