import React, {useEffect, useState} from "react";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import "./UserInfo.css"
import {set} from "react-hook-form";
import deleteImage from "../../../image/delete.svg";

const UserInfo = ({setIsLogIn}) => {

    const {id} = useParams()
    const [reviewInfo, setReviewInfo] = useState({});
    const [user, setUser] = useState({});
    const [firstLetter, setFirstLetter] = useState(' ')
    const navigate = useNavigate();

    useEffect(() => {

        fetch("http://localhost:8080/get-review-info-by-user", {
            method: "POST",
            headers: {'content-type': "application/json"},
            body: JSON.stringify({id: id})
        })
            .then(res => res.json())
            .then(res => setReviewInfo(res))
        fetch("http://localhost:8080/get-user-info-by-id", {
            method: 'POST',
            headers: {'content-type': "application/json"},
            body: JSON.stringify({user_id: id})
        })
            .then(res => res.json())
            .then(res => {
                setUser(res);
                setFirstLetter(res.name.slice(0, 1))
            })
    }, [id])


    const {pathname} = useLocation()
    const styleActiveLink = {color: "black", fontWeight: "bold"};

    const isAdmin = () => {
        const admin = JSON.parse(localStorage.getItem("userInfo")).is_admin
        if (admin) {
            return true
        } else return false
    }

    const showConfirmToDelete = (id) => {
        if(id === JSON.parse(localStorage.getItem("userInfo")).id){
            const con = window.confirm("Вы действительно хотите удалить свой профиль?")
            if (con) {
                fetch(`http://localhost:8080/delete-user`, {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify({user_id: id}),
                })
                    .then((res) => {
                        if (res.status == 204)
                            localStorage.removeItem("userInfo");
                            setIsLogIn(false)
                            navigate(`/logIn`)
                    })
            }
        }
        else {const con = window.confirm("Удалить пользователя с id = " + (id) + "?")
            if (con) {
                fetch(`http://localhost:8080/admin/delete-user`, {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify({user_id: id, admin_id: JSON.parse(localStorage.getItem("userInfo")).id}),
                })
                    .then((res) => {
                        if (res.status == 204)
                            navigate(`/admin`)
                    })
            }}
    }

    return (
        <div className={"user-info"}>
            <div className={"user-photo"}>
                <h1>{firstLetter}</h1>
            </div>
            <div className={"user-name"}>
                <h2>{user.name} {user.surname}</h2>
            </div>
            <div className="review">
                {reviewInfo.avg ?
                    <p>{reviewInfo.avg.toFixed(1) + " (из " + reviewInfo.count + " отзывов)"}</p>
                    : <p>Нет отзывов</p>}
            </div>
            <div className={"user-section"}>
                <Link to={"/profile/" + user?.id + "/ads"}
                      style={pathname === "/profile/" + user?.id + "/ads" ? styleActiveLink : {}}>Объявления</Link>
                <Link to={"/profile/" + user?.id + "/reviews"}
                      style={pathname === "/profile/" + user?.id + "/reviews" ? styleActiveLink : {}}>Отзывы</Link>
                {JSON.parse(localStorage.getItem('userInfo')).id === user?.id ?
                    <>
                        <Link to={"/profile/" + user?.id + "/favorites"}
                              style={pathname === "/profile/" + user?.id + "/favorites" ? styleActiveLink : {}}>Избранное</Link>
                        <Link to={"/profile/" + user?.id + "/settings"}
                              style={pathname === "/profile/" + user?.id + "/settings" ? styleActiveLink : {}}>Настройки
                            профиля</Link>
                    </>
                    :
                    <Link to={"/profile/" + user?.id + "/add-review"}
                          style={pathname === "/profile/" + user?.id + "/add-review" ? styleActiveLink : {}}>Оставить
                        отзыв</Link>
                }
                {(isAdmin() || JSON.parse(localStorage.getItem('userInfo')).id === user?.id) &&
                    <button className={"remove-profile"}
                            onClick={()=>showConfirmToDelete(user.id)}>
                        Удалить профиль
                    </button>}
            </div>
        </div>
    )
}

export default UserInfo