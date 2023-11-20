import React, { useState, useEffect } from 'react';
import './UserProfilePage.css'
import increase from './increase.png'
import decrease from './decrease.png'
import noChange from './noChange.png'
import userImage from './userImage.png'

const UserProfilePage = () => {
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function fetchData() {
            try {
                const response = await fetch(process.env.REACT_APP_API_URL+"/v1/notifications");
                if (!response.ok) {
                    throw new Error('Failed to fetch data');
                }
                const data = await response.json();
                setNotifications(data);
                setLoading(false);
            } catch (error) {
                console.error("Failed to fetch products:", error);
                setLoading(false);
            }
        }
        fetchData();
    }, []);
    if (loading) {
        return <div>Loading...</div>;
    }
    return (
    <>
      <div className="companyTitle">
        <p>Brokie<span>Hub</span></p>
      </div>
      <div className='outerBox'>
            <div className='left'>
                <img
                    alt=""
                    src={userImage}
                    width="100"
                    height="100"
                />{' '}

                <button className= 'button' type='button'>Settings</button>
                
                <button className='logoutButton' type='button'>Logout</button>
            </div>
            
            <div className='centre'>
                <div className='favouriteItems'>
                    <p>Favourite Items</p>
                </div>
                <div className='item'>
                    <div className='name'>
                        <p>Rice</p>
                    </div>
                    <div className='shops'>
                        <p>Coles </p>
                        <p>| </p>
                        <p>Price </p>
                        <p>19.99</p>
                        <img
                            alt=""
                            src={increase}
                            width="40"
                            height="40"
                            className='measurment'/>{' '}
                    </div>
                    <div className='shops'>
                        <p>Woolworths </p>
                        <p>| </p>
                        <p>Price </p>
                        <p>19.99</p>
                        <img
                            alt=""
                            src={decrease}
                            width="40"
                            height="40"
                            className='measurment'/>{' '}
                    </div>
                </div>
                <div className='item'>
                    <div className='name'>
                        <p>Beans</p>
                    </div>
                    <div className='shops'>
                    <p>Woolworths </p>
                        <p>| </p>
                        <p>Price </p>
                        <p>19.99</p>
                        <img
                            alt=""
                            src={noChange}
                            width="40"
                            height="40"
                            className='measurment'/>{' '}
                    </div>
                </div>
            </div> 

            <div className='centre'>
                <div className='notificationItems'>
                    <p>Notifications</p>
                </div>
                <div className='item'>
                    <div className='name'>
                        <p></p>
                    </div>
                    <div className='shops'>
                    {notifications.map((notification) =>
                            <p key={notification.productID === 1}> 
                            <p>{notification.notification_description}</p>
                            </p>
                        )}
                        <p>Coles </p>
                        <p>| </p>
                        <p>Price </p>
                        <p>19.99</p>
                        <img
                            alt=""
                            src={increase}
                            width="40"
                            height="40"
                            className='measurment'/>{' '}
                    </div>
                    <div className='shops'>
                        <p>Woolworths </p>
                        <p>| </p>
                        <p>Price </p>
                        <p>19.99</p>
                        <img
                            alt=""
                            src={decrease}
                            width="40"
                            height="40"
                            className='measurment'/>{' '}
                    </div>
                </div>
            </div> 
      </div> 
    </>
  )
}

export default UserProfilePage;