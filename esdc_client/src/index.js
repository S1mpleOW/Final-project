import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import './assets/css/grid.css';
import './assets/css/index.css';
import App from './App';
import './assets/boxicons-2.1.4/css/boxicons.min.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { AuthVerifyProvider } from './components/context/useAuthVerifyContext';

document.title = 'Đặt sân bóng đá';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
	<BrowserRouter>
		<AuthVerifyProvider>
			<App />
		</AuthVerifyProvider>
	</BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
