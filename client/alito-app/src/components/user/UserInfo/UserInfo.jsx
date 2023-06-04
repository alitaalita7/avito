import React, {useEffect, useState} from "react";
import {Link, useLocation} from "react-router-dom";
import "./UserInfo.css"

const UserInfo = () => {

    const [reviewInfo, setReviewInfo] = useState({});

    useEffect(()=>{
        fetch("http://localhost:8080/get-review-info-by-user",{
            method:"POST",
            headers:{'content-type': "application/json"},
            body: JSON.stringify({id: JSON.parse(localStorage.getItem("userInfo")).id})
        })
            .then(res=> res.json())
            .then(res=> setReviewInfo(res))
    }, [])

    const user = JSON.parse(localStorage.getItem("userInfo"));
    const {pathname} = useLocation()
    const styleActiveLink = {color: "black", fontWeight: "bold"};

    return (
        <div className={"user-info"}>
            <div className={"user-photo"}>
                <h1>{user.name.slice(0, 1)}</h1>
            </div>
            <div className={"user-name"}>
                <h2>{user.name} {user.surname}</h2>
            </div>
            <div className="review">
                {reviewInfo.avg?
                    <p>{reviewInfo.avg + " (из " + reviewInfo.count + " отзывов)"}</p>
                    : <p>Нет отзывов</p>}
            </div>
            <div className={"user-section"}>
                <Link to={"/my-ads"} style={pathname === "/my-ads" ? styleActiveLink : {}}>Мои объявления</Link>
                <Link to={"/reviews"} style={pathname === "/reviews" ? styleActiveLink : {}}>Отзывы</Link>
                <Link to={"/favorites"} style={pathname === "/favorites" ? styleActiveLink : {}}>Избранное</Link>
                <Link to={"/settings"} style={pathname === "/settings" ? styleActiveLink : {}}>Настройки профиля</Link>
            </div>
        </div>
    )
}

export default UserInfo