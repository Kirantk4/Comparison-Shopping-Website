import React, {useState, useContext} from 'react';
// import {signupVerify} from "../Data/repository.js";
import { Modal } from 'react-bootstrap';
import {verifyLogin, retrieveUserData} from '../Data/repository.js';
import {UserContext} from '../App.js';

const LoginModal = (props) => {
  const {setUser} = useContext(UserContext); //Import User Context in order to Access Method
  //Input states in order to save whatever inputs the user gives on press rather than rely on onSubmit
  const [emailInput, setEmailInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const [errorDetected, setErrorDetected] = useState(null);
  /*Handle Submit queries user retrieval in order to check if the login is valid, and if it is,
  set it in the user state in App via cotnext. */
  async function handleSubmit(event) {
    event.preventDefault();
    const result = await verifyLogin(emailInput, passwordInput);
    console.log(result);
    if (result) {
      const data = await retrieveUserData(emailInput);
      const condensedUserArray = {
        UserID: data.id,
        Email: data.Email,
        Name: data.Name,
        Address: data.Address
      };
      setUser(condensedUserArray);
      props.onHide();
    } else {
        setErrorDetected("User could not be found with input. Please Check Username and Password are correct.");
    }
  }
  return (
    <Modal show={props.show} onHide={props.onHide}>
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Welcome Back to Brokie Hub
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email" className="control-label">Email</label>
            <input name="email" id="email" className="form-control" placeholder='example@gmail.com' onChange={e => setEmailInput(e.target.value)} required />
          </div>
          <div className="form-group">
            <label htmlFor="password" className="control-label">Password</label>
            <input type="password" name="password" id="password" className="form-control" onChange={e => setPasswordInput(e.target.value)} required />
          </div>
          <div className="form-group">
            <input type="submit" className="btn btn-primary" value="Login" />
          </div>
        </form>
      </Modal.Body>
      <Modal.Footer>
        {
          errorDetected != null ?
            <div className="form-group">
              <span className="text-danger">{errorDetected}</span>
            </div>
             : <div></div>
        }
      </Modal.Footer>
    </Modal>
  )
}

export default LoginModal;
