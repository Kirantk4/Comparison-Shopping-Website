import React from 'react';
import './CheckoutPage.css'

const CheckoutPage = () => {
  return (
    <div>
        <div className="companyTitle">
            <p>Brokie<span>Hub</span></p>
        </div>

        <div className='outerContainer'>
            <div className='left'>
                <div className='userDetails'> 
                    <p>User Details:</p> 
                    <div className='box'>
                        <p>Full Name:</p>
                        <input type='text' placeholder="Name"></input>
                        <p>Email Address:</p>
                        <input type='text' placeholder= "Email"></input>
                        <p>Address:</p>
                        <input type='text' placeholder= "Address"></input>
                        <p>Post code:</p>
                        <input type='float' placeholder= "Post code"></input>
                        <p>payment method:</p>
                        <select payment="">
                            <option value="Mastercard">Mastercard</option>
                            <option value="Paypal">Paypal</option>
                        </select>
                    </div>
                </div>    
            </div>
            <div className='right'>
                <div className='total'>
                    <p>Total:</p>
                    <div className='box'>
                        <p>Cart total: $11</p>
                        <p></p>
                        <p>Delivery cost: $2.99</p>
                        <p></p>
                        <p></p>
                        <p>Total cost: $13.99</p>
                    </div>
                    <p></p>
                    <p>Payment Details:</p>
                    <div className='box'>
                        <p>Card Number:</p>
                        <input type='text' placeholder= '    '></input>
                        <p>Name on card:</p>
                        <input type='text' placeholder= '    '></input>
                        <div className='lrbox'>
                            <div className='l'>
                                <p>Expiration</p>
                                <input type='text' placeholder= '    ' size={"8"}></input>
                            </div>
                            <div className='r'>
                                <p>CVC</p>
                                <input type='text' placeholder= '   ' size={"3"} ></input>
                            </div>
                        </div>
                    </div>                    
                </div>
                <p></p>
                <center><button className='submitButton' type='button'>Submit order</button></center>
                <p></p>
            </div>
        </div>
    </div>
  )
}

export default CheckoutPage;