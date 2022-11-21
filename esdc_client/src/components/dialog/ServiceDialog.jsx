import { yupResolver } from '@hookform/resolvers/yup';
import React, { useEffect, useState } from 'react';
import { Alert, Button, Form } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { ITEMS_GET, SERVICES_GET } from '../../utils/constant';
import Loading from '../loading/Loading';
import { useAuth } from '../store/useAuth';

const schema = yup.object().shape({
	name: yup.string().required('Name is required'),
	priceSell: yup.number().required('Price is required'),
	items: yup.array().required('Items is required'),
});

const ServiceDialog = ({ id, setShow, handleFetchData }) => {
	const {
		handleSubmit,
		register,
		formState: { isSubmitting, isValid, errors },
		reset,
		setError,
		setValue,
		getValues,
	} = useForm({
		mode: 'onChange',
		defaultValues: {
			name: '',
			priceSell: 0,
			items: [],
		},
		resolver: yupResolver(schema),
	});
	const [items, setItems] = useState([]);
	const user = useAuth((state) => state.user);
	const onSubmit = (data) => {
		console.log(data);
		if (isValid && !isSubmitting) {
			fetch(id ? `${SERVICES_GET}/${id}` : SERVICES_GET, {
				method: id ? `PATCH` : 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${user.token}`,
				},
				body: JSON.stringify(data),
			})
				.then((response) => response.json())
				.then((data) => {
					console.log(data);
					if (data?.name) {
						handleFetchData();
						reset({
							name: '',
							price: 0,
							items: [],
						});
						setItems([]);
						setShow(false);
					} else {
						setError('error', { message: data.message || 'Error in update service' });
					}
				});
		}
	};

	useEffect(() => {
		(async () => {
			const res = await fetch(`${ITEMS_GET}`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${user.token}`,
				},
			});
			const data = await res.json();
			if (data?._embedded?.items.length > 0) {
				setItems(data._embedded.items);
			}
		})();
	}, []);

	useEffect(() => {
		if (id) {
			fetch(`${SERVICES_GET}/${id}/items`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${user.token}`,
				},
			})
				.then((response) => response.json())
				.then((data) => {
					if (data?._embedded?.items.length > 0) {
						setValue(
							'items',
							data._embedded.items.map((item) => `items/${item.resourceId}`)
						);
					}
				});
			fetch(`${SERVICES_GET}/${id}`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${user.token}`,
				},
			})
				.then((response) => response.json())
				.then((data) => {
					if (data?.name) {
						reset({
							name: data.name,
							priceSell: data.priceSell,
						});
					}
				});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [id, user.token]);
	console.log(getValues('items'));
	if (isSubmitting) {
		return (
			<div className="flex items-center justify-center">
				<Loading></Loading>
			</div>
		);
	}
	return (
		<Form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-3">
			<Form.Group>
				<Form.Label>Name</Form.Label>
				<Form.Control type="text" {...register('name')} />
			</Form.Group>
			<Form.Group>
				<Form.Label>Sell price</Form.Label>
				<Form.Control type="number" {...register('priceSell')} />
			</Form.Group>
			<Form.Group>
				<Form.Label>Items</Form.Label>
				<div className="flex items-center gap-2 flex-wrap">
					{items.length > 0 ? (
						items.map((item) => (
							<Form.Check
								key={item.resourceId}
								label={item.name}
								value={`items/${item.resourceId}`}
								{...register('items')}
							></Form.Check>
						))
					) : (
						<Loading></Loading>
					)}
				</div>
			</Form.Group>
			<Form.Group>
				{errors && Object.keys(errors).length > 0 && (
					<Alert variant="danger">
						<p style={{ marginBottom: 'unset' }}>{errors[Object.keys(errors)[0]]?.message}</p>
					</Alert>
				)}
			</Form.Group>
			<Button
				className="btn btn-success btn-lg btn-block uppercase"
				type="submit"
				disabled={isSubmitting}
			>
				{id ? `Update` : 'Add new'} service
			</Button>
			<Button className="btn btn-secondary uppercase" onClick={() => setShow(false)}>
				Cancel
			</Button>
		</Form>
	);
};

export default ServiceDialog;
