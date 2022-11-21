import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import logo from '../../assets/images/football.png';
import * as yup from 'yup';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { Alert, FormGroup } from 'react-bootstrap';
import { LOGIN } from '../../utils/constant';
import { useAuth } from '../../components/store/useAuth';
import { useNotify } from '../../components/store/useNotify';

const schema = yup.object().shape({
	username: yup.string().required('Username is required'),
	password: yup.string().required('Password is required'),
});

const LogIn = () => {
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
		},
		resolver: yupResolver(schema),
	});
	const navigate = useNavigate();
	const user = useAuth((state) => state.user);
	const setUser = useAuth((state) => state.setUser);
	const notifyOpen = useNotify((state) => state.setOpen);
	const notifyContent = useNotify((state) => state.setContent);
	const notifyType = useNotify((state) => state.setType);
	useEffect(() => {
		if (user && (user.roles.includes('ADMIN') || user.roles.includes('EMPLOYEE'))) {
			navigate('/dashboard');
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);
	const onSubmit = async (body) => {
		if (!isValid) return;
		if (isSubmitting) return;
		const response = await fetch(LOGIN, {
			method: 'POST',
			body: JSON.stringify(body),
			headers: {
				'Content-Type': 'application/json',
			},
		});

		const data = await response.json();
		console.log(data);
		if (data.status !== 200) {
			setError('password', {
				type: 'manual',
				message: data.message,
			});
			return;
		}
		if (!data.roles.includes('ADMIN') && !data.roles.includes('EMPLOYEE')) {
			setError('password', {
				type: 'custom',
				message: 'Only admin or employee can register this website',
			});
			return;
		}
		console.log(data);
		setUser({ password: data.password, ...data });
		sessionStorage.setItem('user', JSON.stringify(data));
		notifyType('success');
		notifyContent('Login successfully');
		notifyOpen();
		navigate('/dashboard');
	};

	return (
		<div className="flex flex-col justify-center items-center h-screen bg-[#FAFAFB]">
			<Link to="/login" className="-mb-4 sidebar__logo">
				<img src={logo} alt="tdtu_logo" />
			</Link>

			<form onSubmit={handleSubmit(onSubmit)}>
				<div className="flex justify-center items-center flex-col w-[500px] shadow-sm shadow-black bg-white p-4 rounded-lg">
					<div className="w-full my-2 form-group">
						<label htmlFor="username" className="mb-2">
							Username
						</label>
						<input
							type="username"
							className="form-control"
							id="username"
							name="username"
							placeholder="Enter username"
							{...register('username')}
						/>
					</div>
					<div className="w-full my-2 form-group">
						<label htmlFor="password" className="mb-2">
							Password
						</label>
						<input
							type="password"
							className="form-control"
							id="password"
							placeholder="Password"
							name="password"
							{...register('password')}
						/>
					</div>
					<FormGroup className="w-full my-2">
						{errors && Object.keys(errors).length > 0 && (
							<Alert variant="danger">
								<p style={{ marginBottom: 'unset' }}>{errors[Object.keys(errors)[0]]?.message}</p>
							</Alert>
						)}
					</FormGroup>
					<button className="w-full my-2 btn btn-primary" type="submit">
						Login
					</button>
				</div>
			</form>
		</div>
	);
};

export default LogIn;
