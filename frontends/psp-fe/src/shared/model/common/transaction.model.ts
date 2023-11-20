export interface TransactionModel {
    id: string;
    orderId: string;
    timeStamp: Date;
    merchantId: string;
    amount: number;
    status: string;
}