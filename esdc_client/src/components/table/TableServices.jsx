import React from 'react';
import { useEffect } from 'react';
import { useState } from 'react';
import { formatMoney } from '../../utils/helper';
import ProfileButton from '../button/ProfileButton';
import UpdateButton from '../button/UpdateButton';
import Loading from '../loading/Loading';
import TooltipCustom from '../tooltip/TooltipCustom';

let pages = 1;

let range = [];
const TableServices = (props) => {
	const [dataShow, setDataShow] = useState(
		props.limit && props.bodyData ? props?.bodyData.slice(0, Number(props.limit)) : props?.bodyData
	);
	const [currPage, setCurrPage] = useState(0);

	if (props.limit !== undefined && props.bodyData !== undefined) {
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
									<Loading />
								</td>
							</tr>
						) : props.bodyData && props.bodyData.length > 0 ? (
							dataShow.map((item) => {
								return (
									<tr key={item?.id || item?.resourceId}>
										<td>{item?.id || item?.resourceId}</td>
										<td>{item.name}</td>
										<td>{formatMoney(item.priceSell)}</td>
										<td className="flex items-center gap-2">
											{/* <TooltipCustom content="Delete supplier" placement="top-end" arrow>
												<DeleteButton
													onClick={() => props.handleRemove(item?.id || item?.resourceId)}
												></DeleteButton>
											</TooltipCustom> */}
											<TooltipCustom content="Update service" placement="top" arrow>
												<UpdateButton
													onClick={() => props.handleUpdate(item?.id || item?.resourceId)}
												></UpdateButton>
											</TooltipCustom>
											<TooltipCustom content="Detail service" placement="top-start" arrow>
												<ProfileButton
													to={`/services/${item?.id || item?.resourceId}&${item?.name}`}
												></ProfileButton>
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
		</div>
	);
};

export default TableServices;
