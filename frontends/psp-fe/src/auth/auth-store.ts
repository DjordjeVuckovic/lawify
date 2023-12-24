import { create } from 'zustand';
import {createJSONStorage, persist} from 'zustand/middleware';

interface AuthState {
    accessToken: string | null;
    setAccessToken: (token: string | null) => void;
}

const useAuthStore = create<AuthState>()(
    persist(
        (set) => ({
            accessToken: null,
            setAccessToken: (token) => set({ accessToken: token })
        }),
        {
            name: 'auth-storage', // Unique name for the storage key
            storage: createJSONStorage(() => sessionStorage)
        }
    )
);

export default useAuthStore;