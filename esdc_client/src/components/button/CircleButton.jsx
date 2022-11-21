import React from 'react';

const CircleButton = ({ children, onClick, ...props }) => {
	return (
		<button
			className="w-[45px] h-[45px] rounded-full border-teal-800 hover:bg-teal-800 hover:text-white transition-all text-3xl flex items-center justify-center "
			style={{ border: '3px solid' }}
			{...props}
			onClick={onClick}
		>
			{children}
		</button>
	);
};

export default CircleButton;
