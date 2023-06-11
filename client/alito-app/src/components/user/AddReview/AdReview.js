import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import {useNavigate, useParams} from "react-router-dom";
import "./AdReview.css"

const AdReview = () => {

    const {id} = useParams()
    const navigate = useNavigate()

    const [adsActive, setAdsActive] = useState([]);

    const [reviewData, setReviewData] = useState({});

    const [advertisement_id, setAdValue] = useState('');
    const [rating, setRatingValue] = useState('');
    const [comment, setCommentValue] = useState('');
    const handleAdChange = (event) => {
        setAdValue(event.target.value)
    }
    const handleRatingChange = (event) => {
        setRatingValue(event.target.value)
    }
    const handleCommentChange = (event) => {
        setCommentValue(event.target.value)
    }

    useEffect(() => {
        fetch("http://localhost:8080/get-advertisements-by-user-id-active", {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({user_id: id})
        })
            .then(res => res.json())
            .then(res => setAdsActive(res))
    }, [id])

    const handleSubmit = async (event) => {
        event.preventDefault();
        // TODO: валидация собранных данных
        const data = {
            "from_user": JSON.parse(localStorage.getItem("userInfo")).id,
            "to_user": id,
            advertisement_id,
            rating,
            "date_posted": null,
            comment,
        };
        await fetch("http://localhost:8080/add-review", {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
        await navigate(`/profile/${id}/reviews`)
    };

    return (
        <>
            <UserInfo/>
            <div className={"container"}>
                <div className={"page-name"}>
                    <h1>Новый отзыв</h1>
                </div>
                <form onSubmit={handleSubmit} className={"ad-review-container"}>
                    <select value={advertisement_id} className={"ad-review-block"} onChange={handleAdChange} required>
                        <option value="">-- Выберите объявление --</option>
                        {adsActive.map((ad) => (
                            <option key={ad.id} value={ad.id}>{ad.title}</option>
                        ))}
                    </select>
                    <select value={rating} className={"ad-review-block"} onChange={handleRatingChange} required>
                        <option value="">-- Выберите оценку --</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                    <input value={comment}
                           className={"ad-review-block"}
                           type={"text"}
                           placeholder={"Оставьте комментарий"}
                           onChange={handleCommentChange}/>
                    <button type={"submit"} className={"button-confirm"}>Отправить</button>
                </form>
            </div>
        </>
    )
}

export default AdReview