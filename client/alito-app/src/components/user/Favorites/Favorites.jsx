import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import "./Favorites.css"
import Card from "../../Cards/Card/Card";

const Favorites=()=>{

    const [ads, setAds] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/get-favorite-advertisements", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({id: JSON.parse(localStorage.getItem("userInfo")).id})
        })
            .then(res => res.json())
            .then(res => setAds(res))
    }, [])

    return(
        <>
            <UserInfo/>
            <div className={"container"}>
                <div className={"page-name"}>
                    <h1>Избранное</h1>
                </div>
                <div className={"ads-container"}>
                    {ads.map((ad) => (
                        <Card key={ad.id} ad={ad}/>
                    ))}
                </div>
            </div>
        </>
    )
}
export default Favorites