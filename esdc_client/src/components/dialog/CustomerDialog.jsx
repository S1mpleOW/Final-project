import { yupResolver } from '@hookform/resolvers/yup';
import React from 'react';
import { Form, FormGroup, Button, Alert } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { REGISTER } from '../../utils/constant';
import { useAuth } from '../store/useAuth';
import { useNotify } from '../store/useNotify';
const schema = yup.object().shape({
	fullName: yup.string().required('Full name is required'),
	email: yup.string().required('Email is required'),
	phone: yup.string().required('Phone is required'),
	address: yup.string().required('Address is required'),
	sex: yup.string().oneOf(['MALE', 'FEMALE', 'OTHER']).required('Sex is required'),
	dob: yup.string().required('Date of birth is required'),
	username: yup.string().required('Username is required'),
	password: yup
		.string()
		.min(6, 'Password must be at least 6 characters')
		.required('Password is required'),
});

const CustomerDialog = ({ setShow, handleFetchData }) => {
	const {
		register,
		handleSubmit,
		formState: { errors, isValid, isSubmitting },
		setError,
		reset,
	} = useForm({
		mode: 'onChange',
		defaultValue: {
			fullName: '',
			email: '',
			phone: '',
			address: '',
			sex: 'MALE',
			dob: '',
			username: '',
			password: '',
		},
		resolver: yupResolver(schema),
	});

	const setType = useNotify((state) => state.setType);
	const setContent = useNotify((state) => state.setContent);
	const setOpen = useNotify((state) => state.setOpen);

	const user = useAuth((state) => state.user);
	const onSubmit = (data) => {
		if (isValid && !isSubmitting) {
			const date = data['dob'];
			const formatDob = `${date} 00:00:00`;
			const body = {
				...data,
				dob: formatDob,
			};
			fetch(REGISTER, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${user.token}`,
				},
				body: JSON.stringify(body),
			})
				.then((response) => response.json())
				.then((data) => {
					if (data.status === 200) {
						handleFetchData();
						reset({
							fullName: '',
							email: '',
							phone: '',
							address: '',
							dob: '',
							sex: 'MALE',
							username: '',
							password: '',
						});
						setType('success');
						setContent('Create customer successfully');
						setOpen();
						setShow(false);
					} else {
						setError('error', { message: data.message });
					}
				});
		}
	};
	return (
		<Form onSubmit={handleSubmit(onSubmit)}>
			<FormGroup className="mb-3">
				<Form.Label>Name</Form.Label>
				<Form.Control type="text" placeholder="Enter name" {...register('fullName')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Email</Form.Label>
				<Form.Control type="email" placeholder="Enter email" {...register('email')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Sex</Form.Label>
				<div className="mb-3">
					<Form.Check
						inline
						defaultChecked={true}
						label="Male"
						name="sex"
						type="radio"
						value="MALE"
						{...register('sex')}
					/>
					<Form.Check
						inline
						label="Female"
						name="sex"
						value="FEMALE"
						type="radio"
						{...register('sex')}
					/>
					<Form.Check
						inline
						label="Other"
						name="sex"
						value="OTHER"
						type="radio"
						{...register('sex')}
					/>
				</div>
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Date of Birth</Form.Label>
				<Form.Control
					name="dob"
					type="date"
					placeholder="Enter date of birth"
					{...register('dob')}
				/>
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Phone number</Form.Label>
				<Form.Control type="number" placeholder="Enter phone" {...register('phone')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Address</Form.Label>
				<Form.Control type="text" placeholder="Enter address" {...register('address')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Username</Form.Label>
				<Form.Control type="text" placeholder="Enter username" {...register('username')} />
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Password</Form.Label>
				<Form.Control type="text" placeholder="Enter password" {...register('password')} />
			</FormGroup>
			<FormGroup className="mb-3">
				{errors && Object.keys(errors).length > 0 && (
					<Alert variant="danger">
						<p style={{ marginBottom: 'unset' }}>{errors[Object.keys(errors)[0]]?.message}</p>
					</Alert>
				)}
			</FormGroup>
			<Button className="btn btn-success btn-lg btn-block" type="submit">
				Add new customer
			</Button>
		</Form>
	);
};

export default CustomerDialog;
