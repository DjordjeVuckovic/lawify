import './App.scss'
import {Navbar} from "./shared/components/navbar";
import {Body} from "./shared/components/body";
import {Route, Routes} from "react-router-dom";
import {PaymentTransactionPage} from "./pages/payment-transaction/payment-transaction.page.tsx";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import Modal from 'react-modal';
import {SignInPage} from "./pages/sign-in-up/sign-in.page.tsx";
import {Toaster} from "react-hot-toast";
import {HomePage} from "./pages/home/home.page.tsx";
import {SignUpPage} from "./pages/sign-in-up/sign-up.page.tsx";
import {CapturePayment} from "./pages/capture-payment/capture-payment.tsx";
import {UserPaymentProvidersPage} from "./pages/user-payment-providers/user-payment-providers.page.tsx";
import {PaymentFail} from "./pages/payment-result/payment-fail.tsx";
import {PaymentSuccess} from "./pages/payment-result/payment-success.tsx";
import {SubscribeToNewProviders} from "./pages/user-payment-providers/subscribe-to-new-providers.tsx";

Modal.setAppElement('#root');

const queryClient = new QueryClient();

function App() {

    return (
        <>
            <QueryClientProvider client={queryClient}>
                <Navbar/>
                <Body>
                    <Routes>
                        <Route path='/payments' element={<PaymentTransactionPage/>}/>
                        <Route path='/sign-in' element={<SignInPage/>}/>
                        <Route path='/sign-up' element={<SignUpPage/>}/>
                        <Route path='/' element={<HomePage/>}/>
                        <Route path='/subscriptions/new-payment' element={<SubscribeToNewProviders/>}/>
                        <Route path='/payment-providers' element={<UserPaymentProvidersPage/>}/>
                        <Route path='/capture' element={<CapturePayment/>}/>
                        <Route path='/subscription-payment-failed' element={<PaymentFail/>}/>
                        <Route path='/subscription-payment-success' element={<PaymentSuccess/>}/>
                    </Routes>
                </Body>
                <Toaster position="bottom-center"
                         reverseOrder={false}
                />
            </QueryClientProvider>
        </>
    )
}

export default App
