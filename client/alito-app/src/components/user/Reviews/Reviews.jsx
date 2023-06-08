import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import "./Reviews.css"
import Card from "../../Cards/Card/Card";
import {useParams} from "react-router-dom";

const Reviews = () => {

    const {id} = useParams();

    const [receivedReviews, setReceivedReviews] = useState([])
    const [sentReviews, setSentReviews] = useState([])

    useEffect(()=>{
        fetch("http://localhost:8080/get-received-reviews", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({id: id})
        })
            .then(res=>res.json())
            .then(res=>setReceivedReviews(res))

        fetch("http://localhost:8080/get-sent-reviews", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({id: id})
        })
            .then(res=>res.json())
            .then(res=>setSentReviews(res))
    },[])

    const [section, setSection] = useState("received");
    const styleForSection = {borderBottom: "3px solid black"}
    const pageSections = [
        {
            text:"Полученные",
            key:"received",
            reviews: receivedReviews
        },
        {
            text:"Отправленные",
            key:"sent",
            reviews: sentReviews

        }
    ];

    return (
        <>
            <UserInfo />
            <div className={"container"}>
                <div className={"title-page"}>
                    <h1>Отзывы</h1>
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
                <div className={"reviews-container"}>
                    {pageSections.find((sec) => sec.key === section)?.reviews.map((review) => (
                        <div className={"review-container"}>
                            <div className={"from"}>
                                <h4>{review.name} {review.surname}</h4>
                            </div>
                            <div className={"date"}>
                                <p>{review.date_posted}</p>
                            </div>
                            <div className={"rating"}>{'☆'.repeat(review.rating)}</div>
                            <div className={"ads-title"}>
                                <p>"{review.title}"</p>
                            </div>
                            <div className={"comment"}>
                                <h4>Комментарий</h4>
                                <p>{review.comment}</p>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </>
    )
}
export default Reviews