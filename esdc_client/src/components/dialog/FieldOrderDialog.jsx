import React, { useState } from 'react';
import { Button, DialogActions, TextField, Typography } from '@mui/material';
import { useForm } from 'react-hook-form';
import { useParams } from 'react-router-dom';
import { DateTimePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import Stack from '@mui/material/Stack';
import Alert from '@mui/material/Alert';
import { Form } from 'react-bootstrap';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { FIELD_BOOKED } from '../../utils/constant';
import { useAuth } from '../store/useAuth';
import Loading from '../loading/Loading';
import { useNotify } from '../store/useNotify';

const schema = yup.object().shape({
	field_id: yup.string().required('Field is required'),
	phone: yup
		.string()
		.required('Phone number is required')
		.min(10, 'Phone number must be at least 10 characters')
		.max(10, 'Phone number must be at most 10 characters'),
});

const FieldOrderDialog = ({ scheduler, setRefresh }) => {
	const { id } = useParams();
	const [startTime, setStartTime] = useState(scheduler.state.start.value);
	const [endTime, setEndTime] = useState(scheduler.state.end.value);
	const [loading, setLoading] = useState(false);
	const user = useAuth((state) => state.user);
	const {
		handleSubmit,
		register,
		formState: { isSubmitting, isValid, errors },
		setError,
	} = useForm({
		mode: 'onChange',
		defaultValues: {
			field_id: id,
			phone: '0342343249',
		},
		resolver: yupResolver(schema),
	});

	const setType = useNotify((state) => state.setType);
	const setContent = useNotify((state) => state.setContent);
	const setOpen = useNotify((state) => state.setOpen);

	if (!id) return;
	const onSubmit = async (data) => {
		if (!isValid) return;
		if (isSubmitting) return;
		setLoading(true);
		const body = {
			field_id: Number.parseInt(data.field_id),
			phone: data.phone,
			start_time: dayjs(startTime).format('YYYY-MM-DDTHH:mm:ss'),
			end_time: dayjs(endTime).format('YYYY-MM-DDTHH:mm:ss'),
		};
		const response = await fetch(FIELD_BOOKED, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${user.token}`,
			},
			body: JSON.stringify(body),
		});
		const responseData = await response.json();
		console.log(responseData);
		if (responseData.status === 200) {
			setRefresh((prev) => !prev);
			setType('success');
			setContent('Booked field successfully!');
			setOpen(true);
			setLoading(false);
			scheduler.close();
		} else {
			setError('phone', { message: responseData.message });
		}
	};
	if (isSubmitting || loading) {
		return (
			<div className="flex items-center justify-center w-full h-full p-5">
				<Loading />
			</div>
		);
	}
	return (
		<div>
			<Form onSubmit={handleSubmit(onSubmit)} className="p-3 pb-0">
				<Typography variant="h6">Order</Typography>
				<div
					className="flex flex-col gap-3 "
					style={{
						display: 'flex',
						flexDirection: 'column',
						gap: '20px',
						width: '400px',
						marginTop: '1rem',
					}}
				>
					<TextField label="Field" fullWidth disabled {...register('field_id')} variant="filled" />
					<TextField
						label="Phone number"
						fullWidth
						variant="filled"
						error={Object.keys(errors).length > 0 && !!errors['phone']['message']}
						{...register('phone')}
					/>
					<LocalizationProvider dateAdapter={AdapterDayjs}>
						<Stack spacing={3}>
							<DateTimePicker
								renderInput={(props) => <TextField variant="filled" {...props} />}
								label="Start time"
								value={startTime}
								onChange={(time) => setStartTime(time)}
							/>
							<DateTimePicker
								renderInput={(props) => <TextField variant="filled" {...props} />}
								label="End time"
								value={endTime}
								onChange={(time) => setEndTime(time)}
							/>
						</Stack>
					</LocalizationProvider>
				</div>
				<div className="mt-3">
					{errors && Object.keys(errors).length > 0 && (
						<Alert severity="error">{errors[Object.keys(errors)[0]]?.message}</Alert>
					)}
				</div>
				<DialogActions className="mt-3">
					<Button onClick={scheduler.close}>Cancel</Button>
					<Button type="submit" variant="contained">
						Confirm
					</Button>
				</DialogActions>
			</Form>
		</div>
	);
};

export default FieldOrderDialog;
