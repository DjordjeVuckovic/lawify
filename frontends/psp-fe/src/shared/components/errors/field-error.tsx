import {MdOutlineErrorOutline} from "react-icons/md";

export const FieldError = ({error}) => {
	return (
		<div className={'flex-start g-04'}>
			<MdOutlineErrorOutline color={'#ff4040'} size={20}/>
			<span className={'warn'}>{error}</span>
		</div>
	)
}