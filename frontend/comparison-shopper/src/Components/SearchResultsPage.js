import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../App.js';
import './SearchResultsPage.css';
import { addProductWithLowestPriceToCart } from './CartPage.js';


const SearchResultsPage = () => {
    const [products, setProducts] = useState([]);
    // eslint-disable-next-line
    const [loading, setLoading] = useState(true);
    const [search, setSearch] = useState('');
    const [category, setCategory] = useState(0);
    const [sortBy, setSortBy] = useState('');
    const [superMarket, setSuperMarket] = useState('');
    const urlString = window.location.search;
    const params = new URLSearchParams(urlString);

    const [cart, setCart] = useState([]);
    const { user } = useContext(UserContext);
    const navigate = useNavigate();

    const productPage = (productID) => {
        navigate(`/product/${productID}`);
    };
    
    function handleChangeCategory(event) {
        setCategory(event.target.value);
    }
    
    function handleChangeSuperMarket(event) {
        setSuperMarket(event.target.value);
    }
    

    useEffect(() => {
        // Fetch Cart Data for the user only when the user changes
            async function fetchCartForUser() {
                try {
                    const response = await fetch(process.env.REACT_APP_API_URL+"/v1/carts/customer/"+user.UserID);
                    const data = await response.json();
                    if (data) {
                        setCart(data);
                    } else {
                        console.error("Expected cart data to be an array but received:", data);
                        setCart([]);
                    }
                } catch (error) {
                    console.error("Failed to fetch cart data:", error);
                }
            }
        
        // Common function to fetch Product Data
        async function fetchProductData() {
        // Get the search param if exist otherwise set to default
            if (params.has('search')){
                //search = params.get('search');
                setSearch(params.get('search'));
            }
            //Get the categorys param if exists otherwise set to default
            if (params.has('category')){
                setCategory(params.get('category'));
            }
            if (params.has('sortby')){
                setSortBy(params.get('sortby'));
            }
            if (params.has('superMarket')){
                setSuperMarket(params.get('superMarket'));
            }

            //Sortby
            try {
                console.log("searching for: " + search)
                const response = await fetch(process.env.REACT_APP_API_URL+"/v1/products/search?name=" + search + "&category=" + category+ "&supermarket="+ superMarket +"&priceUpper=0.0&priceLower=0.0&sortBy=" + sortBy + "&sortOrder=");
                if (!response.ok) {
                    throw new Error('Failed to fetch data');
                }
                const data = await response.json();
                setProducts(data);
                setLoading(false);
            } catch (error) {
                console.error("Failed to fetch products:", error);
                setLoading(false);
            }
        }

        if (user) {
            fetchCartForUser();
        }
            fetchProductData();
    // eslint-disable-next-line
    }, [user, search, category, sortBy, superMarket]);
    return (
    <div className="tableWrapper">
        <div className="tableContainer">
    
            <div className="buttonsContainer">
                <button>
                    <img src="icons/FilterIcon.png" alt="Filter" width="20" />
                    <select name = 'categoryDropBox' onChange={handleChangeCategory} >
                        <option value = "0">Category</option>
                        <option value = "1">Meat & Seafood</option>
                        <option value = "2">Fruit & Vegtables</option>
                        <option value = "3">Dairy, Eggs & Cheese</option>
                        <option value = "4">Bakery</option>
                        <option value = "5">Pantry</option>
                        <option value = "6">Drinks</option>
                        <option value = "7">Health & Beauty</option>
                        <option value = "8">Milk</option>
                        <option value = "9">Cheese</option>
                        <option value = "10">Eggs</option>
                        <option value = "11">Lamb</option>
                        <option value = "12">Chicken</option>
                        <option value = "13">Fish</option>
                        <option value = "14">Beef</option>
                        <option value = "15">Pork</option>
                        <option value = "16">Fruit</option>
                        <option value = "17">Vegtables</option>

                    </select>
                </button>

                <button>
                    <select name = 'superMarketDropBox' onChange = {handleChangeSuperMarket}>
                        <option value= ''>SuperMarket</option>
                        <option value= 'Coles'>Coles</option>
                        <option value= 'Woolworths'>Woolworths</option>
                        <option value= 'Aldi'>Aldi</option>
                    </select>
                </button>
            </div>
            {products.length === 0 ? (
            <p style={{ fontSize: '24px', textAlign: 'center' }}>No products found</p>
            ) : (
            <table>
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Product Name</th>
                        <th>Size</th>
                        <th>Unit</th>
                        <th>Category ID</th>
                        <th>Add To Cart</th>
                    </tr>
                </thead>
                <tbody>
                    
                    {products.map((product) => (
                            <tr key={product.productID}>
                                <td>
                                    <button onClick={() => productPage(product.productID)} style={{border: 'none', background: 'transparent'}}>
                                        <img src={'/products/' + product.productID + '.webp'} alt={"Image of " + product.productName} width={70} style={{border: '2px solid #000', borderRadius: '5px'}}/>
                                    </button>
                                </td>
                                <td>{product.productName}</td>
                                <td>{product.size}</td>
                                <td>{product.unit}</td>
                                <td>{product.category_id}</td>
                                <td><button onClick={() => addProductWithLowestPriceToCart(product, cart, navigate)}>Add To Cart</button></td>
                            </tr>
                        ))}
                </tbody>
            </table>
            )}
        </div>
    </div>
    );
};

export default SearchResultsPage;
