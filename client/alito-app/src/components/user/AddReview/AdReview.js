import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import Card from "../../Cards/Card/Card";
import {useParams} from "react-router-dom";

const AdReview = () => {

    const {id} = useParams()
    const [adsActive, setAdsActive] = useState([]);

    useEffect(()=>{
        fetch("http://localhost:8080/get-advertisements-by-user-id-active", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({id: id})
        })
            .then(res => res.json())
            .then(res => setAdsActive(res))
    }, [])
    // const getUserAdvertisements = () =>{
    //     fetch("http://localhost:8080/get-advertisements-by-user-id-active", {
    //         method: "POST",
    //         headers: { 'Content-Type': 'application/json' },
    //         body: JSON.stringify({id: id})
    //     })
    //         .then(res => res.json())
    //         .then(res => setAdsActive(res))
    // }

    return(
        <>
            <UserInfo/>
            <div className={"container"}>
                <div className={"page-name"}>
                    <h1>Новый отзыв</h1>
                </div>
                <div className={"ad-review-container"}>
                    <select  required>
                        <option value="">-- Выберите объявление --</option>
                        {adsActive.map((ad) => (
                            <option key={ad.id} value={ad.title}>{ad.title}</option>
                        ))}
                    </select>
                </div>
            </div>
        </>
    )
}

export default AdReview