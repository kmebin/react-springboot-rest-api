import Product from './Product';

function ProductList({ products, onAddClick }) {
  return (
    <>
      <h5 className='flex-grow-0'>
        <b>상품 목록</b>
      </h5>
      <ul className='list-group products'>
        {products.map((product) => (
          <li key={product.productId} className='list-group-item d-flex mt-3'>
            <Product {...product} onAddClick={onAddClick} />
          </li>
        ))}
      </ul>
    </>
  );
}

export default ProductList;
