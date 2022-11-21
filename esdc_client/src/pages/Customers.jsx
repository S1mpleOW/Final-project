import React, { useEffect } from 'react';

import Table from '../components/table/Table';

import CustomerDialog from '../components/dialog/CustomerDialog';
import { Button, Modal } from 'react-bootstrap';
import { useState } from 'react';
import Header from '../components/header/Header';
import { CUSTOMERS_GET } from '../utils/constant';
import Loading from '../components/loading/Loading';
import DeleteConfirm from '../components/modal/DeleteConfirm';
import { useAuth } from '../components/store/useAuth';
import { useNotify } from '../components/store/useNotify';

const customerTableHead = ['', 'name', 'email', 'phone', 'location', 'action'];

const renderHead = (item, index) => <th key={index}>{item}</th>;

const Customers = () => {
	const [show, setShow] = useState(false);
	const [isLoading, setIsLoading] = useState(false);
	const [customers, setCustomers] = useState([]);
	const [customerDeleteId, setCustomerDeleteId] = useState(null);
	const [showModalDeleteCustomer, setShowModalDeleteCustomer] = useState(false);
	const user = useAuth((state) => state.user);
	const setOpen = useNotify((state) => state.setOpen);
	const setContent = useNotify((state) => state.setContent);
	const setType = useNotify((state) => state.setType);
	const handleShow = () => setShow(true);
	const handleClose = () => setShow(false);
	const handleRemove = (id) => {
		setCustomerDeleteId(id);
		setShowModalDeleteCustomer(true);
	};

	const handleDelete = async () => {
		setShowModalDeleteCustomer(false);
		if (!customerDeleteId) return;
		setIsLoading(true);
		const response = await fetch(`${CUSTOMERS_GET}/${customerDeleteId}`, {
			headers: {
				Authorization: `Bearer ${user.token}`,
			},
			method: 'DELETE',
		});
		console.log(response);
		if (response?.status === 200) {
			handleFetchData();
			setType('success');
			setContent('Delete customer successfully');
			setOpen();
		}
		setShowModalDeleteCustomer(false);
		setIsLoading(false);
	};
	const handleFetchData = async () => {
		setIsLoading(true);
		const response = await fetch(CUSTOMERS_GET, {
			headers: {
				Authorization: `Bearer ${user.token}`,
			},
			method: 'GET',
		});
		const data = await response.json();
		setIsLoading(false);
		if (data.status === 200 && data?.data?.content.length > 0) {
			setCustomers([...data.data.content]);
		} else {
			console.error(response);
		}
	};

	useEffect(() => {
		handleFetchData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [user.token]);
	if (!isLoading) {
		return (
			<div>
				<div className="flex items-center justify-between mb-2 header">
					<Header title="customers"></Header>
					<Button
						onClick={handleShow}
						className="flex items-center gap-2 text-black btn btn-success"
						data-toggle="modal"
					>
						<i className="bx bxs-add-to-queue"></i>
						<span>Add Customer</span>
					</Button>
				</div>
				<div className="row">
					<div className="col-12">
						<div className="card">
							<div className="card__body">
								<Table
									limit="10"
									headData={customerTableHead}
									renderHead={(item, index) => renderHead(item, index)}
									bodyData={customers}
									isLoading={isLoading}
									handleRemove={handleRemove}
									isTableCustomer={true}
								></Table>
							</div>
						</div>
					</div>
				</div>
				<Modal show={show} onHide={handleClose}>
					<Modal.Header>
						<Modal.Title>Add Customer</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						<CustomerDialog setShow={setShow} handleFetchData={handleFetchData} />
					</Modal.Body>
					<Modal.Footer>
						<Button className="btn btn-danger btn-lg btn-block" onClick={handleClose}>
							Close
						</Button>
					</Modal.Footer>
				</Modal>
				{customerDeleteId && (
					<DeleteConfirm
						show={showModalDeleteCustomer}
						handleClose={() => setShowModalDeleteCustomer(false)}
						handleDelete={handleDelete}
					/>
				)}
			</div>
		);
	}
	return (
		<div className="flex justify-center">
			<Loading></Loading>
		</div>
	);
};

export default Customers;
