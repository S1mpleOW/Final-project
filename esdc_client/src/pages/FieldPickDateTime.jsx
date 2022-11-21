import { Scheduler } from '@aldabil/react-scheduler';
import React from 'react';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import BackLink from '../components/backLinks/BackLink';
import FieldOrderDialog from '../components/dialog/FieldOrderDialog';
import FieldBooked from '../components/field_card/FieldBooked';
import { useAuth } from '../components/store/useAuth';
import { useNotify } from '../components/store/useNotify';
import { CANCEL_ORDER_FIELD, FIELDS_BOOKED_WEEK } from '../utils/constant';

const FieldPickDateTime = () => {
	const { id } = useParams();
	const [fieldBooked, setFieldBooked] = React.useState([]);
	const [refresh, setRefresh] = React.useState(false);
	const user = useAuth((state) => state.user);
	const setOpen = useNotify((state) => state.setOpen);
	const setContent = useNotify((state) => state.setContent);
	const setType = useNotify((state) => state.setType);
	useEffect(() => {
		if (id) {
			fetch(FIELDS_BOOKED_WEEK, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${user.token}`,
				},
				body: JSON.stringify({
					field_id: id,
				}),
			})
				.then((res) => {
					if (res.status === 200) {
						return res.json();
					} else {
						throw new Error('Something went wrong');
					}
				})
				.then((data) => {
					console.log(data);
					if (data.status === 200) {
						const mappedData = data.data.map((item) => {
							return {
								event_id: item.id,
								title: `Booked`,
								start: new Date(item.start),
								end: new Date(item.end),
								phone: item.phone || '',
								total_price: item.total_price || '',
								editable: false,
								color: '#82CD47',
							};
						});
						setFieldBooked(mappedData);
					}
				});
		}
	}, [id, refresh, user.token]);
	if (!id) return;

	const handleCancelEvent = async (id) => {
		console.log(id);
		const response = await fetch(`${CANCEL_ORDER_FIELD}/${id}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${user.token}`,
			},
		});
		const data = await response.json();
		if (data.status === 200) {
			setRefresh((prev) => !prev);
			setOpen(true);
			setContent('Cancel order success');
			setType('success');
		} else {
			setOpen(true);
			setContent(data.message);
			setType('error');
		}
	};
	return (
		<>
			<BackLink to="/fields" name="fields"></BackLink>
			<Scheduler
				view="week"
				day={null}
				month={null}
				events={fieldBooked}
				week={{
					weekDays: [0, 1, 2, 3, 4, 5, 6],
					weekStartOn: 1,
					startHour: 5,
					endHour: 22,
					step: 60,
				}}
				eventRenderer={(event) => <FieldBooked {...event} />}
				customEditor={(scheduler) => (
					<FieldOrderDialog setRefresh={setRefresh} scheduler={scheduler} />
				)}
				onDelete={handleCancelEvent}
				viewerExtraComponent={(fields, event) => {
					return (
						<div className="flex flex-col">
							<div className="flex items-center gap-2 mt-2">
								<div className="text-lg">Phone:</div>
								<div className="text-lg font-bold">{event?.phone || ''}</div>
							</div>
							<div className="flex items-center gap-2 mt-2">
								<div className="text-lg">Total price:</div>
								<div className="text-lg font-bold">{event?.total_price || ''}</div>
							</div>
						</div>
					);
				}}
			/>
		</>
	);
};

export default FieldPickDateTime;
