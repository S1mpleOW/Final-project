import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

import Dashboard from './pages/Dashboard';
import Customers from './pages/Customers';
import Layout from './components/layout/Layout';
import Product from './pages/Product';
import Employee from './pages/Employee';
import Suppliers from './pages/Suppliers';
import ProductSupplier from './pages/ProductSupplier';
import LogIn from './pages/Author/LogIn';
import Register from './pages/Author/Register';
import Protected from './pages/Author/Protected';
import Logout from './pages/Author/Logout';
import Profile from './pages/Profile';
import Services from './pages/Services';
import Fields from './pages/Fields';
import FieldPickDateTime from './pages/FieldPickDateTime';
import ProductSell from './pages/ProductSell';
import ProtectedAdmin from './pages/Author/ProtectedAdmin';
import HistoryTransaction from './pages/HistoryTransaction';

const App = () => {
	return (
		<Routes>
			<Route element={<Protected />}>
				<Route element={<Layout></Layout>}>
					<Route path="/" element={<Navigate to="/dashboard" />} />
					<Route element={<ProtectedAdmin />}>
						<Route path="/dashboard" element={<Dashboard />} />
						<Route
							path="/customers/history-transaction/:id"
							element={
								<HistoryTransaction isCustomerHistoryTransaction={true}></HistoryTransaction>
							}
						></Route>
						<Route
							path="/employees/history-transaction/:id"
							element={<HistoryTransaction></HistoryTransaction>}
						></Route>
						<Route path="/customers" element={<Customers />} />
						<Route path="/employees" element={<Employee />} />
					</Route>
					<Route path="/products" element={<Product />} />
					<Route path="/suppliers/:id" element={<ProductSupplier />} />
					<Route path="/suppliers" element={<Suppliers />} />
					<Route path="/logout" element={<Logout />} />
					<Route path="/profile" element={<Profile />}></Route>
					<Route path="/services/:id" element={<ProductSell />}></Route>
					<Route path="/services" element={<Services />}></Route>
					<Route path="/fields/:id" element={<FieldPickDateTime />}></Route>
					<Route path="/fields" element={<Fields />}></Route>
				</Route>
			</Route>
			<Route path="/login" element={<LogIn />} />
			{/* <Route path="/register" element={<Register />} /> */}
		</Routes>
	);
};

export default App;
