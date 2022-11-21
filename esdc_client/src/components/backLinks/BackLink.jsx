import React from 'react';
import { Link } from 'react-router-dom';

const BackLink = ({ to, name, className, ...props }) => {
	return (
		<Link
			to={to}
			className={`flex items-center text text-secondary decoration-transparent ${className}`}
			{...props}
		>
			<i className="bx bx-left-arrow-alt"></i>
			<strong>Back to {name}</strong>
		</Link>
	);
};

export default BackLink;
