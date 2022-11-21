import React from 'react';
import { useState } from 'react';
import { formatMoney } from '../utils/helper';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import { useEffect } from 'react';
import { useAuth } from '../components/store/useAuth';
import { useParams } from 'react-router-dom';
import {
	CUSTOMERS_GET,
	EMPLOYEES_GET,
	HISTORY_TRANSACTION_CUSTOMER,
	HISTORY_TRANSACTION_EMPLOYEE,
} from '../utils/constant';
import Loading from '../components/loading/Loading';
import Alert from '@mui/material/Alert';

const columns = [
	{ id: 'Fullname', label: 'Fullname', minWidth: 170 },
	{ id: 'Phone', label: 'Phone', minWidth: 170 },
	{
		id: 'Type',
		label: 'Type',
		minWidth: 170,
		align: 'center',
	},
	{
		id: 'Status',
		label: 'Status',
		minWidth: 170,
		align: 'center',
		format: (value) => (
			<Alert variant="outlined" severity="info" className="max-w-[200px] mx-auto">
				{value}
			</Alert>
		),
	},
	{
		id: 'Total price',
		label: 'Total price',
		minWidth: 170,
		format: (value) => formatMoney(value),
	},
	{
		id: 'Transaction date',
		label: 'Transaction date',
		minWidth: 170,
		format: (value) => new Date(value).toLocaleDateString(),
	},
];

function createData(items, user) {
	const data = items.map((item) => {
		const receipt = item.bookedTicket
			? item.bookedTicket
			: item.importReceipt
			? item.importReceipt
			: item.serviceReceipt;

		return {
			id: item.createdAt,
			Fullname: user.account.fullName,
			Phone: user.account.phone,
			Type: item.type.split('_').join(' '),
			Status: receipt.paymentStatus,
			'Total price': receipt.totalPrice,
			'Transaction date': item.createdAt,
		};
	});
	return data;
}

const HistoryTransaction = ({ isCustomerHistoryTransaction }) => {
	const [page, setPage] = useState(0);
	const [rowsPerPage, setRowsPerPage] = useState(10);
	const [totalElements, setTotalElements] = useState(0);
	const [rows, setRows] = useState([]);
	const currentUser = useAuth((state) => state.user);
	const [user, setUser] = useState(null);
	const [isLoading, setIsLoading] = useState(true);
	const { id } = useParams();
	useEffect(() => {
		(async () => {
			setIsLoading(true);
			const response = await fetch(
				`${isCustomerHistoryTransaction ? CUSTOMERS_GET : EMPLOYEES_GET}/${id}`,
				{
					method: 'GET',
					headers: {
						'Content-Type': 'application/json',
						Authorization: `Bearer ${currentUser.token}`,
					},
				}
			);
			const data = await response.json();
			setUser(data);
			setIsLoading(false);
		})();
	}, [id, currentUser.token, isCustomerHistoryTransaction]);
	useEffect(() => {
		if (id) {
			setIsLoading(true);
			fetch(
				`${
					isCustomerHistoryTransaction ? HISTORY_TRANSACTION_CUSTOMER : HISTORY_TRANSACTION_EMPLOYEE
				}/${id}?page=${page}`,
				{
					method: 'GET',
					headers: {
						'Content-Type': 'application/json',
						Authorization: `Bearer ${currentUser.token}`,
					},
				}
			)
				.then((res) => res.json())
				.then((data) => {
					if (data.status === 200 && user) {
						setTotalElements(data.data.totalElements);
						setRows(createData(data.data.content, user));
					}
					setIsLoading(false);
				});
		}
	}, [id, currentUser.token, user, page, isCustomerHistoryTransaction]);

	const handleChangePage = (event, newPage) => {
		setPage(newPage);
	};

	const handleChangeRowsPerPage = (event) => {
		console.log(event);
		setRowsPerPage(+event.target.value);
		setPage(0);
	};

	return (
		<Paper sx={{ width: '100%', overflow: 'hidden' }}>
			<TableContainer sx={{ maxHeight: 1000 }}>
				<Table stickyHeader aria-label="sticky table">
					<TableHead>
						<TableRow>
							{columns.map((column) => (
								<TableCell
									key={column.id}
									align={column.align}
									style={{ minWidth: column.minWidth }}
								>
									{column.label}
								</TableCell>
							))}
						</TableRow>
					</TableHead>
					<TableBody>
						<TableRow>
							<TableCell colSpan="6">
								{isLoading && (
									<div className="flex justify-center">
										<Loading></Loading>
									</div>
								)}
							</TableCell>
						</TableRow>

						{!isLoading &&
							rows?.length > 0 &&
							rows.map((row) => {
								return (
									<TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
										{columns.map((column) => {
											const value = row[column.id];
											return (
												<TableCell key={column.id} align={column.align}>
													{column.format ? column.format(value) : value}
												</TableCell>
											);
										})}
									</TableRow>
								);
							})}
					</TableBody>
				</Table>
			</TableContainer>
			<TablePagination
				rowsPerPageOptions={[10]}
				component="div"
				count={totalElements}
				rowsPerPage={rowsPerPage}
				page={page}
				onPageChange={handleChangePage}
				onRowsPerPageChange={handleChangeRowsPerPage}
			/>
		</Paper>
	);
};

export default HistoryTransaction;
