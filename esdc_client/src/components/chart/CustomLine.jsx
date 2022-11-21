import React, { useEffect, useState } from 'react';

import {
	Chart as ChartJS,
	CategoryScale,
	LinearScale,
	PointElement,
	LineElement,
	Title,
	Tooltip,
	Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);
const currentYear = new Date().getFullYear();
export const options = {
	responsive: true,
	plugins: {
		legend: {
			position: 'top',
		},
		title: {
			display: true,
			text: 'Total Revenue in ' + currentYear,
		},
	},
};
const labelsYear = [
	'January',
	'February',
	'March',
	'April',
	'May',
	'June',
	'July',
	'August',
	'September',
	'October',
	'November',
	'December',
];
const currentMonth = new Date().getMonth() + 1;
const labelsMonth = Array(new Date(currentYear, currentMonth, 0).getDate())
	.fill(0)
	.map((_, index) => `${index + 1}`);

// export const data = {
// 	labels,
// 	datasets: [
// 		{
// 			label: 'Total Revenue',
// 			data: [0, 20, 15, 30, 25, 40, 35, 50, 45, 60, 55, 70],
// 			borderColor: 'rgb(255, 99, 132)',
// 			backgroundColor: 'rgba(255, 99, 132, 0.5)',
// 		},
// 	],
// };

const CustomLine = ({ values }) => {
	const [dataShow, setDataShow] = useState({
		labelsMonth,
		datasets: [
			{
				label: 'Total Revenue in ' + currentYear,
				data: [],
				borderColor: 'rgb(255, 99, 132)',
				backgroundColor: 'rgba(255, 99, 132, 0.5)',
			},
		],
	});
	console.log(values);
	useEffect(() => {
		if (values && values?.total_price?.length > 0) {
			setDataShow({
				labels: labelsMonth,
				datasets: [
					{
						label: values.name || '',
						data: values.total_price,
						borderColor: 'rgb(255, 99, 132)',
						backgroundColor: 'rgba(255, 99, 132, 0.5)',
					},
				],
			});
		}
	}, [values]);

	return <Line options={options} data={dataShow} />;
};

export default CustomLine;
