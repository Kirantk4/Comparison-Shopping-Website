import React, {useState, useEffect} from 'react';
import './ProductPage.css';
import { useParams, useNavigate } from 'react-router-dom';
import { addProductWithLowestPriceToCart } from './CartPage.js';
import { useContext } from 'react';
import { UserContext } from '../App.js';


const ProductPage = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const { productId } = useParams(); 
  const navigate = useNavigate();
  const { user } = useContext(UserContext);
  const [cart, setCart] = useState([]);

  const addToCartLowestPrice = () => {
    if (products && products.length > 0) {
        const lowestPricedProduct = products.sort((a, b) => a.price - b.price)[0];
        addProductWithLowestPriceToCart(lowestPricedProduct, cart, navigate);
    }
  }

  useEffect(() => {
    const fetchCartForUser = async () => {
        const response = await fetch(process.env.REACT_APP_API_URL+"/v1/carts/customer/"+user.UserID);
        
        const data = await response.json();
        if (data) {
            setCart(data);
        } else {
            console.error("Expected cart data to be an array but received:", data);
            setCart([]);
        }
    };

    const fetchData = async () => {
        try {
            // Fetch product price details
            const responsePrice = await fetch(process.env.REACT_APP_API_URL + "/v1/products/price/"+productId);
            const priceData = await responsePrice.json();

            // Fetch product name details
            const responseProduct = await fetch(process.env.REACT_APP_API_URL + "/v1/products/"+productId);
            const productData = await responseProduct.json();

            const combinedData = priceData.map(priceItem => ({
                ...priceItem,
                productName: productData.productName,
                size: productData.size,
                unit: productData.unit,
                category_id: productData.category_id
            }));

            setProducts(combinedData);
            setLoading(false);
        } catch (error) {
            setLoading(false);
        }
    }
      fetchCartForUser();

      fetchData();
  }, [user, productId]);

  if (loading) {
      return <div>Loading...</div>;
  }

  return (
    <>
      <div className="product-display">
        <div className="imageBorder">
          {Array.from(new Set(products.map(product => product.productID))).map((uniqueProductID) => (
            <img key={uniqueProductID} src={'/products/' + uniqueProductID + '.webp'} alt={"Image of" + (products.find(product => product.productID === uniqueProductID)?.productName || "Product")} width={70} style={{ border: '2px solid #000', borderRadius: '5px' }} />
          ))}

          <h2>{products[0]?.productName || "Product Name"}</h2>

          <button onClick={addToCartLowestPrice}>Add to Cart</button>
        </div>
        <div className="block-display">
      <table>
                <thead>
                    <tr>
                        <th>Location</th>
                        <th>Today's Price ($)</th>
                    </tr>
                </thead>
                <tbody>
                  {products.map((product, index) => (
                      <tr key={`${product.productID}-${product.supermarket}-${index}`}>
                          <td>{product.supermarket}</td>
                          <td>{product.price}</td>
                      </tr>
                  ))}
                </tbody>
            </table>
        </div>
      </div>
    </>
  )
}

export default ProductPage;
