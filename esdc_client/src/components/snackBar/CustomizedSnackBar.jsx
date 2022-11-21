import * as React from 'react';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import { useState } from 'react';
import { useNotify } from '../store/useNotify';

const Alert = React.forwardRef(function Alert(props, ref) {
	return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

function CustomizedSnackbars() {
	const open = useNotify((state) => state.open);
	const setOpen = useNotify((state) => state.setOpen);
	const setClose = useNotify((state) => state.setClose);
	const content = useNotify((state) => state.content);
	const type = useNotify((state) => state.type);
	const handleClose = (event, reason) => {
		if (reason === 'clickaway') {
			return;
		}
		setClose();
	};

	return (
		<Stack spacing={2} sx={{ width: '100%' }}>
			<Snackbar open={open} autoHideDuration={3000} onClose={handleClose}>
				<Alert onClose={handleClose} severity={type} sx={{ width: '100%' }}>
					{content}
				</Alert>
			</Snackbar>
		</Stack>
	);
}
export default CustomizedSnackbars;
