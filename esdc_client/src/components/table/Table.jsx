import React, { useEffect, useState } from 'react';
import DeleteButton from '../button/DeleteButton';
import HistoryButton from '../button/HistoryButton';
import ProfileButton from '../button/ProfileButton';
import Loading from '../loading/Loading';
import Profile from '../profile/Profile';
import TooltipCustom from '../tooltip/TooltipCustom';

import './table.css';
let pages = 1;

let range = [];
const Table = (props) => {
	const [dataShow, setDataShow] = useState(
		props.limit && props.bodyData ? props?.bodyData.slice(0, Number(props.limit)) : props?.bodyData
	);
	const [currPage, setCurrPage] = useState(0);
	const [profileShow, setProfileShow] = useState(false);
	const [user, setUser] = useState(null);
	if (props.limit !== undefined) {
		let page = Math.floor(props.bodyData.length / Number(props.limit));
		pages = props.bodyData.length % Number(props.limit) === 0 ? page : page + 1;
		range = [...Array(pages).keys()];
	}

	useEffect(() => {
		setDataShow(
			props.limit && props.bodyData
				? props?.bodyData.slice(0, Number(props.limit))
				: props?.bodyData
		);
	}, [props.bodyData, props.limit]);

	const selectPage = (page) => {
		const start = Number(props.limit) * page;
		const end = start + Number(props.limit);
		setDataShow(props.bodyData.slice(start, end));
		setCurrPage(page);
	};

	const handleFetchUser = (id) => {
		setUser(props.bodyData.find((item) => item.id === id || item.resourceId === id));
		setProfileShow(true);
	};
	return (
		<div>
			<div className="table-wrapper">
				<table>
					{props.headData && props.renderHead ? (
						<thead>
							<tr>{props.headData.map((item, index) => props.renderHead(item, index))}</tr>
						</thead>
					) : null}
					<tbody>
						{props.isLoading ? (
							<tr className="">
								<td colSpan="6" className="text-center">
									<Loading></Loading>
								</td>
							</tr>
						) : props.bodyData && props.bodyData.length > 0 ? (
							dataShow.map((item) => {
								return (
									<tr key={item?.id || item?.resourceId}>
										<td>{item?.id || item?.resourceId}</td>
										<td>{item?.account.fullName}</td>
										<td>{item?.account.email}</td>
										<td>{item?.account.phone}</td>
										<td>{item?.account.address}</td>
										<td className="flex items-center gap-2">
											<TooltipCustom content="Detail" placement="top-end" arrow>
												<ProfileButton
													onClick={() => {
														handleFetchUser(item?.id || item?.resourceId);
													}}
												/>
											</TooltipCustom>

											<TooltipCustom content="Delete" placement="top" arrow>
												<DeleteButton
													onClick={() => props.handleRemove(item?.id || item?.resourceId)}
												></DeleteButton>
											</TooltipCustom>
											<TooltipCustom content="History transaction" placement="top-start" arrow>
												<HistoryButton
													to={`/${
														props.isTableCustomer ? 'customers' : 'employees'
													}/history-transaction/${item?.id || item?.resourceId}`}
												></HistoryButton>
											</TooltipCustom>
										</td>
									</tr>
								);
							})
						) : null}
					</tbody>
				</table>
			</div>
			{pages > 1 ? (
				<div className="table__pagination">
					{range.map((item, index) => (
						<div
							key={index}
							className={`table__pagination-item ${currPage === index ? 'active' : ''}`}
							onClick={() => selectPage(index)}
						>
							{item + 1}
						</div>
					))}
				</div>
			) : null}
			<Profile user={user} show={profileShow} setShow={setProfileShow} />
		</div>
	);
};

export default Table;
