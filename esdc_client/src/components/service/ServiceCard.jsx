import React from 'react';
import { Button as ButtonMui } from '@mui/material';
import { formatMoney } from '../../utils/helper';
import { useNavigate } from 'react-router-dom';
const ServiceCard = ({ name, priceSell, image, id }) => {
	const navigate = useNavigate();
	if (!id) return;
	const handleNavigate = () => {
		navigate(`/services/${id}`);
	};
	return (
		<div className="col-md-12">
			<div className="p-3 border-0 card">
				<div className="card-header w-full h-[300px]">
					<img src={image} alt="service" className="object-cover w-full h-full" />
				</div>
				<div className="mt-3 card-body">
					<h5 className="text-3xl font-bold card-title">{name}</h5>
					<p className="text-lg card-text">
						Giá <strong>{formatMoney(priceSell)}</strong>
					</p>
					<div className="flex flex-col gap-3">
						<ButtonMui variant="contained" color="success" onClick={handleNavigate}>
							VIEW ITEM
						</ButtonMui>
						<div className="flex gap-2">
							<ButtonMui
								variant="contained"
								color="primary"
								// onClick={handleClose}
								fullWidth
							>
								EDIT
							</ButtonMui>
							<ButtonMui
								variant="contained"
								color="error"
								fullWidth
								// onClick={handleClose}
							>
								DELETE
							</ButtonMui>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default ServiceCard;
