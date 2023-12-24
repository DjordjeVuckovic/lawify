import useAuthStore from "./auth-store.ts";
import {AxiosRequestHeaders, InternalAxiosRequestConfig} from "axios";
const BASE_URL = import.meta.env.VITE_BE_BASE_URL
const excludedPaths: string[] = [`${BASE_URL}/psp-mediator-service/api/v1/auth`];

export const addInterceptor = (config: InternalAxiosRequestConfig) : InternalAxiosRequestConfig => {
    const accessToken = useAuthStore.getState().accessToken
    if (excludedPaths.includes(config.url)) {
        return config;
    }

    if (accessToken) {
        const headers = {
            ...config.headers,
            Authorization: `Bearer ${accessToken}`,
        };
        config.headers = headers as AxiosRequestHeaders
        return config;
    }

    return config;
};