import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import {UserContext} from '../App.js';
import './CartPage.css';


export const addProductWithLowestPriceToCart = async (product, cart, navigate) => {
    try {
        // Fetch all the prices for the given product ID
        const priceResponse = await fetch(process.env.REACT_APP_API_URL+"/v1/products/price/"+product.productID);
        const priceDataArray = await priceResponse.json();

        // Find the lowest-priced item
        const lowestPricedItem = priceDataArray.sort((a, b) => a.price - b.price)[0];
    
        if (lowestPricedItem) {
            const productWithLowestPrice = {
                ...product,
                price: lowestPricedItem.price,
                supermarket: lowestPricedItem.supermarket
            };
            
            // Check if product is already in the cart
            const existingProductInCart = cart.contents && cart.contents[product.productID];
    
            if (existingProductInCart) {
                // If product exists, increment its quantity by 1
                const updatedQuantity = existingProductInCart + 1;
                await fetch(process.env.REACT_APP_API_URL+"/v1/carts/set", {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        cartid: cart.cartID,
                        productid: product.productID,
                        quantity: updatedQuantity
                    })
                });
            } else {
                // If product doesn't exist in the cart, add it with a quantity of 1
                await fetch(process.env.REACT_APP_API_URL+"/v1/carts/add", {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        cartid: cart.cartID,
                        productid: productWithLowestPrice.productID,
                        quantity: 1
                    })
                });
            }

            navigate('/cart');
        } else {
            console.error(`Failed to fetch price for productID: ${product.productID}`);
        }
    } catch (error) {
        console.error(`Error handling product in cart for productID: ${product.productID}`, error);
    }
};



const CartPage = () => {
    const { user } = useContext(UserContext);
    const [cart, setCart] = useState([]);
    const [products, setProducts] = useState([]);
    const navigate = useNavigate();

    const roundedQuantity = (num) => Math.round(num * 100) / 100;
    const grandTotal = products.reduce((acc, product) => acc + product.price * product.quantity, 0);

    const productPage = (productId) => {
        navigate(`/product/${productId}`);
    };

    const goToSearchResults = () => {
        navigate('/search');
    }
    
    const goToCheckout = () => {
        navigate('/checkout');
    }

    const fetchProductWithLowestPrice = async (id) => {
        const response = await fetch(process.env.REACT_APP_API_URL+"/v1/products/price/"+id);
        const priceData = await response.json();
    
        const lowestPriceObject = priceData.reduce((lowest, current) => {
            return (current.price < lowest.price) ? current : lowest;
        });
    
        return lowestPriceObject.price;
    };

    useEffect(() => {
        if (!user || !user.UserID) return; 
    
        const fetchCartForUser = async () => {
            const response = await fetch(process.env.REACT_APP_API_URL+"/v1/carts/customer/"+user.UserID);
            const data = await response.json();
            if (data && data.contents) {
                setCart(data);
    
                const productPromises = Object.keys(data.contents).map(async id => {
                    const product = await fetch(process.env.REACT_APP_API_URL+"/v1/products/"+id).then(res => res.json());
                    product.quantity = data.contents[id];
                    product.price = await fetchProductWithLowestPrice(id); 
                    return product;
                });
    
                const fetchedProducts = await Promise.all(productPromises);
                setProducts(fetchedProducts);
            }
        };
    
        fetchCartForUser();
    }, [user]);


    const handleQuantityChange = async (productId, newQuantity) => {
        if (newQuantity >= 1) {
            const updatedContents = { ...cart.contents, [productId]: newQuantity };
            const updatedCart = { ...cart, contents: updatedContents };
            setCart(updatedCart);

            try {
                await fetch(process.env.REACT_APP_API_URL+"/v1/carts/set", {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        cartid: cart.cartID,
                        productid: productId,
                        quantity: newQuantity
                    })
                });
                

                const updatedProducts = products.map(product => {
                    if (product.productID === productId) {
                        return { ...product, quantity: newQuantity };
                    }
                    return product;
                });
                setProducts(updatedProducts);
            } catch (error) {
                console.error("Error updating product quantity:", error);
            }
        }
    };
    

    const removeFromCart = async (productId) => {
        await fetch(process.env.REACT_APP_API_URL+"/v1/carts/delete", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                cartid: cart.cartID,
                productid: productId,
            })
        });

        const updatedProducts = products.filter(product => product.productID !== productId);
        setProducts(updatedProducts);
    };

    
    const clearCart = async () => {
        for (let product of products) {
            await fetch(process.env.REACT_APP_API_URL+"/v1/carts/delete", {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    cartid: cart.cartID,
                    productid: product.productID,
                })
            });
        }

        setCart(prevCart => ({ ...prevCart, contents: {} }));
        setProducts([]);
    };
    
    
    return (
        <div className="cartWrapper">
            <h2>Items in cart</h2>
            {!user.UserID ? (
            <p style={{ fontSize: '24px', textAlign: 'center' , color: 'red' }}>You need to login!</p>
            ) : (
            <table>
                <thead>
                    <tr>
                        <th>Product Photo</th>
                        <th>Product Name</th>
                        <th></th>
                        <th>Quantity</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map((product) => (
                        <tr key={product.productID}>
                            <td>
                                <button onClick={() => productPage(product.productID)} style={{border: 'none', background: 'transparent'}}>
                                    <img src={'/products/' + product.productID + '.webp'} 
                                        alt={"Image of " + product.productName} 
                                        width={70} 
                                        style={{border: '2px solid #000', borderRadius: '5px'}} />
                                </button>
                            </td>
                            <td>{product.productName}</td>
                            <td><button onClick={() => removeFromCart(product.productID)} className="removeBtn">Remove</button></td>
                            <td>
                                <span>Qty:</span>
                                <input type="number" value={product.quantity} onChange={(e) => {handleQuantityChange(product.productID, parseInt(e.target.value)); }} className="quantityInput" />
                            </td>
                            <td>Total: ${roundedQuantity(product.price * (product.quantity || 1))}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            )}
            <div className="clearCartGroup">
                <button onClick={clearCart} className="clearCart">Clear Cart</button>
                <span>Grand Total: ${roundedQuantity(grandTotal)}</span>
            </div>
            <div className="buttonsGroup">
                <button className="continueBtn" onClick={goToSearchResults}>Continue Shopping</button>
                <button className="checkoutBtn" onClick={goToCheckout}>Proceed To Checkout</button>
            </div>
        </div>
    );
}

export default CartPage;