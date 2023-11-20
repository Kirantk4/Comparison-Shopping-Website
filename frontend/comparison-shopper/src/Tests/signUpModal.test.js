import React from 'react';
import { createContext, useContext, useState } from 'react';
import { render, screen, fireEvent } from "@testing-library/react";
import SignUpModal from '../Modals/signUpModal.js';
import {UserContext} from '../App.js';

let container;
let utils;


/*Before each test execution, this will render the signUpModal component within a context provider with a dummy dummyFunction
inside of it in a test environment to test.*/
beforeEach(() => {
  function dummyFunction(){return};
  const userStateObject = {dummyFunction};
  utils = render(
    <UserContext.Provider value={userStateObject}>
      <SignUpModal show={true} />
    </UserContext.Provider>
  );
  container = utils.container;
});

//Tests to see whether the component itself renders properly within the HTML Document
test("Renders Sign Up Form Properly", () => {
  expect(container).toBeInTheDocument();
});

//Tests to see whether the Email Input will return an invalid response given an empty string
test("Detects Empty Response in Email Input via Required Tag", () => {
    const input = utils.getByLabelText('Email');
    expect(input).toBeInvalid();
  });
//Tests to see whether the Name Input will return an invalid response given an empty string
test("Detects Empty Response in Name Input via Required Tag", () => {
  const input = utils.getByLabelText('Username');
  expect(input).toBeInvalid();
});
//Tests to see whether the Address Input will return an invalid response given an empty string
test("Detects Empty Response in Address Input via Required Tag", () => {
  const input = utils.getByLabelText('Address');
  expect(input).toBeInvalid();
});
//Tests to see whether the Password Input will return an invalid response given an empty string
test("Detects Empty Response in Password Input via Required Tag", () => {
  const input = utils.getByLabelText('Password');
  expect(input).toBeInvalid();
});
