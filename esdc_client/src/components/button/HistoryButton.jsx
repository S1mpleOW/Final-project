import React from 'react';
import { Link } from 'react-router-dom';
import './button.css';

const HistoryButton = React.forwardRef(({ onClick, to, ...props }, ref) => {
	if (to) {
		return (
			<div className="flex items-center gap-2 customer__list-action" {...props} ref={ref}>
				<Link to={to} className="customer__list-info">
					<i className="bx bx-history"></i>
				</Link>
			</div>
		);
	}
	return (
		<div className="flex items-center gap-2 customer__list-action" {...props} ref={ref}>
			<button className="customer__list-info" type="button" onClick={onClick}>
				<i className="bx bx-history"></i>
			</button>
		</div>
	);
});

export default HistoryButton;
