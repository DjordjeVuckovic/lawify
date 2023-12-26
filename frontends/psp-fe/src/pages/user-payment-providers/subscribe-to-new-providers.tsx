import {useMutation, useQuery} from "@tanstack/react-query";
import {AxiosError} from "axios";
import {toastError, toastSuccess} from "../../shared/toast/toast.ts";
import {handleError} from "../../utils/handle-error.ts";
import {
    fetchAvailableSubscriptionsByUser,
    processNewSubscriptionPayment
} from "../../shared/services/subscription.service.ts";
import {PaymentOption, SubscriptionPaymentRequest} from "../../shared/model/common/payment-option.ts";
import {useUserPayload} from "../../auth/user-hook.ts";
import {Button} from "../../shared/components/button/button.tsx";
import {PaymentProvidersOptions} from "../payment-options/payment-providers-options.tsx";
import {OptionsStatus} from "../payment-options/options-status.tsx";
import {useState} from "react";
import {BiEuro} from "react-icons/bi";


export const SubscribeToNewProviders = () => {
    const [selectedPayments, setSelectedPayments] = useState<PaymentOption[]>([]);

    const user = useUserPayload()
    const paymentOptionsQuery = useQuery({
        queryKey: ['paymentOptions/user'],
        enabled: !!user,
        queryFn: () => fetchAvailableSubscriptionsByUser(user.id),
    });
    const mutation = useMutation({
        mutationFn: processNewSubscriptionPayment,
        onError: (error: AxiosError) => {
            console.log(error)
            toastError(handleError(error))
        },
        onSuccess: (data) => {
            console.log(data)
            if (data && data.url) {
                window.location.href = data.url;
            }
            toastSuccess('You are successfully subscribed to selected service!')
        }
    });
    const handleSubmit = async (e) => {
        e.preventDefault()
        const payment: SubscriptionPaymentRequest = {
            userId: user.id,
            subscriptionIds: selectedPayments.map(x => x.id)
        }
        mutation.mutate(payment)
    };
    if (paymentOptionsQuery.isLoading) return <div>Loading...</div>;
    return (
        <div className={'padding-base inner-width flex-col-center'}>
            {paymentOptionsQuery.data?.length === 0 ?
                <OptionsStatus message={'You are already subscribed to all providers!'} isError={false}/> :
                <PaymentProvidersOptions paymentOptions={paymentOptionsQuery.data ?? []}
                                         onSelection={(data) => setSelectedPayments(data)}/>
            }
            <form onSubmit={handleSubmit}>
                <Button>
                    Choose <BiEuro size={13}/>
                </Button>
            </form>
        </div>
    );
}