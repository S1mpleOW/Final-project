import { yupResolver } from '@hookform/resolvers/yup';
import { Button, DialogActions } from '@mui/material';
import React from 'react';
import { Alert, Form } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { ITEM_SELL } from '../../utils/constant';
import { formatMoney } from '../../utils/helper';
import { useAuth } from '../store/useAuth';
import { useNotify } from '../store/useNotify';

const schema = yup.object().shape({
	phone: yup.string(),
	items: yup.array(),
});

const SellProductDialog = ({ items, onHide, handleFetchData, totalPrice, setItems }) => {
	const {
		handleSubmit,
		register,
		formState: { isSubmitting, isValid, errors },
		reset,
		setError,
	} = useForm({
		mode: 'onChange',
		defaultValues: {
			phone: '',
			items,
		},
		resolver: yupResolver(schema),
	});
	const user = useAuth((state) => state.user);
	const setType = useNotify((state) => state.setType);
	const setContent = useNotify((state) => state.setContent);
	const setOpen = useNotify((state) => state.setOpen);

	if (items?.length === 0) {
		return (
			<>
				<div className="flex items-center justify-center text-3xl">No items</div>
			</>
		);
	}

	const onSubmit = async (data) => {
		if (!isValid) return;
		if (isSubmitting) return;
		const mappedData = data.items.map((item) => {
			return {
				id: item.id,
				quantity: item.quantity,
			};
		});
		data.items = mappedData;
		const response = await fetch(ITEM_SELL, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${user.token}`,
			},
			body: JSON.stringify(data),
		});
		const responseBody = await response.json();
		if (response.status === 200) {
			reset();
			onHide();
			setType('success');
			setContent('Sell success');
			setOpen(true);
			setItems([]);
			handleFetchData();
		} else {
			setError('phone', {
				type: 'manual',
				message: responseBody.message,
			});
		}
	};
	return (
		<Form className="flex flex-col gap-3" onSubmit={handleSubmit(onSubmit)}>
			<Form.Group>
				<Form.Label>Phone</Form.Label>
				<Form.Control type="text" placeholder="Enter phone" {...register('phone')} />
			</Form.Group>
			<div className="flex flex-wrap items-center gap-3">
				{items?.length > 0 &&
					items.map((item, index) => (
						<Form.Group key={item.name}>
							<Form.Label>{item.name}</Form.Label>
							<Form.Control
								type="text"
								placeholder="Enter quantity"
								disabled
								defaultValue={item.quantity}
								{...register(`items.${index}.quantity`)}
							/>
						</Form.Group>
					))}
			</div>
			<div className="ml-auto">
				<Alert variant="info">Total price: {formatMoney(totalPrice || 0)}</Alert>
			</div>
			<div>
				{errors && Object.keys(errors).length > 0 && (
					<Alert variant="danger">{errors[Object.keys(errors)[0]]?.message}</Alert>
				)}
			</div>
			<DialogActions>
				<Button onClick={onHide}>Cancel</Button>
				<Button type="submit" variant="contained">
					Confirm
				</Button>
			</DialogActions>
		</Form>
	);
};

export default SellProductDialog;
