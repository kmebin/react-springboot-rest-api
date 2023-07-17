import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import React, { useEffect, useState } from 'react';
import ProductList from './components/ProductList';
import Summary from './components/Summary';
import axios from 'axios';

function App() {
  const [products, setProducts] = useState([]);
  const [items, setItems] = useState([]);

  const handleAddClicked = (id) => {
    const product = products.find((product) => product.id === id);
    const found = items.find((item) => item.id === id);
    const updatedItems = found
      ? items.map((item) => (item.id === id ? { ...item, count: item.count + 1 } : item))
      : [...items, { ...product, count: 1 }];

    setItems(updatedItems);
    console.log(id, 'clicked!');
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
          <div class='col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0'>
            <ProductList products={products} onAddClick={handleAddClicked} />
          </div>
          <div className='col-md-4 summary p-4'>
            <Summary items={items} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
