import Modal from 'react-modal';
import {Button} from "../../../../shared/components/button/button.tsx";
// @ts-ignore
export const BankModal = ({ isOpen, onClose, iframeUrl }) => {
    const customStyles = {
        overlay: {
            backgroundColor: 'rgba(0, 0, 0, 0.75)',
            zIndex: 1000
        },
        content: {
            background: 'rgba(22, 27, 32, 1)',
            color: '#fff',
            border: '1px solid #ccc',
            borderRadius: '4px',
            padding: '20px'
        }
    };

    return (
        <Modal isOpen={isOpen} onRequestClose={onClose} contentLabel="External Content" style={customStyles}>
            <div className={'flex-col-start g-5'}>
                <Button onClick={onClose}>Close</Button>
                <iframe className={'justify-self'} src={iframeUrl} width="50%" height="600px" title="Bank payment"></iframe>
            </div>
        </Modal>
    );
};
