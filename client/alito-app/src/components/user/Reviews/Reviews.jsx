import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import "./Reviews.css"
import Card from "../../Cards/Card/Card";
import {useNavigate, useParams} from "react-router-dom";
import deleteImage from "../../../image/delete.svg";

const Reviews = ({setIsLogIn}) => {

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
    },[id])

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

    const navigate = useNavigate();
    const handleNavigateProfile = (id) => {
        navigate("/profile/" + id + '/ads')
    }

    const isAdmin = () => {
        const admin = JSON.parse(localStorage.getItem("userInfo")).is_admin
        if (admin) {
            return true
        } else return false
    }

    const showConfirmToDelete = (id) => {
        const con = window.confirm("Удалить отзыв с id = " + (id) + "?")
        if (con) {
            fetch(`http://localhost:8080/admin/delete-review`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({review_id: id, admin_id: JSON.parse(localStorage.getItem("userInfo")).id}),
            })
                .then((res) => {
                    if (res.status == 204)
                        navigate(`/admin`)
                })
        }
    }

    return (
        <>
            <UserInfo setIsLogIn={setIsLogIn}/>
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
                                <h4 onClick={()=>{
                                    if(section==="received")
                                        handleNavigateProfile(review.from_user)
                                    else handleNavigateProfile(review.to_user)
                                }}>{review.name} {review.surname}</h4>
                                {isAdmin() &&
                                    <button className={"recovery-button-ad"} onClick={()=>showConfirmToDelete(review.id)}>
                                        <img className={"recoveryImage"} src={deleteImage}/>
                                    </button>}
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