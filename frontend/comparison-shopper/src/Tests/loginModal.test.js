import { render, screen, fireEvent } from "@testing-library/react";
import LoginModal from '../Modals/loginModal.js';
import {UserContext} from '../App.js';

let container;
let utils;
/*Before each test execution, this will render the loginModal component within a context provider with a dummy dummyFunction
inside of it in a test environment to test.*/
beforeEach(() => {
  function dummyFunction(){return};
  const userStateObject = {dummyFunction};
  utils = render(
    <UserContext.Provider value={userStateObject}>
      <LoginModal show={true} />
    </UserContext.Provider>
  );
  container = utils.container;
});
//Tests to see whether the component itself renders properly within the HTML Document
test("Renders Login Form Properly", () => {
  expect(container).toBeInTheDocument();
});

//Tests to see whether the Email Input will return an invalid response given an empty string
test("Detects Empty Response in Email Input via Required Tag", () => {
  const input = utils.getByLabelText('Email');
  expect(input).toBeInvalid();
});
//Tests to see whether the Password Input will return an invalid response given an empty string
test("Detects Empty Response in Password Input via Required Tag", () => {
  const input = utils.getByLabelText('Password');
  expect(input).toBeInvalid();
});
