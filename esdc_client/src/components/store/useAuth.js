import create from 'zustand';

export const useAuth = create((set) => {
	return {
		user: sessionStorage.getItem('user') ? JSON.parse(sessionStorage.getItem('user')) : null,
		setUser: (user) => set({ user }),
	};
});
