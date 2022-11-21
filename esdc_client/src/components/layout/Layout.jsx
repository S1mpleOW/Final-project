import React from 'react';
import './layout.css';

import TopNav from '../topnav/TopNav';
import Sidebar from '../sidebar/Sidebar';

import { Outlet } from 'react-router-dom';
import CustomizedSnackbars from '../snackBar/CustomizedSnackBar';

const Layout = () => {
	return (
		<div className="layout">
			<Sidebar />
			<div className="layout__content">
				<TopNav />
				<Outlet />
			</div>
			<CustomizedSnackbars></CustomizedSnackbars>
		</div>
	);
};

export default Layout;
