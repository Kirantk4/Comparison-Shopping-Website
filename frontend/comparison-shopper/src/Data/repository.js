async function createNewUser(emailInput, usernameInput, addressInput, passwordInput) {
  const userArray ={
      Name: usernameInput,
      Email: emailInput,
      Address:addressInput,
      Password:passwordInput
  }
  const newUser = {
    method:'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(userArray)
  };
  var validResponse;
  // eslint-disable-next-line
  const res = await fetch(process.env.REACT_APP_API_URL+"/v1/users/create", newUser)
    .then((result) => {
      if (result.status === 400) {
        console.log("Signup failed");
        validResponse = false;
      } else {
        console.log("Successful Signup");
        validResponse = true;
      }
    });
    return validResponse;
}

async function verifyLogin(emailInput, passwordInput){
  const loginArray = {
    Email: emailInput,
    Password: passwordInput
  }
  const loginPost = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(loginArray)
  };
  var validResponse;
  // eslint-disable-next-line
  const res = await fetch(process.env.REACT_APP_API_URL+"/v1/users/login", loginPost)
    .then((result) => {
      console.log(result);
      if (result.status === 404) {
        validResponse = false;
      } else if (result.status === 200) {
        validResponse = true;
      } else {
        validResponse = false;
      }
    });
    console.log(validResponse);
    return validResponse;
}

async function retrieveUserData(emailInput) {
  const res = await fetch(process.env.REACT_APP_API_URL+"/v1/users/email/" + emailInput);
  const json = await res.json();
  return json;
}
// DEBUG FUNCTION DO NOT USE
async function testUser() {
  const testUser = {
    Name: "test",
    Email: "test@test.com",
    Address:"test",
    Password: "test"
  };
  const newUser = {
    method:'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(testUser)
  };
  console.log(newUser);
  await fetch(process.env.REACT_APP_API_URL+"/v1/users/create", newUser);
;
}
export {
  createNewUser, testUser, retrieveUserData, verifyLogin
}
