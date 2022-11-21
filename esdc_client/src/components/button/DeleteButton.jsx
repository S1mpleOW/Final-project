import React from 'react';

const DeleteButton = React.forwardRef(({ onClick, ...props }, ref) => {
	return (
		<div className="flex items-center gap-2 customer__list-action" ref={ref} {...props}>
			<button className="customer__list-info" type="button" onClick={onClick}>
				<i className="bx bx-x-circle"></i>
			</button>
		</div>
	);
});

export default DeleteButton;
