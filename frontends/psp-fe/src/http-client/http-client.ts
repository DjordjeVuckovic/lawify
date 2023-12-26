import axios from 'axios';
import {addInterceptor} from "../auth/auth.interceptor.ts";
const BASE_URL = import.meta.env.VITE_BE_BASE_URL
const PCC_BASE_URL = import.meta.env.VITE_PCC_BASE_URL
export const httpClient = axios.create({
    baseURL: BASE_URL,
});
httpClient.interceptors.request.use(addInterceptor);

export const pccHttpClient = axios.create({
    baseURL: PCC_BASE_URL,
});