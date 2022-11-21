import { yupResolver } from '@hookform/resolvers/yup';
import React from 'react';
import { Form } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { Button, DialogActions } from '@mui/material';
import { useEffect } from 'react';
import { API_UPLOAD, FIELDS_GET, GROUPS_GET } from '../../utils/constant';
import { useAuth } from '../store/useAuth';
import { useState } from 'react';
import Loading from '../loading/Loading';
const schema = yup.object().shape({
	name: yup.string().required('Field name is required'),
	type: yup.string().required('Field type is required').oneOf(['FIELD_5', 'FIELD_7', 'FIELD_11']),
	price: yup
		.number()
		.min(0, 'Field price must greater than zero')
		.required('Field price is required'),
	fieldGroup: yup.string().required('Field group is required'),
});

const FieldDialog = ({ handleClose, setRefresh }) => {
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
	const [groups, setGroups] = useState([]);
	const [isLoading, setIsLoading] = useState(false);
	const user = useAuth((state) => state.user);
	useEffect(() => {
		(async () => {
			const response = await fetch(GROUPS_GET, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${user.token}`,
				},
			});
			const data = await response.json();
			setGroups(data._embedded.groups);
		})();
	}, [user.token]);

	const onSubmit = (data) => {
		if (isSubmitting) return;
		if (!isValid) return;
		setIsLoading(true);
		if (data?.image?.length > 0) {
			console.log(data);
			const file = data?.image[0];
			console.log(file);
			let formData = new FormData();
			formData.append('image', file);
			fetch(`https://api.imgbb.com/1/upload?key=3f7b638c409019f370014fe9a7a24cc5`, {
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
						fetch(FIELDS_GET, {
							method: 'POST',
							headers: {
								'Content-Type': 'application/json',
								Authorization: `Bearer ${user.token}`,
							},
							body: JSON.stringify(data),
						})
							.then((response) => response.json())
							.then((responseData) => {
								if (responseData.name) {
									reset({
										name: '',
										type: '',
										price: 0,
										image: '',
										fieldGroup: '',
									});
									setRefresh((prev) => !prev);
									handleClose();
								} else {
									setError('name', {
										type: 'manual',
										message: responseData.cause.message,
									});
								}
							});
					} else {
						setIsLoading(false);
					}
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
				<Form.Control
					type="file"
					placeholder="Enter field image"
					{...register('image')}
					isInvalid={!!errors.image}
					accept="image/*"
				/>
				<Form.Control.Feedback type="invalid">{errors.image?.message}</Form.Control.Feedback>
			</Form.Group>
			<Form.Group>
				<Form.Label>Field group</Form.Label>
				<Form.Control as="select" {...register('fieldGroup')} isInvalid={!!errors.fieldGroup}>
					<option value="">Choose field group</option>
					{groups?.length > 0 &&
						groups.map((group) => (
							<option key={group.resourceId} value={`/groups/${group.resourceId}`}>
								{group.name}
							</option>
						))}
				</Form.Control>
				<Form.Control.Feedback type="invalid">{errors.fieldGroup?.message}</Form.Control.Feedback>
			</Form.Group>
			<DialogActions>
				<Button variant="outlined" onClick={handleClose}>
					Cancel
				</Button>
				<Button type="submit" variant="contained">
					Add Field
				</Button>
			</DialogActions>
		</Form>
	);
};

export default FieldDialog;
