import "./button.scss"
import {ReactNode} from "react";
export interface ButtonProps {
    children: ReactNode,
    onClick?: () => void;
}
export const Button = ({onClick,children} : ButtonProps) => {
    return (
        <>
        <button className="button" onClick={onClick}>{children}</button>
        </>
    );
};
