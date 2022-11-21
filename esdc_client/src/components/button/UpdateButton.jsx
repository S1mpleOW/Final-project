import React from 'react';
import './button.css';

const UpdateButton = React.forwardRef(({ onClick, ...props }, ref) => {
	return (
		<div className="flex items-center gap-2 customer__list-action" {...props} ref={ref}>
			<button className="customer__list-info" type="button" onClick={onClick}>
				<i className="bx bx-refresh"></i>
			</button>
		</div>
	);
});

export default UpdateButton;
