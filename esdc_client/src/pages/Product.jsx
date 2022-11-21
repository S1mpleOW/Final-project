import React, { useEffect } from 'react';

import { useState } from 'react';

import ProductCard from '../components/product_card/ProductCard';

import ProductDialog from '../components/dialog/ProductDialog';
import { Button, Modal } from 'react-bootstrap';
import Header from '../components/header/Header';
import { ITEMS_GET } from '../utils/constant';
import ProductDetail from '../components/product_card/ProductDetail';
import { useAuth } from '../components/store/useAuth';
import Loading from '../components/loading/Loading';

const Product = () => {
	const [show, setShow] = useState(false);
	const [products, setProducts] = useState([]);
	const [showDetail, setShowDetail] = useState(false);
	const [itemDetail, setItemDetail] = useState(false);
	const [refresh, setRefresh] = useState(false);
	const [isLoading, setIsLoading] = useState(false);
	const user = useAuth((state) => state.user);
	useEffect(() => {
		(async () => {
			setIsLoading(true);
			const response = await fetch(ITEMS_GET, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${user.token}`,
				},
			});
			const data = await response.json();
			setIsLoading(false);
			if (data._embedded) {
				setProducts(data._embedded.items);
			}
		})();
	}, [refresh, user.token]);
	const handleShow = () => setShow(true);
	const handleClose = () => setShow(false);
	if (isLoading) {
		return (
			<div className="flex items-center justify-center">
				<Loading />
			</div>
		);
	}

	return (
		<div className="page-header">
			<div className="header">
				<Header title="Products in Storage"></Header>
				<Button
					onClick={handleShow}
					className="flex items-center gap-2 btn btn-success"
					data-toggle="modal"
				>
					<i className="bx bxs-add-to-queue"></i>
					<span>Order Product</span>
				</Button>
			</div>
			{products?.length === 0 && (
				<div className="flex items-center justify-center">
					<h1 className="text-2xl uppercase">No product in storage</h1>
				</div>
			)}
			<div className="row">
				{products?.length > 0 &&
					products.map((item) => (
						<div key={item.resourceId}>
							<ProductCard
								id={item.resourceId}
								image={item.image}
								name={item.name}
								price={item.importPrice}
								quantity={item.quantity}
								status={item.status}
								setRefresh={setRefresh}
								handleViewDetail={() => {
									setShowDetail(true);
									setItemDetail(item);
								}}
							/>
						</div>
					))}
			</div>
			<Modal show={show} onHide={handleClose}>
				<Modal.Header>
					<Modal.Title>Order new product</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<ProductDialog onHide={handleClose} setRefresh={setRefresh} />
				</Modal.Body>
				<Modal.Footer>
					<Button className="btn btn-danger btn-lg btn-block" onClick={handleClose}>
						Close
					</Button>
				</Modal.Footer>
			</Modal>
			{itemDetail && (
				<ProductDetail
					item={itemDetail}
					show={showDetail}
					handleClose={() => {
						setShowDetail(false);
					}}
				/>
			)}
		</div>
	);
};

export default Product;
