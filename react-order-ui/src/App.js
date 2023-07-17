import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import React, { useEffect, useState } from 'react';
import ProductList from './components/ProductList';
import Summary from './components/Summary';
import axios from 'axios';

function App() {
  const [products, setProducts] = useState([]);
  const [items, setItems] = useState([]);

  const handleAddClicked = (productId) => {
    const product = products.find((product) => product.productId === productId);
    const found = items.find((item) => item.productId === productId);
    const updatedItems = found
      ? items.map((item) => (item.productId === productId ? { ...item, count: item.count + 1 } : item))
      : [...items, { ...product, count: 1 }];

    setItems(updatedItems);
  };

  const handleOrderSumbit = (order) => {
    if (items.length === 0) {
      alert('아이템을 추가해주세요!');
    } else {
      axios
        .post('http://localhost:8080/api/v1/orders', {
          email: order.email,
          address: order.address,
          postcode: order.postcode,
          orderItems: items.map((item) => ({
            productId: item.productId,
            category: item.category,
            price: item.price,
            quantity: item.count,
          })),
        })
        .then(
          () => alert('주문이 정상적으로 접수되었습니다.'),
          (e) => {
            alert('서버 장애');
            console.error(e);
          }
        );
    }
  };

  useEffect(() => {
    axios.get('http://localhost:8080/api/v1/products').then((v) => setProducts(v.data));
  }, []);

  return (
    <div className='container-fluid'>
      <div className='row justify-content-center m-4'>
        <h1 className='text-center'>Grids & Circle</h1>
      </div>
      <div className='card'>
        <div className='row'>
          <div className='col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0'>
            <ProductList products={products} onAddClick={handleAddClicked} />
          </div>
          <div className='col-md-4 summary p-4'>
            <Summary items={items} onOrderSubmit={handleOrderSumbit} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
