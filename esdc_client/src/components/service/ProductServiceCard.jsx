import { Button } from '@mui/material';
import React from 'react';
import { useState } from 'react';
import { Badge } from 'react-bootstrap';
import { formatMoney } from '../../utils/helper';
import CircleButton from '../button/CircleButton';
import { useNotify } from '../store/useNotify';

const ProductServiceCard = ({ status, name, image, price, importPrice, quantity, id, onClick }) => {
	const [quantityBuy, setQuantityBuy] = useState(0);
	const setOpen = useNotify((state) => state.setOpen);
	const setContent = useNotify((state) => state.setContent);
	const setType = useNotify((state) => state.setType);
	const handleAddToCart = () => {
		if (quantityBuy > 0) {
			onClick({ id, quantity: quantityBuy, name });
			setType('success');
			setContent('Thêm vào giỏ hàng thành công');
			setOpen(true);
		} else {
			setType('error');
			setContent('Số lượng phải lớn hơn 0');
			setOpen(true);
		}
	};

	const handleRemoveQuantity = () => {
		if (quantityBuy > 0) {
			setQuantityBuy(quantityBuy - 1);
		}
	};

	const handleAddQuantity = () => {
		if (quantityBuy < quantity) {
			setQuantityBuy(quantityBuy + 1);
		}
	};

	return (
		<div className="product__item">
			<Badge
				pill
				bg={`${
					(status === 'SOLD_OUT' && `secondary`) ||
					(status === 'AVAILABLE' && `success`) ||
					(status === 'HOT' && `danger`)
				}`}
				className="product-status"
			>
				{status?.split('_').join(' ')}
			</Badge>
			<div className="product__item-image">{<img src={image} alt="product_image"></img>}</div>
			<div className="product__item-info">
				<h3>{name}</h3>

				<div className="mb-3">
					<p style={{ marginBottom: 0 }}>
						Sell price: <strong>{formatMoney(price || importPrice)}</strong>
					</p>
					<span>
						Quantity: <strong>{quantity}</strong>
					</span>
				</div>
				<div className="mx-auto mb-3">
					<div className="flex flex-col gap-3">
						<div className="flex items-center gap-3">
							<CircleButton onClick={handleRemoveQuantity}>
								<i className="bx bx-minus"></i>
							</CircleButton>
							<input
								type="number"
								placeholder="Quantity"
								value={quantityBuy}
								onChange={(e) => setQuantityBuy(Number.parseInt(e.target.value))}
								min={0}
								max={quantity}
								className="text-base form-control"
								style={{ width: '100px', border: '1px solid #567d46' }}
							></input>
							<CircleButton onClick={handleAddQuantity}>
								<i className="bx bx-plus"></i>
							</CircleButton>
						</div>
						<Button
							variant="contained"
							color="success"
							onClick={handleAddToCart}
							className="flex items-center gap-2 text-2xl"
						>
							<strong>Add to cart</strong>
							<i className="text-2xl bx bx-cart-add"></i>
						</Button>
					</div>
				</div>
			</div>
			{/* {showModalSell && (
				<SellProductDialog
					show={showModalSell}
					handleClose={() => {
						setShowModalSell(false);
					}}
					id={id}
					quantity={quantity}
					image={image}
					name={name}
					setRefresh={setRefresh}
				></SellProductDialog>
			)} */}
		</div>
	);
};

export default ProductServiceCard;
