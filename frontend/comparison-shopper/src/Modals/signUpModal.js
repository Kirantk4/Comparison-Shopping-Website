import React, {useState, useContext} from 'react';
import { Modal } from 'react-bootstrap';
import {createNewUser} from '../Data/repository.js';
import {UserContext} from '../App.js';

/*The component makes use of bootstrap modal in order to render the form.*/
const SignUpModal = (props) => {
  const {setUser} = useContext(UserContext); //Import User Context in order to Access Method
  //Input states in order to save whatever inputs the user gives on press rather than rely on onSubmit
  const [emailInput, setEmailInput] = useState("");
  const [usernameInput, setUsernameInput] = useState("");
  const [addressInput, setAddressInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const [errorDetected, setErrorDetected] = useState(null);
  async function handleSubmit(event) {
    event.preventDefault();
    //Regex that should cover 99.99% of emails to validate email inputs.
    // eslint-disable-next-line no-control-regex
    const emailRegex = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/
    if (!emailRegex.test(emailInput)){
      setErrorDetected("Email does not meet formatting standards.");
    } else {
      /*First creates a new user in the database. If database returns a positive result, then proceed with setting the user in the Context
      and logging them in.*/
      const result = await createNewUser(emailInput, usernameInput, addressInput, passwordInput);
      if (result) {
        const newUser = {
          Email: emailInput,
          Name: usernameInput,
          Address: addressInput
        };
        setUser(newUser);
        props.onHide();
      } else {
        setErrorDetected("User already exists with email account.");
      }
    }
  }

  return (
    <Modal show={props.show} onHide={props.onHide}>
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Welcome to Brokie Hub
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email" className="control-label">Email</label>
            <input name="email" id="email" className="form-control" placeholder='example@gmail.com' onChange={e => setEmailInput(e.target.value)} required />
          </div>
          <div className="form-group">
            <label htmlFor="name" className="control-label">Username</label>
            <input name="name" id="name" className="form-control" onChange={e => setUsernameInput(e.target.value)} required />
          </div>
          <div className="form-group">
            <label htmlFor="address" className="control-label">Address</label>
            <input name="address" id="address" className="form-control" onChange={e => setAddressInput(e.target.value)} required />
          </div>
          <div className="form-group">
            <label htmlFor="password" className="control-label">Password</label>
            <input type="password" name="password" id="password" className="form-control" onChange={e => setPasswordInput(e.target.value)} required />
          </div>
          <div className="form-group">
            <input type="submit" className="btn btn-primary" value="Sign Up" />
          </div>
        </form>
      </Modal.Body>
      {/*Footer displays error message when it detects the state has been set to something other than null*/}
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

export default SignUpModal;
