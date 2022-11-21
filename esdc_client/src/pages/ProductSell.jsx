import { Button } from '@mui/material';
import React, { useEffect, useMemo, useState } from 'react';
import { Modal } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import BackLink from '../components/backLinks/BackLink';
import SellProductDialog from '../components/dialog/SellProductDialog';
import Header from '../components/header/Header';
import Loading from '../components/loading/Loading';
import ProductServiceCard from '../components/service/ProductServiceCard';
import { SERVICES_GET } from '../utils/constant';

const ProductSell = () => {
	const { id } = useParams();
	const [products, setProducts] = useState([]);
	const [service, setService] = useState(null);
	const [loading, setLoading] = useState(true);
	const [refresh, setRefresh] = useState(false);
	const idRequest = useMemo(() => id.split('&')[0], [id]);
	const [items, setItems] = useState([]);
	const [show, setShow] = useState(false);

	const handleAddToCart = ({ id, quantity, name }) => {
		const newItems = [...items];
		const index = newItems.findIndex((item) => item.id === id);
		if (index === -1) {
			newItems.push({ id, quantity, name });
		} else {
			newItems[index] = { id, quantity, name };
		}
		setItems(newItems);
	};

	const handleShow = () => {
		setShow(true);
	};
	const handleClose = () => {
		setShow(false);
	};
	const handleFetchData = () => {
		if (idRequest) {
			setLoading(true);
			fetch(`${SERVICES_GET}/${idRequest}`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
				},
			})
				.then((res) => res.json())
				.then((data) => {
					setService(data);
				});

			fetch(`http://localhost:8080/api/services/${idRequest}/items`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
				},
			})
				.then((response) => response.json())
				.then((data) => {
					if (data?._embedded?.items?.length > 0) {
						const mappedData = data._embedded.items.map((item) => {
							return {
								...item,
								price: service?.priceSell,
							};
						});
						setProducts(mappedData);
					}
					setLoading(false);
				});
		}
	};

	useEffect(() => {
		handleFetchData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [service?.priceSell, refresh, idRequest]);

	return (
		<div className="page-header">
			<div className="header">
				<Header title="Products available"></Header>
			</div>
			<div className="flex items-center justify-between mb-3">
				<BackLink to="/services" name="services"></BackLink>
				<Button onClick={handleShow} variant="contained" style={{ backgroundColor: '#567d46' }}>
					<i className="text-lg bx bxs-add-to-queue"></i>
					<span className="block mr-3 text-xl">Sell</span>
				</Button>
			</div>
			{loading && <div className="flex justify-center">{<Loading />}</div>}
			{products.length === 0 && <div className="flex justify-center">No products available</div>}
			<div className="grid grid-cols-4 gap-3">
				{!loading &&
					products?.length > 0 &&
					products.map((item) => (
						<ProductServiceCard
							{...item}
							id={item.resourceId}
							key={item.resourceId}
							setRefresh={setRefresh}
							onClick={handleAddToCart}
						/>
					))}
			</div>
			<Modal show={show} onHide={handleClose}>
				<Modal.Header>
					<Modal.Title>Receipt</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<SellProductDialog
						onHide={handleClose}
						setShow={setShow}
						items={items}
						handleFetchData={handleFetchData}
						totalPrice={service?.priceSell * items.reduce((acc, item) => acc + item.quantity, 0)}
						setItems={setItems}
					></SellProductDialog>
				</Modal.Body>
			</Modal>
		</div>
	);
};

export default ProductSell;
