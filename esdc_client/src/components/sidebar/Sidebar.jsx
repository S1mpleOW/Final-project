import React from 'react';

import './sidebar.css';

import logo from '../../assets/images/football.png';

import sidebar_items from '../../assets/jsonData/sidebar-routes.json';
import { NavLink } from 'react-router-dom';

const SidebarItem = (props) => {
	return (
		<div className="sidebar__item">
			<div className={`sidebar__item-inner`}>
				<i className={props.icon}></i>
				<span>{props.title}</span>
			</div>
		</div>
	);
};

const Sidebar = (props) => {
	return (
		<div className="sidebar">
			<div className="sidebar__logo">
				<img src={logo} alt="tdtu_logo" />
			</div>
			{sidebar_items.map((item) => (
				<NavLink
					to={item.route}
					key={item.display_name}
					className={({ isActive }) => (isActive ? 'active-nav-link' : '')}
				>
					<SidebarItem title={item.display_name} icon={item.icon} />
				</NavLink>
			))}
		</div>
	);
};

export default Sidebar;
