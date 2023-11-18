import React, {useState, useContext} from 'react';
import './LandingPageContent.css';
import SignUpModal from '../Modals/signUpModal.js';
import LoginModal from '../Modals/loginModal.js';
import { useNavigate } from 'react-router-dom';
import {UserContext} from '../App.js';

const LandingPageContent = () => {
  const [displaySignUp, setDisplaySignUpOn] = useState(false);
  const [displayLogin, setDisplayLogin] = useState(false);
  const [searchValue, setSearchValue] = useState('');
  const {user, setUser} = useContext(UserContext);
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
    <>
      <SignUpModal
        show={displaySignUp}
        onHide={() => setDisplaySignUpOn(false)}
      />
      <LoginModal
        show={displayLogin}
        onHide={() => setDisplayLogin(false)}
      />
      <div className="companyTitle">
        <p>Brokie<span>Hub</span></p>
      </div>
      <div className="searchBar">
      <input
        type="search"
        value={searchValue}
        onChange={handleChange}
        onKeyPress={handleKeyDown}
        placeholder="Find your latest deal today. &#x1F50E; "
      />
      </div>

      <div className="buttonContainer">
        {JSON.stringify(user) === "{}" ? (
            <>
                <button type="button" className="signUpBtn" onClick={() => setDisplaySignUpOn(true)}>Sign Up!</button>
                <button type="button" className="loginBtn" onClick={() => setDisplayLogin(true)}>Login</button>
            </>
        ) : (
            <>
                <p>Welcome {user.Name}!</p>
                <button type="button" className="logoutBtn" onClick={() => setUser({})}>Logout</button>
            </>
        )}
    </div>
    </>
  )
}

export default LandingPageContent;
