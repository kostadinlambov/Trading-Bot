import React from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck, faExclamationTriangle, faInfoCircle } from "@fortawesome/free-solid-svg-icons";

 const ToastComponent = {
    SuccessToast: ({ text, closeToast }) => {
        return (
            <div className="d-flex justify-content-start align-items-center" >
                <div style={{ 'fontSize': '25px' }}>
                    <FontAwesomeIcon icon={faCheck} />
                </div>
                <div style={{ 'text-align': 'left', 'margin-left': '1rem' }}>
                    <div>{text}</div>
                </div>
            </div>
        )
    },

    ErrorToast: ({ text, closeToast }) => {
        return (
            <div className="d-flex justify-content-start align-items-center">
                <div style={{ 'fontSize': '25px' }}>
                    {/* <i className="fas fa-exclamation-triangle"></i> */}
                    <FontAwesomeIcon icon={faExclamationTriangle} />
                </div>
                <div style={{ 'text-align': 'left', 'margin-left': '1rem' }}>
                    <div className="font-weight-bold">Error!</div>
                    <div>{text}</div>
                </div>
            </div>
        )
    },

    InfoToast: ({ text, closeToast }) => {
        return (
            <div className="d-flex justify-content-start align-items-center">
                <div style={{ 'fontSize': '25px' }}>
                    {/* <i className="fas fa-info-circle"></i> */}
                    <FontAwesomeIcon icon={faInfoCircle} />
                </div>
                <div style={{ 'text-align': 'left', 'margin-left': '1rem' }}>
                    <div>{text}</div>
                </div>
            </div>
        )
    }
};


export default ToastComponent;


