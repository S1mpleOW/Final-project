import { yupResolver } from '@hookform/resolvers/yup';
import React, { useEffect } from 'react';
import { Alert, Button, Form, FormGroup } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import logo from '../../assets/images/football.png';
import { useAuth } from '../../components/store/useAuth';
import * as yup from 'yup';
import { REGISTER } from '../../utils/constant';

const schema = yup.object().shape({
	username: yup.string().required('Username is required'),
	password: yup
		.string()
		.required('Password is required')
		.min(6, 'Password must be at least 6 characters'),
	fullName: yup.string().required('Full name is required'),
	phone: yup
		.string()
		.required('Phone is required')
		.min(10, 'Phone must be at least 10 characters')
		.max(10, 'Phone must be at most 10 characters'),
	email: yup.string().required('Email is required'),
	address: yup.string().required('Address is required'),
	dob: yup.string().required('Date of birth is required'),
	sex: yup.string().required('Sex is required'),
	confirm: yup
		.string()
		.required('Confirm password is required')
		.oneOf([yup.ref('password'), null], 'Passwords must match'),
});

const Register = () => {
	const navigate = useNavigate();
	const user = useAuth((state) => state.user);
	const {
		handleSubmit,
		register,
		formState: { isSubmitting, isValid, errors },
		setError,
	} = useForm({
		mode: 'onChange',
		defaultValues: {
			username: '',
			password: '',
			fullName: '',
			phone: '',
			email: '',
			address: '',
			sex: '',
			dob: '',
			description: '',
		},
		resolver: yupResolver(schema),
	});
	useEffect(() => {
		if (user && (user.roles.includes('ADMIN') || user.roles.includes('EMPLOYEE'))) {
			navigate('/dashboard');
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [user]);
	const onSubmit = async (body) => {
		if (!isValid) return;
		if (isSubmitting) return;
		const formatDob = `${body.dob} 00:00:00`;
		body.dob = formatDob;
		const response = await fetch(REGISTER, {
			method: 'POST',
			body: JSON.stringify(body),
			headers: {
				'Content-Type': 'application/json',
			},
		});
		if (response.status === 200) {
			navigate('/login');
		}
		const data = await response.json();
		if (data.message) {
			setError('username', { type: 'manual', message: data.message });
		}
	};
	return (
		<div className="flex flex-col justify-center items-center h-screen bg-[#FAFAFB]">
			<div className="-mb-4 sidebar__logo">
				<img src={logo} alt="tdtu_logo" />
			</div>

			<div className="flex justify-center items-center flex-col w-[800px] shadow-sm shadow-black bg-white p-4 rounded-lg">
				<Form className="w-full" onSubmit={handleSubmit(onSubmit)}>
					<div className="flex justify-between gap-3">
						<div className="w-full">
							<FormGroup className="mb-3">
								<Form.Label>Full Name</Form.Label>
								<Form.Control
									type="text"
									placeholder="Enter full name"
									name="fullName"
									{...register('fullName')}
								/>
							</FormGroup>
							<FormGroup className="mb-3">
								<Form.Label>Email</Form.Label>
								<Form.Control
									type="email"
									placeholder="Enter email"
									name="email"
									{...register('email')}
								/>
							</FormGroup>

							<FormGroup className="mb-4">
								<Form.Label>Sex</Form.Label>
								<div className="mb-4">
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
										type="radio"
										value="FEMALE"
										{...register('sex')}
									/>
									<Form.Check
										inline
										label="Other"
										name="sex"
										type="radio"
										value="OTHER"
										{...register('sex')}
									/>
								</div>
							</FormGroup>
							<FormGroup className="mb-3">
								<Form.Label>Username</Form.Label>
								<Form.Control
									type="text"
									name="username"
									{...register('username')}
									placeholder="Enter username"
								/>
							</FormGroup>

							<FormGroup className="mb-3">
								<Form.Label>Description</Form.Label>
								<Form.Control
									as="textarea"
									name="description"
									{...register('description')}
									rows={3}
									placeholder="Enter description"
								/>
							</FormGroup>
						</div>
						<div className="w-full">
							<FormGroup className="mb-3">
								<Form.Label>Address</Form.Label>
								<Form.Control
									type="text"
									name="address"
									{...register('address')}
									placeholder="Enter address"
								/>
							</FormGroup>

							<FormGroup className="mb-3">
								<Form.Label>Phone number</Form.Label>
								<Form.Control
									type="number"
									name="phone"
									{...register('phone')}
									placeholder="Enter phone number"
								/>
							</FormGroup>

							<FormGroup className="mb-3">
								<Form.Label>Date of Birth</Form.Label>
								<Form.Control
									name="dob"
									type="date"
									{...register('dob')}
									placeholder="Enter date of birth"
								/>
							</FormGroup>

							<FormGroup className="mb-3">
								<Form.Label>Password</Form.Label>
								<Form.Control
									type="password"
									name="password"
									{...register('password')}
									placeholder="Enter password"
								/>
							</FormGroup>
							<FormGroup className="mb-3">
								<Form.Label>Confirm Password</Form.Label>
								<Form.Control
									type="password"
									name="confirm"
									{...register('confirm')}
									placeholder="Enter confirm password"
								/>
							</FormGroup>
						</div>
					</div>

					<FormGroup className="mb-3">
						{errors && Object.keys(errors).length > 0 && (
							<Alert variant="danger">
								<p style={{ marginBottom: 'unset' }}>{errors[Object.keys(errors)[0]]?.message}</p>
							</Alert>
						)}
					</FormGroup>

					<Button className="btn btn-success btn-lg btn-block" type="submit">
						Register
					</Button>
				</Form>
			</div>
		</div>
	);
};

export default Register;
