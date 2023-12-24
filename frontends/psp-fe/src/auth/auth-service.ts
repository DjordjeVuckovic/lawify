import {httpClient} from "../http-client/http-client.ts";
import {AuthResponse, SignInRequest, SignUpRequest} from "./auth.model.ts";
const BASE_URL = import.meta.env.VITE_BE_BASE_URL
export const signIn = async (data: SignInRequest): Promise<AuthResponse> => {
    const response = await httpClient.post(`${BASE_URL}/psp-mediator-service/api/v1/auth`, data);
    return response.data;
};

export const signUp = async (data: SignUpRequest) => {
    const response = await httpClient.post(`${BASE_URL}/psp-mediator-service/api/v1/auth/register`, data);
    return response.data;
};