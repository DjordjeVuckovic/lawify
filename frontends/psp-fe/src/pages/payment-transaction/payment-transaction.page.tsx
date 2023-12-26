import {useSearchParams} from "react-router-dom";
import {PaymentOptions} from "../payment-options/payment-options.tsx";
import {useMutation, useQuery} from "@tanstack/react-query";
import {fetchTransaction, generateQrCode, processTransaction} from "../../shared/services/transaction.service.ts";
import {AxiosError} from "axios";
import {TransactionResponse} from "../../shared/model/common/transaction.model.ts";
import {BankModal} from "./ui/bank-modal/bank-modal.tsx";
import {useState} from "react";
import {toastError, toastSuccess} from "../../shared/toast/toast.ts";
import {handleError} from "../../utils/handle-error.ts";
import {downloadFile} from "../../utils/download-file.ts";

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
    const transactionQuery = useQuery({
        queryKey: ['transaction', transactionId],
        queryFn: () => fetchTransaction(transactionId),
        enabled: !!transactionId,
    });
    const mutation = useMutation({
        mutationFn: processTransaction,
        onError: (error: AxiosError) => {
            console.log(error)
            toastError(handleError(error))
        },
        onSuccess: (response: TransactionResponse) => {
            console.log("success")
            console.log(response)
            toastSuccess('You are successfully process transaction!')
            if (response.bankService) {
                openModal(response)
                return
            }
            window.location.replace(response.redirectUrl);

        }
    });
    const qrCodeMutation = useMutation({
        mutationFn: generateQrCode,
        onError: (error: AxiosError) => {
            console.log(error)
            toastError(handleError(error))
        },
        onSuccess: (blob: Blob) => {
            downloadFile('bank-qr-code.png', blob)
            toastSuccess('You are successfully generated qr code transaction!')

        }
    });
    if (transactionQuery.isLoading) return <div>Loading...</div>;
    if (transactionQuery.isError) return <div>Error: {transactionQuery.error?.message}</div>;
    const processPaymentTransaction = (subscriptionId: string, serviceName: string) => {
        console.log('processPaymentTransaction')
        if (serviceName === 'QrCode') {
            qrCodeMutation.mutate({
                    receiverAccountNumber: transactionQuery.data.backAccount,
                    paymentAmount: transactionQuery.data!.transaction.amount
                }
            )
            return;
        }
        mutation.mutate({transactionId: transactionQuery.data!.transaction.id, subscriptionId: subscriptionId})
    }
    return (
        <>
            <PaymentOptions paymentOptions={transactionQuery.data!.availableServices}
                            amount={transactionQuery.data!.transaction.amount}
                            onPayment={(paymentId, serviceName) => processPaymentTransaction(paymentId, serviceName)}
            />
            <BankModal isOpen={modalIsOpen} onClose={closeModal} iframeUrl={iframeUrl}/>
            <div
                className={'flex-center'}>{mutation.isSuccess && mutation.data.bankService ? JSON.stringify(mutation.data) : ''}</div>
        </>
    );
};
