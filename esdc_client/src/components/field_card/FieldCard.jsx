import { Button } from '@mui/material';
import React, { useState } from 'react';
import { Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { formatMoney } from '../../utils/helper';
import UpdateFieldDialog from '../dialog/UpdateFieldDialog';
import './index.css';
const FieldCard = ({ id, name, price, image, setRefresh, type }) => {
	const navigate = useNavigate();
	const [update, setUpdate] = useState(false);
	if (!id) return;
	const handleShow = () => {
		setUpdate(true);
	};
	const handleClose = () => setUpdate(false);
	return (
		<div className="flex flex-col p-3 border-0 rounded-lg card">
			<div className="top flex-shrink-0 w-full h-[250px] mx-auto rounded-lg overflow-hidden">
				<img src={image} alt="" className="object-cover w-full h-full" />
			</div>
			<div className="mt-auto">
				<div className="mt-3">
					<h1 className="text-5xl font-semibold text-gray-800">{name}</h1>
					<div className="flex items-center my-3">
						<p className="text-lg text-gray-800">
							<strong>{formatMoney(price)}</strong>
							<span className="text-gray-800 text-md">/hour</span>
						</p>
						<p className="ml-auto text-lg text-gray-800">
							<span>Loại: </span>
							<strong className="text-gray-800 text-md">sân {type.split('_')[1]}</strong>
						</p>
					</div>
				</div>
				<div className="flex flex-col gap-3">
					<button
						className="px-4 py-2 cursor-pointer bg-gradient-to-r from-[#557153] to-[#A9AF7E] text-white w-full btn-hover uppercase"
						onClick={() => navigate(`/fields/${id}`)}
					>
						Book field
					</button>
					<Button
						variant="contained"
						fullWidth
						style={{ backgroundColor: '#6D9886', borderRadius: '20px' }}
						className="text-gray-700"
						onClick={handleShow}
					>
						Update
					</Button>
				</div>
			</div>
			<Modal show={update} onHide={handleClose}>
				<Modal.Header>
					<Modal.Title>Update Field</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<UpdateFieldDialog setRefresh={setRefresh} id={id} handleClose={handleClose} />
				</Modal.Body>
			</Modal>
		</div>
	);
};

export default FieldCard;
