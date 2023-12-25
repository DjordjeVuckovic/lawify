import {useEffect} from "react";

export const PaypalButton = () => {
    useEffect(() => {
        const script = document.createElement('script');
        script.src = "https://www.paypal.com/sdk/js?client-id=Aa1u29AHYoRdBI98eBNq5n_Z9ynTykQyIWQ_XMNMSZW2EAI8HUpEgMVLAGcEGrQRvkp-ohPJFOjkE-9P&vault=true&intent=subscription";
        script.addEventListener('load', () => {
            // Ensure the global `paypal` object exists
            if (window.paypal) {
                window.paypal.Buttons({
                    style: {
                        shape: 'pill',
                        color: 'gold',
                        layout: 'vertical',
                        label: 'paypal'
                    },
                    createSubscription: function(data, actions) {
                        return actions.subscription.create({
                            plan_id: 'P-5PG1946263622900VMWE6GJA'
                        });
                    },
                    onApprove: function(data, actions) {
                        window.location.href = 'http://localhost:4200';
                        history.pushState(null, null, window.location.href);
                        window.onpopstate = function () {
                            // When back is pressed, replace the current URL with the desired URL again
                            window.location.href = 'http://localhost:4200';
                        };
                    }
                }).render('#paypal-button-container-P-5PG1946263622900VMWE6GJA');
            }
        });
        document.body.appendChild(script);

        // Clean up the script when the component unmounts
        return () => {
            document.body.removeChild(script);
        };
    }, []);
    return (
        <>
            <div id="paypal-button-container-P-5PG1946263622900VMWE6GJA"></div>
        </>
    );
};
