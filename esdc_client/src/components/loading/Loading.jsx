import React from 'react';
import './loading.css';
const Loading = ({ className }) => {
	return (
		<div className={`lds-roller ${className}`}>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
		</div>
	);
};

export default Loading;
