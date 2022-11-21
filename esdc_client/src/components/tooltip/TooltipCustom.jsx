import { Tooltip } from '@mui/material';
import React from 'react';
const TooltipCustom = ({ placement, content, children, ...props }) => {
	return (
		<Tooltip title={content} placement={placement} {...props}>
			{children}
		</Tooltip>
	);
};

export default TooltipCustom;
