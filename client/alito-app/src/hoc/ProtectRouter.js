import React from 'react';
import {Navigate} from "react-router-dom";

const ProtectRouter = ({isLogin, children, path="/login"}) => {
    if(isLogin){
        return children
    }
    return <Navigate to={path}/>
};

export default ProtectRouter;