import create from 'zustand';

export const useNotify = create((set) => {
	return {
		open: false,
		content: '',
		type: 'success',
		setOpen: () => set((state) => ({ ...state, open: true })),
		setClose: () => set((state) => ({ ...state, open: false })),
		setContent: (content) => set((state) => ({ ...state, content })),
		setType: (type) => set((state) => ({ ...state, type })),
	};
});
