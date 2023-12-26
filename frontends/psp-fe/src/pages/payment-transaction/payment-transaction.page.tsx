import {useSearchParams} from "react-router-dom";
import {PaymentOptions} from "../payment-options/payment-options.tsx";
import {useMutation, useQuery} from "@tanstack/react-query";
import {fetchTransaction, processTransaction} from "../../shared/services/transaction.service.ts";
import {AxiosError} from "axios";
import {TransactionResponse} from "../../shared/model/common/transaction.model.ts";
import {BankModal} from "./ui/bank-modal/bank-modal.tsx";
import {useEffect, useState} from "react";
import useAuthStore from "../../auth/auth-store.ts";

export const PaymentTransactionPage = () => {
    const [modalIsOpen, setModalIsOpen] = useState(false)
    const [iframeUrl, setIframeUrl] = useState('');
    const {setAccessToken} = useAuthStore()
    const openModal = (response: TransactionResponse) => {
        setModalIsOpen(true);
        setIframeUrl(response.redirectUrl)
    }
    const closeModal = () => {
        setModalIsOpen(false);
    };
    const [searchParams] = useSearchParams();
    const transactionId = searchParams.get('transaction');
    const transactionQuery = useQuery({
        queryKey: ['transaction', transactionId],
        queryFn: () => fetchTransaction(transactionId),
        enabled: !!transactionId,
    });
    const mutation = useMutation({
        mutationFn: processTransaction,
        onError: (error: AxiosError) => {
            console.log(error)
        },
        onSuccess: (response: TransactionResponse) => {
            console.log("success")
            console.log(response)
            if (response.bankService) {
                openModal(response)
                return
            }
            window.location.replace(response.redirectUrl);

        }
    });
    useEffect(() => {
            if (transactionQuery.isSuccess && transactionQuery.data?.token) {
                setAccessToken(transactionQuery.data.token)
            }
        }, [transactionQuery.isSuccess, transactionQuery.data?.token]
    )
    if (transactionQuery.isLoading) return <div>Loading...</div>;
    if (transactionQuery.isError) return <div>Error: {transactionQuery.error?.message}</div>;
    const processPaymentTransaction = (subscriptionId: string) => {
        console.log('processPaymentTransaction')
        mutation.mutate({transactionId: transactionQuery.data!.transaction.id, subscriptionId: subscriptionId})
    }
    return (
        <>
            <PaymentOptions paymentOptions={transactionQuery.data!.availableServices}
                            amount={transactionQuery.data!.transaction.amount}
                            onPayment={(paymentId) => processPaymentTransaction(paymentId)}
            />
            <BankModal isOpen={modalIsOpen} onClose={closeModal} iframeUrl={iframeUrl}/>
            <div
                className={'flex-center'}>{mutation.isSuccess && mutation.data.bankService ? JSON.stringify(mutation.data) : ''}</div>
        </>
    );
};
