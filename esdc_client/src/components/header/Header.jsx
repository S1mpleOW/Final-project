import React from 'react';

const Header = ({ title, className, ...props }) => {
	return (
		<h3 className={`text-3xl capitalize font-semibold ${className}`} {...props}>
			{title}
		</h3>
	);
};

export default Header;
