import React from 'react';
import { Modal } from 'react-bootstrap';
import { addYear, formatMoney } from '../../utils/helper';
import './product_detail.css';
const ProductDetail = ({ show, handleClose, item }) => {
	console.log(item);
	return (
		<Modal show={show} onHide={handleClose}>
			<div className="product-card">
				<h2 className="product-status badge">{item?.status}</h2>
				<div className="product-image">
					<img src={item?.image} alt="" />
				</div>
				<div className="product-details">
					<span className="product-category">{item?.itemCategory}</span>
					<h2 className="product-name">{item?.name}</h2>
					<h3 className="product-unit capitalize">{item?.unit}</h3>
					<h3 className="product-quantity">Số lượng: {item?.quantity}</h3>
					<h3 className="product-dateIn">
						Ngày nhập: {new Date(item?.createdAt).toLocaleDateString()}
					</h3>
					<h3 className="product-exp">Hạn sử dụng: {addYear(new Date(item?.createdAt), 2)}</h3>

					<div className="product-bottom-details">
						<div className="product-price">Giá: {formatMoney(item?.importPrice)}</div>
						<div className="product-links">
							{/* <a href="" className="favorite_btn"><i className="fa fa-heart"></i></a> */}
						</div>
					</div>
				</div>
			</div>
		</Modal>
	);
};

export default ProductDetail;
