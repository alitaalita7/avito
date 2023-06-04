import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import "./MyAdvertisements.css"
import Card from "../../Cards/Card/Card";

const MyAdvertisements = () => {

    const [ads, setAds] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/get-advertisements-by-user-id", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({id: JSON.parse(localStorage.getItem("userInfo")).id})
        })
            .then(res => res.json())
            .then(res => setAds(res))
    }, [])


    const [section, setSection] = useState("active");
    const styleForSection = {borderBottom: "3px solid black"}
    const pageSections = [
        {
            text:"Активные",
            key:"active"
        },
        {
            text:"Архив",
            key:"archive"
        }
    ];

    return (
        <>
            <UserInfo/>
            <div className={"container"}>
                <div className={"name-page"}>
                    <h1>Мои объявления</h1>
                </div>
                <div className={"page-section"}>
                    {pageSections.map((sec) => (
                        <h2 key={sec.text}
                            style={sec.key === section ? styleForSection : {}}
                            onClick={() => setSection(sec.key)}
                            className={"sections"}>
                            {sec.text}
                        </h2>)
                    )}
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
export default MyAdvertisements