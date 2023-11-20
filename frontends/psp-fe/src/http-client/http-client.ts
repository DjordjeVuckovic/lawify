import axios from 'axios';
const BASE_URL = import.meta.env.VITE_BE_BASE_URL
export const httpClient = axios.create({
    baseURL: BASE_URL,
});
