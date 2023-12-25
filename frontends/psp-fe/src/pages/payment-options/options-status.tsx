import img from '../../assets/empty-options.png';
import imgDone from '../../assets/thumbs-up.png';
export const OptionsStatus = ({message,isError}: {message: string,isError: boolean}) => {
    return (
        <div className={'padding-base g-1 flex-col-center inner-width'}>
            <h1 className={'main-color-text-h1 large-text'}>{message}</h1>
            <div className={'flex-col-center'}>
                <h3 className={'white'}>Payment providers filter is empty!</h3>
            </div>
            {isError ? <img src={img} alt={'i'}/> : <img src={imgDone} alt={'i'}/>}
        </div>
    );
};
