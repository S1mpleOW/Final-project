export const formatMoney = (money) => {
	const config = { style: 'currency', currency: 'VND', maximumFractionDigits: 9 };
	const formated = new Intl.NumberFormat('vi-VN', config).format(money);
	return formated;
};

export const addYear = (current, year) => {
	return new Date(
		new Date(current).setFullYear(new Date(current).getFullYear() + year)
	).toLocaleDateString();
};
