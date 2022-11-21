import React, { useEffect, useState } from 'react';
import { Form, FormGroup, Button, Alert } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { API_UPLOAD, ITEM_ORDER, SUPPLIER, token } from '../../utils/constant';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import Loading from '../loading/Loading';
import { useAuth } from '../store/useAuth';
const schema = yup.object().shape({
	supplier_id: yup.number().required("Supplier's name is required"),
	import_price: yup
		.number()
		.min(5000, "Product's import price must greater than five thousand")
		.required("Product's import price is required"),
	item_category: yup.string().required("Product's category is required"),
	name: yup.string().required("Product's name is required"),
	quantity: yup
		.number()
		.min(0, "Product's quantity must greater than zero")
		.required("Product's quantity is required"),
	unit: yup.string().required("Product's unit is required"),
	delivery_date: yup.string().required("Product's delivery date is required"),
});
const ProductDialog = ({ onHide, setRefresh }) => {
	const [suppliers, setSuppliers] = useState([]);
	const [isLoading, setIsLoading] = useState(false);
	const user = useAuth((state) => state.user);
	const {
		handleSubmit,
		register,
		formState: { isSubmitting, isValid, errors },
		reset,
		setError,
	} = useForm({
		mode: 'onChange',
		defaultValues: {
			supplier_id: 1,
			import_price: 0,
			item_category: 'DRINK',
			name: '',
			quantity: 0,
			note: '',
			unit: '',
			delivery_date: '',
		},
		resolver: yupResolver(schema),
	});
	useEffect(() => {
		(async () => {
			setIsLoading(true);
			const response = await fetch(SUPPLIER, {
				headers: {
					Authorization: `Bearer ${user.token}`,
				},
				method: 'GET',
			});
			const data = await response.json();
			setIsLoading(false);
			if (data?._embedded?.suppliers && data?._embedded?.suppliers.length > 0) {
				setSuppliers([...data?._embedded?.suppliers]);
			} else {
				console.error(data);
			}
		})();
	}, [user.token]);

	const onSubmit = async (data) => {
		if (isSubmitting) return;
		if (!isValid) return;
		setIsLoading(true);
		if (data?.image?.length > 0) {
			const file = data?.image[0];
			let formData = new FormData();
			formData.append('image', file);
			fetch(API_UPLOAD, {
				method: 'POST',
				body: formData,
			})
				.then((response) => {
					if (response.status === 200) {
						console.log('Uploaded');
						return response.json();
					}
					throw new Error('Network response was not ok.');
				})
				.then((result) => {
					console.log(result);
					if (result.status === 200) {
						return result;
					}
					return false;
				})
				.then((result) => {
					if (result) {
						data.image = result.data.display_url;
						data.delivery_date = `${data.delivery_date} 00:00:00`;
						console.log(data);
						fetch(ITEM_ORDER, {
							method: 'POST',
							headers: {
								'Content-Type': 'application/json',
								Authorization: `Bearer ${user.token}`,
							},
							body: JSON.stringify(data),
						})
							.then((response) => response.json())
							.then((data) => {
								console.log(data);
								if (data.status === 200) {
									reset({
										supplier_id: 1,
										import_price: 0,
										item_category: 'DRINK',
										name: '',
										quantity: 0,
										note: '',
										unit: '',
										delivery_date: '',
									});
									setRefresh((prev) => !prev);
								}
							});
					}
				})
				.catch((error) => {
					console.error('Error:', error);
					return false;
				})
				.finally(() => {
					setIsLoading(false);
					onHide();
				});
		} else {
			setError('image', {
				type: 'manual',
				message: 'Image is required',
			});
		}
	};
	if (isSubmitting || isLoading)
		return (
			<div className="flex items-center justify-center">
				<Loading />
			</div>
		);
	return (
		<Form onSubmit={handleSubmit(onSubmit)}>
			<FormGroup className="mb-3">
				<Form.Label>Name</Form.Label>
				<Form.Control type="text" placeholder="Enter name" name="name" {...register('name')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Price</Form.Label>
				<Form.Control type="number" placeholder="Enter price" {...register('import_price')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Quantity</Form.Label>
				<Form.Control type="number" placeholder="Enter quantity" {...register('quantity')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Category</Form.Label>
				<div className="mb-3">
					<Form.Check
						inline
						defaultChecked={true}
						label="DRINK"
						name="category"
						type="radio"
						value="DRINK"
						{...register('category')}
					/>
					<Form.Check
						inline
						label="PITCH"
						name="category"
						type="radio"
						value="PITCH"
						{...register('category')}
					/>
				</div>
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Unit</Form.Label>
				<Form.Control type="text" placeholder="Enter unit" {...register('unit')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Import Product Picture</Form.Label>
				<Form.Control type="file" accept="image/*" {...register('image')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Supplier</Form.Label>
				<Form.Select aria-label="SELECT" {...register('supplier_id')}>
					<option disabled hidden>
						Please select supplier
					</option>
					{suppliers?.length > 0 &&
						suppliers.map((supplier, index) => {
							console.log(supplier.resourceId);
							return (
								<option key={index} value={Number.parseInt(supplier.resourceId)} name="supplier_id">
									{supplier.name}
								</option>
							);
						})}
				</Form.Select>
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Delivery date</Form.Label>
				<Form.Control type="date" {...register('delivery_date')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Note</Form.Label>
				<Form.Control as="textarea" rows={3} {...register('note')} placeholder="Enter note" />
			</FormGroup>
			<FormGroup className="mb-3">
				{errors && Object.keys(errors).length > 0 && (
					<Alert variant="danger">
						<p style={{ marginBottom: 'unset' }}>{errors[Object.keys(errors)[0]]?.message}</p>
					</Alert>
				)}
			</FormGroup>
			<Button className="btn btn-success btn-lg btn-block" type="submit">
				Make payment
			</Button>
		</Form>
	);
};

export default ProductDialog;
