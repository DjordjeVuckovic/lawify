import {IoAddCircleOutline} from "react-icons/io5";
import './user-payment-providers.page.scss'
import {useNavigate} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {fetchSubscriptionsByUser} from "../../shared/services/subscription.service.ts";
import {PaymentProvidersOptions} from "../payment-options/payment-providers-options.tsx";
import {useUserPayload} from "../../auth/user-hook.ts";
import {OptionsStatus} from "../payment-options/options-status.tsx";

export const UserPaymentProvidersPage = () => {
    const navigate = useNavigate()
    const user = useUserPayload()
    const paymentOptionsQuery = useQuery({
        queryKey: ['paymentOptions/user'],
        enabled: !!user,
        queryFn: () => fetchSubscriptionsByUser(user.id),
    });
    if (paymentOptionsQuery.isLoading) return <div>Loading...</div>;
    return (
        <div className={'padding-base inner-width payment-providers-container'}>
            <div className={'flex-space'}>
                <h1 className={'main-color-text-h1'}>Your payment providers</h1>
                <button onClick={() => navigate('/subscriptions/new-payment')} type="button"
                        className="payment-providers-btn">
                    Subscribe to new
                    <span className={'button-icon'}>
                            <IoAddCircleOutline size={24}/>
                        </span>
                </button>
            </div>
            {(paymentOptionsQuery.isError || paymentOptionsQuery.data?.length === 0) ?
                <OptionsStatus message={'No payment providers found'} isError={true}/> :
                <PaymentProvidersOptions paymentOptions={paymentOptionsQuery.data ?? []}
                                         onSelection={(data) => console.log(data)}/>
            }
        </div>
    );
};
