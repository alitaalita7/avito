import React, {useEffect, useState} from "react";
import Card from "../Card/Card";
import './Advertisements.css'
const Advertisements = ({ads}) => {
    return (
        <div className="ad-container">
            {ads.map((ad) => (<Card key={ad.id}
                                    ad={ad}
                                    />))}
        </div>
    )
}
export default Advertisements;
