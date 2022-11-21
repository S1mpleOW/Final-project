import React from 'react';
import { useEffect } from 'react';
import { useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import ServiceDialog from '../components/dialog/ServiceDialog';
import Header from '../components/header/Header';
import { useAuth } from '../components/store/useAuth';
import TableServices from '../components/table/TableServices';
import { SERVICES_GET } from '../utils/constant';

const servicesTableHead = ['', 'name', 'Sell price', 'action'];

const renderHead = (item, index) => <th key={index}>{item}</th>;

const Services = () => {
	const [services, setServices] = useState([]);
	const [show, setShow] = useState(false);
	const [isLoading, setIsLoading] = useState(false);
	const [showModalDeleteService, setShowModalDeleteService] = useState(false);
	// const [serviceDeleteId, setServiceDeleteId] = useState(null);
	const [serviceUpdateId, setServiceUpdateId] = useState(null);

	const handleClose = () => setShow(false);
	const handleShow = () => setShow(true);
	const user = useAuth((state) => state.user);

	// const handleRemove = (id) => {
	// 	// setServiceDeleteId(id);
	// 	setShowModalDeleteService(true);
	// };

	const handleUpdate = (id) => {
		setServiceUpdateId(id);
		setShow(true);
	};
	const handleFetchData = async () => {
		setIsLoading(true);
		const res = await fetch(SERVICES_GET, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${user.token}`,
			},
		});
		setIsLoading(false);
		console.log(res);
		const data = await res.json();
		console.log(data);
		setServices(data._embedded.services);
	};
	useEffect(() => {
		handleFetchData();
	}, [user.token]);
	return (
		<div className="">
			<div className="header">
				<Header title="Services available"></Header>
				<Button
					onClick={handleShow}
					className="flex items-center gap-2 btn btn-success"
					data-toggle="modal"
				>
					<i className="bx bxs-add-to-queue"></i>
					<span>Add service</span>
				</Button>
			</div>
			<div className="row">
				<div className="col-12">
					<div className="card">
						<div className="card__body">
							<TableServices
								limit="10"
								headData={servicesTableHead}
								renderHead={(item, index) => renderHead(item, index)}
								bodyData={services}
								isLoading={isLoading}
								// handleRemove={handleRemove}
								handleUpdate={handleUpdate}
							></TableServices>
						</div>
					</div>
				</div>
			</div>
			<Modal show={show} onHide={handleClose}>
				<Modal.Header>
					<Modal.Title>Add Product</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<ServiceDialog
						onHide={handleClose}
						setShow={setShow}
						handleFetchData={handleFetchData}
						id={serviceUpdateId}
					/>
				</Modal.Body>
			</Modal>
		</div>
	);
};

export default Services;
