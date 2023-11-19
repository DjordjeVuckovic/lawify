import {Link} from "react-router-dom";
import "./nav-link.scss"

export const NavLink = ({title,url}: {title: string,url: string}) => {
    return (
        <Link className={'link'} to={url}>{title}</Link>
    );
};
