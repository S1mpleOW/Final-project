import { yupResolver } from '@hookform/resolvers/yup';
import React from 'react';
import { useEffect } from 'react';
import { useState } from 'react';
import { Form } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { API_UPLOAD, FIELDS_GET } from '../../utils/constant';
import { useAuth } from '../store/useAuth';
import { Button, DialogActions } from '@mui/material';
import Loading from '../loading/Loading';
import { useNotify } from '../store/useNotify';

const schema = yup.object().shape({
	name: yup.string().required('Field name is required'),
	type: yup.string().required('Field type is required').oneOf(['FIELD_5', 'FIELD_7', 'FIELD_11']),
	price: yup
		.number()
		.min(0, 'Field price must greater than zero')
		.required('Field price is required'),
});
const UpdateFieldDialog = ({ id, handleClose, setRefresh }) => {
	const {
		handleSubmit,
		register,
		formState: { isSubmitting, isValid, errors },
		reset,
		setError,
	} = useForm({
		mode: 'onChange',
		defaultValues: {
			name: '',
			type: '',
			price: 0,
			fieldGroup: '',
		},
		resolver: yupResolver(schema),
	});
	const [isLoading, setIsLoading] = useState(false);
	const user = useAuth((state) => state.user);
	const [image, setImage] = useState(null);
	const setType = useNotify((state) => state.setType);
	const setContent = useNotify((state) => state.setContent);
	const setOpen = useNotify((state) => state.setOpen);
	useEffect(() => {
		(async () => {
			const response = await fetch(`${FIELDS_GET}/${id}`, {
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${user.token}`,
				},
			});
			const data = await response.json();
			reset({
				name: data.name,
				type: data.type,
				price: data.price,
			});
			setImage(data.image);
		})();
	}, [id, reset, user.token]);

	const onSubmit = (data) => {
		if (isSubmitting) return;
		if (!isValid) return;
		setIsLoading(true);
		if (image && typeof image !== 'string') {
			let formData = new FormData();
			formData.append('image', image);
			fetch(API_UPLOAD, {
				method: 'POST',
				body: formData,
			})
				.then((response) => {
					console.log(response);
					if (response.status === 200) {
						console.log('Uploaded');
						return response.json();
					}
					setError('image', {
						type: 'manual',
						message: 'Upload image failed',
					});
					return false;
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
						fetch(`${FIELDS_GET}/${id}`, {
							method: 'PATCH',
							headers: {
								'Content-Type': 'application/json',
								Authorization: `Bearer ${user.token}`,
							},
							body: JSON.stringify(data),
						})
							.then((response) => response.json())
							.then((responseData) => {
								if (responseData.name) {
									setType('success');
									setContent('Update field successfully');
									setOpen(true);
									reset({
										name: '',
										type: '',
										price: 0,
									});
									setRefresh((prev) => !prev);
									setIsLoading(false);
									handleClose();
								} else {
									setError('name', {
										type: 'manual',
										message: responseData.cause.message,
									});
								}
							});
					} else {
						setError('image', {
							type: 'manual',
							message: 'Upload image failed',
						});
					}
				});
		} else {
			fetch(`${FIELDS_GET}/${id}`, {
				method: 'PATCH',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${user.token}`,
				},
				body: JSON.stringify(data),
			})
				.then((response) => response.json())
				.then((responseData) => {
					console.log(responseData);
					console.log(data);
					if (responseData.name) {
						setType('success');
						setContent('Update field successfully');
						setOpen(true);
						reset({
							name: '',
							type: '',
							price: 0,
						});
						setRefresh((prev) => !prev);
						setIsLoading(false);
						handleClose();
					} else {
						setError('name', {
							type: 'manual',
							message: responseData.message,
						});
					}
				});
		}
	};
	const handleChange = (event) => {
		const fileUploaded = event.target.files[0];
		setImage(fileUploaded);
	};

	if (isSubmitting || isLoading)
		return (
			<div className="flex items-center justify-center">
				<Loading />
			</div>
		);

	if (!id) return;
	console.log(image);
	return (
		<Form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-3">
			<Form.Group>
				<Form.Label>Field name</Form.Label>
				<Form.Control
					type="text"
					placeholder="Enter field name"
					{...register('name')}
					isInvalid={!!errors.name}
				/>
				<Form.Control.Feedback type="invalid">{errors.name?.message}</Form.Control.Feedback>
			</Form.Group>
			<Form.Group>
				<Form.Label>Field type</Form.Label>
				<Form.Control as="select" {...register('type')} isInvalid={!!errors.type}>
					<option value="">Choose field type</option>
					<option value="FIELD_5">Sân 5</option>
					<option value="FIELD_7">Sân 7</option>
					<option value="FIELD_11">Sân 11</option>
				</Form.Control>
				<Form.Control.Feedback type="invalid">{errors.type?.message}</Form.Control.Feedback>
			</Form.Group>
			<Form.Group>
				<Form.Label>Field price</Form.Label>
				<Form.Control
					type="number"
					placeholder="Enter field price"
					{...register('price')}
					isInvalid={!!errors.price}
				/>
				<Form.Control.Feedback type="invalid">{errors.price?.message}</Form.Control.Feedback>
			</Form.Group>
			<Form.Group>
				<Form.Label>Field image</Form.Label>
				{image && (
					<div className="mb-3 w-full max-h-[200px] overflow-hidden">
						{typeof image === 'string' ? (
							<img src={image} alt="field" className="max-h-[200px] w-full object-cover" />
						) : (
							<img
								src={URL.createObjectURL(image)}
								alt="field"
								className="max-h-[200px] object-cover w-full"
							/>
						)}
					</div>
				)}
				<Form.Control
					type="file"
					placeholder="Enter field image"
					onChange={(e) => handleChange(e)}
					isInvalid={!!errors.image}
					accept="image/*"
				/>
				<Form.Control.Feedback type="invalid">{errors.image?.message}</Form.Control.Feedback>
			</Form.Group>

			<DialogActions>
				<Button variant="outlined" onClick={handleClose}>
					Cancel
				</Button>
				<Button type="submit" variant="contained">
					Update
				</Button>
			</DialogActions>
		</Form>
	);
};

export default UpdateFieldDialog;
