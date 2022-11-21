import React, { useEffect, useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import SupplierDialog from '../components/dialog/SupplierDialog';
import Header from '../components/header/Header';
import Loading from '../components/loading/Loading';
import DeleteConfirm from '../components/modal/DeleteConfirm';
import { useAuth } from '../components/store/useAuth';
import TableSupplier from '../components/table/TableSupplier';
import { SUPPLIER } from '../utils/constant';

const supplierTableHead = ['', 'name', 'phone', 'address', 'note', 'action'];

const renderHead = (item, index) => <th key={index}>{item}</th>;

const Suppliers = () => {
	const [show, setShow] = useState(false);
	const [isLoading, setIsLoading] = useState(false);
	const [suppliers, setSuppliers] = useState([]);
	const [showModalDeleteSupplier, setShowModalDeleteSupplier] = useState(false);
	const [supplierDeleteId, setSupplierDeleteId] = useState(null);

	const [supplierUpdateId, setSupplierUpdateId] = useState(null);
	const user = useAuth((state) => state.user);
	const handleShow = () => {
		setShow(true);
		setSupplierUpdateId(null);
	};
	const handleClose = () => setShow(false);
	const handleRemove = (id) => {
		setSupplierDeleteId(id);
		setShowModalDeleteSupplier(true);
	};

	useEffect(() => {
		handleFetchData();
	}, []);

	const handleDelete = async () => {
		if (!supplierDeleteId) return;
		setIsLoading(true);
		const response = await fetch(`${SUPPLIER}/${supplierDeleteId}`, {
			headers: {
				Authorization: `Bearer ${user.token}`,
			},
			method: 'DELETE',
		});
		if (response?.status === 204) {
			handleFetchData();
		}
		setIsLoading(false);
		setShowModalDeleteSupplier(false);
	};

	const handleUpdate = (id) => {
		setSupplierUpdateId(id);
		setShow(true);
	};

	const handleFetchData = async () => {
		setIsLoading(true);
		const response = await fetch(SUPPLIER, {
			headers: {
				Authorization: `Bearer ${user.token}`,
			},
			method: 'GET',
		});
		const data = await response.json();
		console.log(data);
		setIsLoading(false);
		if (data?._embedded?.suppliers && data?._embedded?.suppliers.length > 0) {
			setSuppliers([...data?._embedded?.suppliers]);
		} else {
			console.error(data);
		}
	};

	if (!isLoading) {
		return (
			<div>
				<div className="flex items-center justify-between mb-2 header">
					<Header title="Suppliers"></Header>
					<Button
						onClick={handleShow}
						className="flex items-center gap-2 text-black btn btn-success"
						data-toggle="modal"
					>
						<i className="bx bxs-add-to-queue"></i>
						<span>Add supplier</span>
					</Button>
				</div>
				<div className="row">
					<div className="col-12">
						<div className="card">
							<div className="card__body">
								<TableSupplier
									limit="10"
									headData={supplierTableHead}
									renderHead={(item, index) => renderHead(item, index)}
									bodyData={suppliers}
									isLoading={isLoading}
									handleRemove={handleRemove}
									handleUpdate={handleUpdate}
								/>
							</div>
						</div>
					</div>
				</div>
				<Modal show={show} onHide={handleClose}>
					<Modal.Header>
						<Modal.Title>{supplierUpdateId ? `Update` : 'Add'} supplier</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						<SupplierDialog
							setShow={setShow}
							handleFetchData={handleFetchData}
							id={supplierUpdateId}
						></SupplierDialog>
					</Modal.Body>
				</Modal>
				{supplierDeleteId && (
					<DeleteConfirm
						show={showModalDeleteSupplier}
						handleClose={() => setShowModalDeleteSupplier(false)}
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

export default Suppliers;
