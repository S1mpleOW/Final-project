import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../../components/store/useAuth';

const ProtectedAdmin = () => {
	const user = useAuth((state) => state.user);

	if (!user?.roles?.includes('ADMIN')) {
		return (
			<div className="flex flex-col justify-center items-center bg-[#FAFAFB] mt-10">
				<h1>You are not allowed to access this page</h1>
			</div>
		);
	}

	return <Outlet />;
};

export default ProtectedAdmin;
