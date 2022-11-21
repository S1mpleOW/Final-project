import React, { useEffect, useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import FieldDialog from '../components/dialog/FieldDialog';
import FieldCard from '../components/field_card/FieldCard';
import Header from '../components/header/Header';
import { FIELDS_GET } from '../utils/constant';

const Fields = () => {
	const [fields, setFields] = useState([]);
	const [show, setShow] = useState(false);
	const [refresh, setRefresh] = useState(false);
	const handleClose = () => setShow(false);
	const handleShow = () => setShow(true);

	useEffect(() => {
		(async () => {
			const response = await fetch(FIELDS_GET);
			const data = await response.json();
			setFields(data._embedded.footballFields);
		})();
	}, [refresh]);
	console.log(fields);
	return (
		<div className="page-header">
			<div className="header">
				<Header title="All fields"></Header>
				<Button
					onClick={handleShow}
					className="flex items-center gap-2 btn btn-success"
					data-toggle="modal"
				>
					<i className="bx bxs-add-to-queue"></i>
					<span>Add field</span>
				</Button>
			</div>
			{fields?.length === 0 && (
				<div className="flex items-center justify-center">
					<h1 className="text-2xl uppercase">No Field</h1>
				</div>
			)}
			<div className="grid grid-cols-4 gap-4">
				{fields?.length > 0 &&
					fields.map((field) => (
						<FieldCard
							key={field.resourceId}
							id={field.resourceId}
							name={field.name}
							price={field.price}
							image={field.image}
							type={field.type}
							setRefresh={setRefresh}
						></FieldCard>
					))}
			</div>
			<Modal show={show} onHide={handleClose}>
				<Modal.Header>
					<Modal.Title>Add Field</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<FieldDialog handleClose={handleClose} setRefresh={setRefresh} />
				</Modal.Body>
			</Modal>
		</div>
	);
};

export default Fields;
