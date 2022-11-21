import React, { useEffect, useState } from 'react';
import { Card } from 'react-bootstrap';
import { useAuth } from '../components/store/useAuth';
import { AVATAR_API, PROFILE } from '../utils/constant';

const Profile = () => {
	const [user, setUser] = useState(null);
	const userAuth = useAuth((state) => state.user);

	useEffect(() => {
		(async () => {
			const response = await fetch(PROFILE, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${userAuth.token}`,
				},
			});
			const data = await response.json();
			if (data.status === 200) {
				setUser(data.account);
			}
		})();
	}, [userAuth.token]);

	console.log(user);

	return (
		<Card bg="light" className="max-w-[800px] mx-auto">
			<Card.Header>
				<h1>Profile</h1>
			</Card.Header>
			<Card.Body>
				<div className="row">
					<div className="col-4">
						<img
							src={AVATAR_API('S1mple')}
							alt=""
							className="object-cover w-full h-full rounded-lg"
						/>
					</div>
					<div className="col-8">
						<div className="div">
							<h4>Fullname</h4>
							<p>{user?.fullName}</p>
						</div>
						<div className="div">
							<h4>Date of Birth</h4>
							<p>{new Date(user?.dob).toLocaleDateString() || ''}</p>
						</div>
						<div className="div">
							<h4>Address</h4>
							<p>{user?.address}</p>
						</div>
						<div className="div">
							<h4>Phone</h4>
							<p>{user?.phone}</p>
						</div>
						<div className="div">
							<h4>Email</h4>
							<p>
								<a href={`mailto:${user?.email}`}>{user?.email}</a>
							</p>
						</div>
					</div>
				</div>
			</Card.Body>
		</Card>
	);
};

export default Profile;
