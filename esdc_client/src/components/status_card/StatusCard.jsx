import React, { useEffect, useState } from 'react';
import { formatMoney } from '../../utils/helper';
import { useAuth } from '../store/useAuth';

import './status_card.css';

const StatusCard = ({ icon, title, link, isActive = false, onClick, id, disabled, fillterBy }) => {
	const [revenue, setRevenue] = useState(null);
	const user = useAuth((state) => state.user);
	useEffect(() => {
		(async () => {
			const currentYear = new Date().getFullYear();
			const currentMonth = new Date().getMonth() + 1;
			const response = await fetch(
				`${!disabled ? `${link}/${currentYear}/${currentMonth}` : link}`,
				{
					method: 'GET',
					headers: {
						'Content-Type': 'application/json',
						Authorization: `Bearer ${user.token}`,
					},
				}
			);
			const data = await response.json();
			const date = new Date();
			date.setMonth(data?.month - 1);
			setRevenue({
				...data,
				month: date.toLocaleString('en-US', {
					month: 'long',
				}),
			});
		})();
	}, [disabled, link, user.token]);

	return (
		<div
			className={`status-card ${isActive ? 'active' : ''} ${
				disabled ? 'pointer-events-none !bg-[#e8e8e8]' : ''
			}`}
			onClick={onClick}
		>
			<div className="status-card__icon">
				<i className={icon}></i>
			</div>
			<div className="status-card__info">
				<h4>{formatMoney(revenue?.total_price || 0)}</h4>
				<span>
					{title} / {fillterBy === 'MONTH' ? revenue?.month : revenue?.year}{' '}
				</span>
			</div>
		</div>
	);
};

export default StatusCard;
