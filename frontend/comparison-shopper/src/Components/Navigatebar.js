import React, { useState } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import logo from './online-shopping.png';
import { useNavigate } from 'react-router-dom';
import './Navigatebar.css';
import { Link } from 'react-router-dom';

const Navigatebar = () => {
  const [searchValue, setSearchValue] = useState('');
  const handleChange = (e) => {
    setSearchValue(e.target.value);
  };


  const navigate = useNavigate();

  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
        // Change this to search results page using searchValue
        navigate(`/search?search=${searchValue}`);
    }
  }
  
  return (
      <Navbar expand="lg" className="navbarStyle">
        <Navbar.Brand href="#">
          <img alt="" src={logo} width="40" height="40" className="d-inline-block align-top" />{' '}
        </Navbar.Brand>
        <Nav className="me-auto">
          <Link to="/" component={Nav.Link}>Home</Link>
          <Link to="/search" component={Nav.Link}>Products</Link>
          <Link to="/cart" component={Nav.Link}>Cart</Link>
          <Link to="/user" component={Nav.Link}>User profile</Link>
        </Nav>
        <div className="searchBarContainer">
          <input
          className="searchBar"
          type="search"
          value={searchValue}
          onChange={handleChange}
          onKeyPress={handleKeyDown}
          placeholder="Find your latest deal today. &#x1F50E; "
        />
        </div>
      </Navbar>
  )
}

export default Navigatebar;
