import './App.css';
import LandingPageContent from './Components/LandingPageContent.js';
import Navigatebar from './Components/Navigatebar.js';
import Footer from './Components/Footer.js';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import ProductPage from './Components/ProductPage.js';
import SearchResultsPage from './Components/SearchResultsPage.js';
import CheckoutPage from './Components/CheckoutPage.js';
import UserProfilePage from './Components/UserProfilePage.js';
import CartPage from './Components/CartPage.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useState, createContext } from 'react';

export const UserContext = createContext({
  user: null,
  setUser: () => {}
});

function App() {
  const [user, setUser] = useState({id: null});
  const userStateObject = { user, setUser };
  console.log(user)

  return (
    <>
    {/* Context Provider wraps the entire application so that any component can access the user state as long as they also import context and values. */}
      <UserContext.Provider value={userStateObject}>
        <Router>
          <Navigatebar/>
          <Routes>
            <Route path="/" element={<LandingPageContent />} />
            <Route path="/product" element={<ProductPage />} />
            <Route path="/search" element={<SearchResultsPage />} />
            <Route path="/checkout" element={<CheckoutPage />} />
            <Route path="/user" element={<UserProfilePage />} />
            <Route path="/cart" element={<CartPage />} />
            <Route path="/product/:productId" element={<ProductPage />} />
            <Route path="/search/:productId" element={<SearchResultsPage />} />
          </Routes>
          <Footer/>
        </Router>
      </UserContext.Provider>
    </>
  );
}

export default App;
