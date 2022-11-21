import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../../components/store/useAuth';
const Protected = () => {
	const user = useAuth((state) => state.user);

	if (!user || !user?.roles) {
		return <Navigate to="/login" />;
	}
	if (!user?.roles?.includes('ADMIN') && !user?.roles?.includes('EMPLOYEE')) {
		return <Navigate to="/login" />;
	}

	return <Outlet />;
};
export default Protected;
