import {useSearchParams} from "react-router-dom";
import {PaymentOptions} from "../payment-options/payment-options.tsx";
import {useQuery} from "@tanstack/react-query";
import {fetchTransaction} from "../../shared/services/transaction.service.ts";

export const PaymentTransactionPage = () => {
    const [searchParams] = useSearchParams();
    const transactionId = searchParams.get('transaction');
    console.log(transactionId)
    const { data, isLoading, error, isError } =  useQuery({
        queryKey: ['transaction', transactionId],
        queryFn: () => fetchTransaction(transactionId),
        enabled: !!transactionId,
    });
    if (isLoading) return <div>Loading...</div>;
    if (isError) return <div>Error: {error?.message}</div>;
    console.log(data)
    return (
        <>
            <PaymentOptions/>
        </>
    );
};
