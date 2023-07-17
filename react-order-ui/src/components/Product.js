function Product(props) {
  const { productName, category, price } = props;
  return (
    <>
      <div className='col-2'>
        <img className='img-fluid' src='https://i.imgur.com/HKOFQYa.jpeg' alt='' />
      </div>
      <div className='col'>
        <div className='row text-muted'>{category}</div>
        <div className='row'>{productName}</div>
      </div>
      <div className='col text-center price'>{price}원</div>
      <div className='col text-end action'>
        <button className='btn btn-small btn-outline-dark' href=''>
          추가
        </button>
      </div>
    </>
  );
}

export default Product;
