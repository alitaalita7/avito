import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import "./UserAdvertisements.css"
import Card from "../../Cards/Card/Card";
import {useParams} from "react-router-dom";

const UserAdvertisements = () => {

    const {id} = useParams()
    const [adsActive, setAdsActive] = useState([]);
    const [adsArchive, setAdsArchive] = useState([]);
    const [section, setSection] = useState("active");

    useEffect(() => {
        fetch("http://localhost:8080/get-advertisements-by-user-id-active", {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({user_id: id})
        })
            .then(res => res.json())
            .then(res => setAdsActive(res))

        fetch("http://localhost:8080/get-advertisements-by-user-id-archive", {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({user_id: id})
        })
            .then(res => res.json())
            .then(res => setAdsArchive(res))
    }, [id, section])

    useEffect(() => {
        if (id != JSON.parse(localStorage.getItem("userInfo")).id) {
            const filtred = pageSections.filter((el) => el.key !== "archive")
            setPageSections(filtred)
        } else {
            setPageSections([
                {
                    text: "Активные",
                    key: "active",
                    // ads: adsActive // Массив активных объявлений
                },
                {
                    text: "Архив",
                    key: "archive",
                    // ads: adsArchive // Массив архивных объявлений
                }
            ])
        }
    }, [id])


    const styleForSection = {borderBottom: "3px solid black"}
    const [pageSections, setPageSections] = useState([
        {
            text: "Активные",
            key: "active",
        },
        {
            text: "Архив",
            key: "archive",
        }
    ]);

    const showConfirmToRecovery = (id) => {
        const userId = JSON.parse(localStorage.getItem("userInfo")).id;
        const con = window.confirm("Вы действительно хотите восстановить это объявление? Его снова увидят другие пользователи.")
        if (con) {
            fetch(`http://localhost:8080/recovery-advertisement`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({ad_id: id}),
            })
                .then((res) => {
                    if (res.status == 204)
                        setSection("active")
                })
        }
    }

    return (
        <>
            <UserInfo/>
            <div className={"container"}>
                <div className={"name-page"}>
                    <h1>Объявления</h1>
                </div>
                <div className={"page-section"}>
                    {pageSections.map((sec) => (
                        <h2
                            key={sec.text}
                            style={sec.key === section ? styleForSection : {}}
                            onClick={() => setSection(sec.key)}
                            className={"sections"}
                        >
                            {sec.text}
                        </h2>
                    ))}
                </div>
                {section==="archive" &&
                    <p className={"text-archive"}>Другие пользователи не видят ваши архивные объявления</p>
                }
                <div className={"ads-container"}>
                    {section === "active" && (
                        adsActive.map((ad) => <Card key={ad.id} ad={ad}/>)
                    )}
                    {section === "archive" && (
                        adsArchive.map((ad) => <Card key={ad.id}
                                                     ad={ad}
                                                     showConfirmToRecovery={()=>showConfirmToRecovery(ad.id)}/>)
                    )}
                </div>
            </div>
        </>
    )
}
export default UserAdvertisements