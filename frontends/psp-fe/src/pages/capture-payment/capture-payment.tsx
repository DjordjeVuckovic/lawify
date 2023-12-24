import {useSearchParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import {completeOrder} from "../../shared/services/complete-order.service.ts";

export const CapturePayment = () => {
    const [searchParams] = useSearchParams();
    const token = searchParams.get('token');
    console.log(token)
    const {data, isLoading} = useQuery({
        queryKey: ['token', token],
        queryFn: () => completeOrder(token),
        enabled: !!token,
    });
    if(isLoading){
        return <div>Loading..</div>
    }
    return (
        <>
            <div>Capture payment</div>
            <div>{JSON.stringify(data)}</div>
        </>

    );
};
