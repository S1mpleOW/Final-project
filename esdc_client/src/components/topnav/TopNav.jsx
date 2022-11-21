import React from 'react';

import './topnav.css';

import { Link } from 'react-router-dom';

import Dropdown from '../dropdown/Dropdown';

import notifications from '../../assets/jsonData/notification.json';

import user_menu from '../../assets/jsonData/user-menu.json';
import { useAuth } from '../store/useAuth';
import { AVATAR_API } from '../../utils/constant';

const renderNotification = (item, index) => (
	<div className="notification-item" key={index}>
		<i className={item.icon}></i>
		<span>{item.content}</span>
	</div>
);

const renderUserToggle = (user) => (
	<div className="topnav__right-user">
		<div className="topnav__right-user__image">
			<img src={AVATAR_API(user.name)} alt="" />
		</div>
		<div className="topnav__right-user__name">{user.display_name}</div>
	</div>
);

const renderUserMenu = (item, index) => (
	<Link key={index} to={item.link} style={{ textDecoration: 'none' }}>
		<div className="notification-item">
			<i className={item.icon}></i>
			<span>{item.content}</span>
		</div>
	</Link>
);

const TopNav = () => {
	const user = useAuth((state) => state.user);
	return (
		<div className="topnav">
			<div className="topnav__search">
				<input type="text" placeholder="Search here..." />
				<i className="bx bx-search"></i>
			</div>
			<div className="topnav__right">
				<div className="topnav__right-item">
					<Dropdown
						customToggle={() => renderUserToggle(user)}
						contentData={user_menu}
						renderItems={(item, index) => renderUserMenu(item, index)}
					/>
				</div>
			</div>
		</div>
	);
};

export default TopNav;
