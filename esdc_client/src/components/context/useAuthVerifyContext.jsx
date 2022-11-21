import { createContext, useContext, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../store/useAuth';

const AuthVerifyContext = createContext();
const parseJwt = (token) => {
	try {
		return JSON.parse(atob(token.split('.')[1]));
	} catch (e) {
		return null;
	}
};
const AuthVerifyProvider = (props) => {
	const user = useAuth((state) => state.user);
	const navigate = useNavigate();
	let location = useLocation();
	useEffect(() => {
		if (user && user.token) {
			const decodedJwt = parseJwt(user.token);
			const expirationTime = decodedJwt.exp;

			const expiryTime = new Date(expirationTime).getTime();
			const currentTime = Date.now() / 1000;

			if (currentTime >= expiryTime) {
				navigate('/logout');
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [location]);
	return <AuthVerifyContext.Provider value={{ user }}>{props.children}</AuthVerifyContext.Provider>;
};

const useAuthVerifyContext = () => {
	const context = useContext(AuthVerifyContext);
	if (typeof context === 'undefined') {
		throw new Error('useAuth must be used within a AuthProvider');
	}
	return context;
};

export { AuthVerifyProvider, useAuthVerifyContext };
