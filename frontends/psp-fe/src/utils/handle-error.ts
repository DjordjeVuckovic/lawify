import {AxiosError} from "axios";
export interface HandleError{
    httpStatus: string;
    message: string;
    timeStamp: number;
}
export function handleError(err: AxiosError): string | null {
    if(err.response.status === 500){
        return "Server error please contact our support!"
    }
    const errorData = err.response.data as HandleError
    return errorData.message
}