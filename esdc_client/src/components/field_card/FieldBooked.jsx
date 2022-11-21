import React from 'react';

const FieldBooked = ({ start, end, title }) => {
	return (
		<div className="flex flex-col items-center justify-center w-full h-full gap-1 my-auto">
			<div className="flex items-center gap-1">
				<span>
					{start.toLocaleTimeString('en-US', {
						timeStyle: 'short',
					})}
				</span>
				-
				<span>
					{end.toLocaleTimeString('en-US', {
						timeStyle: 'short',
					})}
				</span>
			</div>
			<div>{title}</div>
		</div>
	);
};

export default FieldBooked;
