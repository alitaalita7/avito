import React, {useEffect, useState} from "react";
import recoveryImage from "../../image/recovery.png"
import "./Admin.css"
import BlockedUsers from "./BlockedUsers";
import BlockedAdvertisements from "./BlockedAdvertisements";
import BlockedReviews from "./BlockedReviews";

const Admin = () => {

    const [users, setUsers] = useState([]);
    const [advertisements, setAdvertisements] = useState([]);
    const [reviews, setReviews] = useState([]);

    useEffect(()=>{
        fetch(`http://localhost:8080/admin/users`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({admin_id: JSON.parse(localStorage.getItem("userInfo")).id}),
        })
            .then(res=>res.json())
            .then(res=>setUsers(res))

        fetch(`http://localhost:8080/admin/advertisements`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({admin_id: JSON.parse(localStorage.getItem("userInfo")).id}),
        })
            .then(res=>res.json())
            .then(res=>setAdvertisements(res))

        fetch(`http://localhost:8080/admin/reviews`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({admin_id: JSON.parse(localStorage.getItem("userInfo")).id}),
        })
            .then(res=>res.json())
            .then(res=>setReviews(res))
    },[])

    return (
        <div className={"admin-container"}>
            <h1>Админ панель</h1>
            <h2>Заблокированные пользователи</h2>
            <table className={"table"}>
                <thead>
                    <th>id</th>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Телефон</th>
                    <th>is_admin</th>
                    <th>Восстановление</th>
                </thead>
                <tbody className={"table-body"}>
                {
                    users.map((user)=>(<BlockedUsers key={user.id} {...user} setUsers={setUsers}/>))
                }
                </tbody>
            </table>
            <h2>Заблокированные объявления</h2>
            <table className={"table"}>
                <thead>
                    <th>id</th>
                    <th>Название</th>
                    <th>Дата создания</th>
                    <th>Статус</th>
                    <th>Категория</th>
                    <th>id пользователя</th>
                    <th>Восстановление</th>
                </thead>
                <tbody className={"table-body"}>
                {
                    advertisements.map((ad)=>(<BlockedAdvertisements key={ad.id} {...ad} setAdvertisements={setAdvertisements}/>))
                }
                </tbody>
            </table>
            <h2>Заблокированные отзывы</h2>
            <table className={"table"}>
                <thead>
                    <th>id</th>
                    <th>От кого (user id)</th>
                    <th>Кому (user id)</th>
                    <th>id объявления</th>
                    <th>Дата создания</th>
                    <th>Восстановление</th>
                </thead>
                <tbody>
                {
                    reviews.map((r)=>(<BlockedReviews key={r.id} {...r} setReviews={setReviews}/>))
                }
                </tbody>
            </table>
        </div>
    )
}
export default Admin