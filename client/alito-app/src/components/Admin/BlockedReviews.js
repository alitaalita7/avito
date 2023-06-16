import React from 'react';
import recoveryImage from "../../image/recovery.png";

const BlockedReviews = ({id, from_user, to_user, advertisement_id, date_posted, setReviews}) => {

    const showRecoveryConfirmReviews = (id) => {
        const con = window.confirm("Восстановить отзыв с id = " + (id) + "?")
        if (con) {
            fetch(`http://localhost:8080/admin/recovery-reviews`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({admin_id: JSON.parse(localStorage.getItem("userInfo")).id, review_id: id}),
            })
                .then((res) => {
                    if (res.status == 204) {
                        setReviews(state => {
                            return state.filter((el) => el.id !== id)
                        })
                    }
                })
        }
    }

    return (
        <tr>
            <td>{id}</td>
            <td>{from_user}</td>
            <td>{to_user}</td>
            <td>{advertisement_id}</td>
            <td>{date_posted}</td>
            <td>
                <button className={"recovery-button-ad"} onClick={()=>showRecoveryConfirmReviews(id)}>
                    <img className={"recoveryImage"} src={recoveryImage}/>
                </button>
            </td>
        </tr>
    );
};

export default BlockedReviews;