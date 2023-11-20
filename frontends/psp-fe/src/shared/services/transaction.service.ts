import {httpClient} from "../../http-client/http-client.ts";
import {TransactionModel, TransactionRequest, TransactionResponse} from "../model/common/transaction.model.ts";

export const fetchTransaction = async (transactionId: string | null): Promise<TransactionModel> => {
    if(!transactionId){
        return Promise.reject();
    }
    const response =
        await httpClient.get(`/psp-mediator-service/api/v1/transactions/${transactionId}`);
    return response.data;
}

export const processTransaction = async (request: TransactionRequest): Promise<TransactionResponse> => {
    const response =
        await httpClient.post(`/psp-mediator-service/api/v1/transactions/payment`,request);
    return response.data;
}