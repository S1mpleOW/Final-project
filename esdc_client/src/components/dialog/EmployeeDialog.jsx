import React, { useEffect, useState } from 'react';
import { Form, FormGroup, Button, Alert } from 'react-bootstrap';
import { EMPLOYEE_POST, GROUPS_GET } from '../../utils/constant';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import Loading from '../loading/Loading';
import { useAuth } from '../store/useAuth';
import { useNotify } from '../store/useNotify';

const schema = yup.object().shape({
	fullName: yup.string().required('Full name is required'),
	email: yup.string().required('Email is required'),
	phone: yup.string().required('Phone is required'),
	address: yup.string().required('Address is required'),
	identityCard: yup.string().required('Identity card is required'),
	sex: yup.string().oneOf(['MALE', 'FEMALE', 'OTHER']).required('Sex is required'),
	dob: yup.string().required('Date of birth is required'),
	fieldGroupId: yup.number().required('Field group is required'),
	username: yup.string().required('Username is required'),
	password: yup
		.string()
		.min(6, 'Password must be at least 6 characters')
		.required('Password is required'),
});

const EmployeeDialog = ({ setShow, handleFetchData }) => {
	const [fieldGroups, setFieldGroups] = useState([]);
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
			fullName: 'ka',
			email: 'ka@gmail.com',
			phone: '0326042022',
			address: '360 pham van dong',
			identityCard: '1900805822',
			dob: '',
			sex: 'MALE',
			fieldGroupId: 1,
			username: 'kavjp',
			password: '123456',
			description: 'Shift 3',
			salary: 1000000,
		},
		resolver: yupResolver(schema),
	});

	const setType = useNotify((state) => state.setType);
	const setContent = useNotify((state) => state.setContent);
	const setOpen = useNotify((state) => state.setOpen);

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
			console.log(data);
			if (data?._embedded?.groups && data?._embedded?.groups.length > 0) {
				setFieldGroups([...data?._embedded?.groups]);
			}
		})();
	}, [user.token]);

	console.log(fieldGroups);
	const onSubmit = (data) => {
		if (isValid && !isSubmitting) {
			const date = data['dob'];
			const formatDob = `${date} 00:00:00`;
			const body = {
				...data,
				dob: formatDob,
			};
			fetch(EMPLOYEE_POST, {
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
							identityCard: '',
							dob: '',
							sex: 'MALE',
							fieldGroupId: 1,
							username: '',
							password: '',
							description: '',
							salary: 0,
						});
						setType('success');
						setContent('Create employee successfully');
						setOpen();
						setShow(false);
					} else {
						setError('error', { message: data.message });
					}
				});
		}
	};
	if (isSubmitting) {
		return (
			<div className="flex items-center justify-center">
				<Loading></Loading>
			</div>
		);
	}
	return (
		<Form onSubmit={handleSubmit(onSubmit)}>
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
				<Form.Control type="email" placeholder="Enter email" name="email" {...register('email')} />
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
				<Form.Label>Date of Birth</Form.Label>
				<Form.Control
					name="dob"
					type="date"
					{...register('dob')}
					placeholder="Enter date of birth"
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
				<Form.Label>Address</Form.Label>
				<Form.Control
					type="text"
					name="address"
					{...register('address')}
					placeholder="Enter address"
				/>
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Identity card</Form.Label>
				<Form.Control
					type="text"
					name="identityCard"
					{...register('identityCard')}
					placeholder="Enter identity card"
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

			<FormGroup className="mb-3">
				<Form.Label>Salary</Form.Label>
				<Form.Control
					{...register('salary')}
					type="text"
					name="salary"
					placeholder="Enter salary"
				/>
			</FormGroup>
			<FormGroup className="mb-3">
				<Form.Label>Group</Form.Label>
				<Form.Select aria-label="SELECT" {...register('fieldGroupId')}>
					<option value="DEFAULT" disabled hidden>
						Please select group
					</option>
					{fieldGroups?.length > 0 &&
						fieldGroups.map((fieldGroup, index) => {
							return (
								<option key={index} value={fieldGroup.resourceId}>
									{fieldGroup.name}
								</option>
							);
						})}
				</Form.Select>
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
				<Form.Label>Password</Form.Label>
				<Form.Control
					type="password"
					name="password"
					{...register('password')}
					placeholder="Enter password"
				/>
			</FormGroup>

			<FormGroup className="mb-3">
				{errors && Object.keys(errors).length > 0 && (
					<Alert variant="danger">
						<p style={{ marginBottom: 'unset' }}>{errors[Object.keys(errors)[0]]?.message}</p>
					</Alert>
				)}
			</FormGroup>

			<Button className="btn btn-success btn-lg btn-block" type="submit">
				Add new employee
			</Button>
		</Form>
	);
};

export default EmployeeDialog;
