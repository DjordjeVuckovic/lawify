import './App.scss'
import {PaymentOptions} from "./pages/payment-options/payment-options.tsx";
import {Navbar} from "./shared/components/navbar";
import {Body} from "./shared/components/body";
import {Route, Routes} from "react-router-dom";

function App() {

  return (
    <>
        <Navbar/>
        <Body>
            <Routes>
                <Route path='/' element={<PaymentOptions/>} />
            </Routes>
        </Body>
    </>
  )
}

export default App
