import React, { useEffect, useMemo, useState } from 'react';

import StatusCard from '../components/status_card/StatusCard';

import statusCardItem from '../assets/jsonData/status-card.json';
import Header from '../components/header/Header';
import CustomLine from '../components/chart/CustomLine';
import { useAuth } from '../components/store/useAuth';
import Loading from '../components/loading/Loading';

const Dashboard = () => {
	const [activeTab, setActiveTab] = useState(1);
	const [fillterBy, setFillterBy] = useState('MONTH');
	const [isLoading, setIsLoading] = useState(false);
	const revenueLink = useMemo(() => {
		const itemActive = statusCardItem.find((item) => item.id === activeTab);
		return fillterBy === 'MONTH' ? itemActive.revenueByMonth : itemActive.revenueByYear;
	}, [activeTab, fillterBy]);
	const [data, setData] = useState(null);
	const user = useAuth((state) => state.user);
	const handleNextClick = () => {
		setActiveTab((prev) => (prev === statusCardItem.length - 1 ? 1 : prev + 1));
	};

	const handlePrevClick = () => {
		setActiveTab((prev) => (prev === 1 ? statusCardItem.length - 1 : prev - 1));
	};

	useEffect(() => {
		(async () => {
			const currentYear = new Date().getFullYear();
			const currentMonth = new Date().getMonth() + 1;
			if (revenueLink) {
				setIsLoading(true);
				const response = await fetch(
					`${revenueLink}/${fillterBy === 'MONTH' ? currentMonth : currentYear}`,
					{
						method: 'GET',
						headers: {
							'Content-Type': 'application/json',
							Authorization: `Bearer ${user.token}`,
						},
					}
				);
				const data = await response.json();
				if (data.status) {
					setData(data);
					console.log(data);
				}
				setIsLoading(false);
			}
		})();
	}, [fillterBy, revenueLink, user.token]);

	return (
		<div>
			<Header title="Dashboard" className="mb-3"></Header>
			<div className="grid grid-cols-5 gap-3 mb-3">
				{statusCardItem.map((item) => (
					<StatusCard
						key={item.title}
						{...item}
						isActive={item.id === activeTab}
						onClick={() => {
							setActiveTab(item.id);
						}}
						disabled={item.disabled}
						fillterBy={fillterBy}
					/>
				))}
			</div>
			{isLoading ? (
				<div className="flex justify-center">
					<Loading></Loading>
				</div>
			) : (
				<>
					<div className="flex items-center justify-between mb-3">
						<div className="flex items-center">
							<button
								className={`px-3 py-1 rounded-md mr-2 ${
									fillterBy === 'MONTH' ? 'bg-[#567d46] text-white' : 'bg-gray-200'
								}`}
								onClick={() => {
									setFillterBy('MONTH');
								}}
							>
								Tháng
							</button>
							<button
								className={`px-3 py-1 rounded-md ${
									fillterBy === 'YEAR' ? 'bg-[#567d46] text-white' : 'bg-gray-200'
								}`}
								onClick={() => {
									setFillterBy('YEAR');
								}}
							>
								Năm
							</button>
						</div>
						<div className="flex items-center">
							<button
								className="px-3 py-1 mr-2 bg-gray-200 rounded-md hover:bg-[#567d46] transition-colors hover:text-white ease-linear"
								onClick={handlePrevClick}
							>
								<i className="bx bx-chevron-left"></i>
							</button>
							<button
								className="px-3 py-1 bg-gray-200 rounded-md hover:bg-[#567d46] transition-colors hover:text-white ease-linear"
								onClick={handleNextClick}
							>
								<i className="bx bx-chevron-right"></i>
							</button>
						</div>
					</div>
					<div className="pb-5 mt-2">
						<CustomLine values={data}></CustomLine>
					</div>
				</>
			)}
		</div>
	);
};

export default Dashboard;
