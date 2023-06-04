import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import "./Reviews.css"
import Card from "../../Cards/Card/Card";

const Reviews = () => {

    const reviews = [
        {
            id: 1,
            from: "Alina Sharapova",
            to: "John Doe",
            date: "Май 10, 2023",
            rating: 5,
            ads_title: "Название объявления",
            text: "Хороший продавец, будем еще с ним сотрудничать, товары без брака."
        },
        {
            id: 2,
            from: "Alina Sharapova",
            to: "John Doe",
            date: "Май 10, 2023",
            rating: 2,
            ads_title: "Название объявления",
            text: "Хороший продавец, будем еще с ним сотрудничать, товары без брака."
        },
        {
            id:3,
            from:"Alina Sharapova",
            to:"John Doe",
            date:"Май 10, 2023",
            rating: 5,
            ads_title: "Название объявления",
            text: "Хороший продавец, будем еще с ним сотрудничать, товары без брака."
        },
        {
            id:4,
            from:"Alina Sharapova",
            to:"John Doe",
            date:"Май 10, 2023",
            rating: 4,
            ads_title: "Название объявления",
            text: "Хороший продавец, будем еще с ним сотрудничать, товары без брака."
        }]

    // const [ads, setAds] = useState([]);
    //
    // useEffect(() => {
    //     fetch("http://localhost:8080/get-all-advertisements", {})
    //         .then(res=>res.json())
    //         .then(res=>setAds(res))
    // }, [])
    const [section, setSection] = useState("received");
    const styleForSection = {borderBottom: "3px solid black"}
    const pageSections = [
        {
            text:"Полученные",
            key:"received"
        },
        {
            text:"Отправленные",
            key:"sent"
        }
    ];

    return (
        <>
            <UserInfo/>
            <div className={"container"}>
                <div className={"title-page"}>
                    <h1>Отзывы</h1>
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
                <div className={"reviews-container"}>
                    {reviews.map((review) => (
                        <div className={"review-container"}>
                            <div className={"from"}>
                                <h4>{review.from}</h4>
                            </div>
                            <div className={"date"}>
                                <p>{review.date}</p>
                            </div>
                            <div className={"rating"}>
                                {'☆'.repeat(review.rating)}
                            </div>
                            <div className={"ads-title"}>
                                <p>"{review.ads_title}"</p>
                            </div>
                            <div className={"comment"}>
                                <h4>Комментарий</h4>
                                <p>{review.text}</p>
                            </div>
                        </div>
                    ))}
                    {/*{ads.map((ad) => (*/}
                    {/*    <Card key={ad.id} ad={ad}/>*/}
                    {/*))}*/}
                </div>
            </div>
        </>
    )
}
export default Reviews