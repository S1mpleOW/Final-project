import React from 'react';
import { Button, Modal } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { AVATAR_API } from '../../utils/constant';
import Loading from '../loading/Loading';
const Profile = ({ show, setShow, user }) => {
	return (
		<Modal show={show} onHide={() => setShow(false)} animation={true}>
			<Modal.Header style={{ display: 'unset' }}>
				<Modal.Title>Profile</Modal.Title>
				{!user ? (
					<div className="flex justify-center">
						<Loading></Loading>
					</div>
				) : (
					<Modal.Body style={{ paddingLeft: 'unset' }}>
						<div className="flex">
							<div className="flex-shrink-0 max-w-[150px] max-h-[150px] rounded-xl overflow-hidden">
								<img
									src={AVATAR_API(user?.account.fullName)}
									className="object-cover w-full h-full"
									alt=""
								/>
							</div>
							<div className="flex-grow ml-4 ">
								<h2 className="text-2xl font-bold">{user?.account.fullName}</h2>
								<p className="flex items-center text-base text-gray-500">
									<span className="text-gray-900">
										<i className="bx bx-child"></i>
									</span>
									<span className="ml-2">{user?.account.sex}</span>
								</p>
								<p className="flex items-center text-base text-gray-500">
									<span className="text-gray-900">
										<i className="bx bxs-calendar"></i>
									</span>
									<span className="ml-2">{user?.account.dob}</span>
								</p>
								<p className="flex items-center text-base text-gray-500">
									<span className="text-gray-900">
										<i className="bx bx-phone-call"></i>
									</span>
									<span className="ml-2">{user?.account.phone}</span>
								</p>
								<p className="flex items-center text-base text-gray-500">
									<span className="text-gray-900">
										<i className="bx bx-envelope"></i>
									</span>
									<span className="ml-2">
										<Link to={`mailto:${user?.account.email}`} target="_blank">
											{user?.account.email}
										</Link>
									</span>
								</p>
								<p className="flex items-center text-base text-gray-500">
									<span className="text-gray-900">
										<i className="bx bx-map"></i>
									</span>
									<span className="ml-2">{user?.account.address}</span>
								</p>
								<p className="flex items-center text-base text-gray-500">
									<span className="text-gray-900">
										<i className="bx bx-wallet"></i>
									</span>
									<span className="ml-2">
										{user?.rewardPoint >= 0 ? user.rewardPoint : 0 || user.salary}
									</span>
								</p>
							</div>
						</div>
					</Modal.Body>
				)}
				<Modal.Footer>
					<Button variant="secondary" onClick={() => setShow(false)}>
						Close
					</Button>
				</Modal.Footer>
			</Modal.Header>
		</Modal>
	);
};

export default Profile;
