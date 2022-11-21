import React, { useEffect } from 'react';

import { useState } from 'react';

import ProductCard from '../components/product_card/ProductCard';

import Header from '../components/header/Header';
import { ITEM_SUPPLIED, token } from '../utils/constant';
import ProductDetail from '../components/product_card/ProductDetail';
import { useParams } from 'react-router-dom';
import { useAuth } from '../components/store/useAuth';

const ProductSupplier = () => {
	const [products, setProducts] = useState([]);
	const [showDetail, setShowDetail] = useState(false);
	const [itemDetail, setItemDetail] = useState(false);
	const [refresh, setRefresh] = useState(false);
	const { id } = useParams();
	const user = useAuth((state) => state.user);
	useEffect(() => {
		(async () => {
			if (id) {
				const response = await fetch(`${ITEM_SUPPLIED}?id=${id.split('&')[0]}`, {
					method: 'GET',
					headers: {
						'Content-Type': 'application/json',
						Authorization: `Bearer ${user.token}`,
					},
				});
				const data = await response.json();
				if (data._embedded) {
					setProducts(data._embedded.items);
				}
			}
		})();
	}, [id, refresh, user.token]);
	if (!id) {
		return <div>Not Found</div>;
	}
	if (products.length === 0) {
		return (
			<div className="flex justify-center">
				<div className="text-2xl text-bold">No Product</div>
			</div>
		);
	}
	return (
		<div className="page-header">
			<div className="header">
				<Header title={`Products are supplied by ${id.split('&')[1]}`}></Header>
			</div>
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
								handleViewDetail={() => {
									setShowDetail(true);
									setItemDetail(item);
								}}
								setRefresh={setRefresh}
							/>
						</div>
					))}
			</div>

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

export default ProductSupplier;
