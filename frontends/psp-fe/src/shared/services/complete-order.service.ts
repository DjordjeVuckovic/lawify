import {httpClient} from "../../http-client/http-client.ts";
import {CompleteOrderRequest} from "../model/common/complete-order-request.ts";

export const completeOrder = async (token: string | null)=>{
    if(!token)
        return Promise.reject();
    const request : CompleteOrderRequest={
        token: token
    }
    const response = await httpClient.post(`/psp-mediator-service/api/v1/transactions/complete-transaction`,request);
    return response.data;
}