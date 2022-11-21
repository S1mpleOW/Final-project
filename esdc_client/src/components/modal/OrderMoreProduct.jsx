import { yupResolver } from '@hookform/resolvers/yup';
import React from 'react';
import { Alert, Button, Form, FormGroup, Modal } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { ORDER_MORE_PRODUCT } from '../../utils/constant';
import { useAuth } from '../store/useAuth';
import * as yup from 'yup';
import { useNotify } from '../store/useNotify';

const schema = yup.object().shape({
	quantity: yup.number().required('Quantity is required').min(5),
	delivery_date: yup.string().required('Delivery date is required'),
	note: yup.string(),
});

const OrderMoreProduct = ({ show, handleClose, id, quantity, image, name, setRefresh }) => {
	const user = useAuth((state) => state.user);
	console.log(setRefresh);
	const {
		handleSubmit,
		register,
		formState: { isSubmitting, isValid, errors },
		setError,
	} = useForm({
		mode: 'onChange',
		defaultValues: {
			quantity: 5,
			delivery_date: '',
			note: '',
		},
		resolver: yupResolver(schema),
	});
	const setOpen = useNotify((state) => state.setOpen);
	const setContent = useNotify((state) => state.setContent);
	const setType = useNotify((state) => state.setType);
	if (!id) return;

	const handleOrder = async (body) => {
		if (isSubmitting) return;
		if (!isValid) return;
		const deliveryDateFormat = `${body.delivery_date.split('T').join(' ')}:00`;
		body.delivery_date = deliveryDateFormat;
		body.itemID = id;
		const response = await fetch(ORDER_MORE_PRODUCT, {
			method: 'POST',
			body: JSON.stringify(body),
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${user.token}`,
			},
		});
		if (response.status !== 200) {
			setError('quantity', {
				type: 'custom',
				message: 'Something went wrong. Please try again later',
			});
			return;
		}
		setType('success');
		setContent('Order more product successfully');
		setOpen();
		setRefresh((prev) => !prev);
		handleClose();
	};
	return (
		<Modal show={show} onHide={handleClose} size="lg">
			<Modal.Header closeButton>
				<Modal.Title>Order</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				<div className="row">
					<div className="col-4">
						<img src={image} alt="" />
					</div>
					<div className="col-8">
						<h5>{name}</h5>
						<p className="!mb-2">Current quantity: {quantity}</p>
						<Form onSubmit={handleSubmit(handleOrder)}>
							<Form.Group>
								<Form.Label>Order more</Form.Label>
								<Form.Control
									type="number"
									placeholder="Enter quantity to order"
									min={5}
									defaultValue={5}
									{...register('quantity')}
								/>
							</Form.Group>
							<Form.Group>
								<Form.Label>Delivery date</Form.Label>
								<Form.Control
									type="datetime-local"
									{...register('delivery_date')}
									placeholder="Enter delivery date"
								/>
							</Form.Group>
							<Form.Group>
								<Form.Label>Note</Form.Label>
								<Form.Control
									as="textarea"
									rows={3}
									placeholder="Enter note"
									{...register('note')}
								/>
							</Form.Group>
							<FormGroup className="mb-3">
								{errors && Object.keys(errors).length > 0 && (
									<Alert variant="danger">
										<p style={{ marginBottom: 'unset' }}>
											{errors[Object.keys(errors)[0]]?.message}
										</p>
									</Alert>
								)}
							</FormGroup>

							<div className="flex justify-center w-full ">
								<Button
									className=" w-full  max-w-[200px] btn btn-success btn-lg btn-block"
									type="submit"
								>
									Order
								</Button>
							</div>
						</Form>
					</div>
				</div>
			</Modal.Body>
			<Modal.Footer>
				<Button variant="secondary" onClick={handleClose}>
					Close
				</Button>
			</Modal.Footer>
		</Modal>
	);
};

export default OrderMoreProduct;
