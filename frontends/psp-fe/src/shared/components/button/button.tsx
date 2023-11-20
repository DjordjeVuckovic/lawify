import "./button.scss"
import {ReactNode} from "react";
export interface ButtonProps {
    disabled?: boolean
    children: ReactNode,
    onClick?: () => void;
}
export const Button = ({disabled = false,onClick,children} : ButtonProps) => {
    return (
        <>
        <button className="button" onClick={onClick} disabled={disabled}>{children}</button>
        </>
    );
};
