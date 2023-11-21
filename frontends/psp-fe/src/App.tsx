import './App.scss'
import {Navbar} from "./shared/components/navbar";
import {Body} from "./shared/components/body";
import {Route, Routes} from "react-router-dom";
import {PaymentTransactionPage} from "./pages/payment-transaction/payment-transaction.page.tsx";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import Modal from 'react-modal';

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
                    </Routes>
                </Body>
            </QueryClientProvider>
        </>
    )
}

export default App
