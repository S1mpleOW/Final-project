import React from 'react';
import { useLayoutEffect } from 'react';
import { useAuth } from '../../components/store/useAuth';

const Logout = () => {
	const setUser = useAuth((state) => state.setUser);

	useLayoutEffect(() => {
		setUser(null);
		sessionStorage.removeItem('user');

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);
	return <div>Logout successfully</div>;
};

export default Logout;
