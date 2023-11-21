import {useSearchParams} from "react-router-dom";
import {PaymentOptions} from "../payment-options/payment-options.tsx";
import {useMutation, useQuery} from "@tanstack/react-query";
import {fetchTransaction, processTransaction} from "../../shared/services/transaction.service.ts";
import {fetchSubscriptions} from "../../shared/services/subscription.service.ts";
import {AxiosError} from "axios";
import {TransactionResponse} from "../../shared/model/common/transaction.model.ts";
import {BankModal} from "./ui/bank-modal/bank-modal.tsx";
import {useState} from "react";

export const PaymentTransactionPage = () => {
    const [modalIsOpen, setModalIsOpen] = useState(false)
    const [iframeUrl, setIframeUrl] = useState('');
    const openModal = (response: TransactionResponse) => {
        setModalIsOpen(true);
        setIframeUrl(response.redirectUrl)
    }
    const closeModal = () => {
        setModalIsOpen(false);
    };
    const [searchParams] = useSearchParams();
    const transactionId = searchParams.get('transaction');
    console.log(transactionId)
    const transactionQuery = useQuery({
        queryKey: ['transaction', transactionId],
        queryFn: () => fetchTransaction(transactionId),
        enabled: !!transactionId,
    });

    const paymentOptionsQuery = useQuery({
        queryKey: ['paymentOptions'],
        queryFn: fetchSubscriptions,
    });
    const mutation = useMutation({
        mutationFn: processTransaction,
        onError: (error: AxiosError) => {
            console.log(error)
        },
        onSuccess: (response: TransactionResponse) => {
            console.log("success")
            console.log(response)
            openModal(response)
        }
    });
    if (transactionQuery.isLoading || paymentOptionsQuery.isLoading) return <div>Loading...</div>;
    if (transactionQuery.isError || paymentOptionsQuery.isError) return <div>Error: {transactionQuery.error?.message}</div>;
    const processPaymentTransaction = (subscriptionId: string) => {
        console.log('processPaymentTransaction')
        mutation.mutate({transactionId: transactionQuery.data!.id, subscriptionId: subscriptionId})
    }
    return (
        <>
            <PaymentOptions paymentOptions={paymentOptionsQuery.data!}
                            amount={transactionQuery.data!.amount}
                            onPayment={(paymentId) => processPaymentTransaction(paymentId)}
            />
            <BankModal isOpen={modalIsOpen} onClose={closeModal} iframeUrl={iframeUrl}/>
            <div
                className={'flex-center'}>{mutation.isSuccess && mutation.data.bankService ? JSON.stringify(mutation.data) : ''}</div>
        </>
    );
};
