import React from 'react';
import recoveryImage from "../../image/recovery.png";

const BlockedAdvertisements = ({id, title, date_created, status, category, user_id, setAdvertisements}) => {

    const showRecoveryConfirmAdvertisement = (id) => {
        fetch(`http://localhost:8080/admin/recovery-advertisement`,{
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({admin_id: JSON.parse(localStorage.getItem("userInfo")).id, ad_id:id}),
        })
            .then((res) => {
                if (res.status == 204){
                    setAdvertisements(state=>{
                        return state.filter((el)=>el.id!==id)
                    })
                }
            })
    }


    return (
        <tr>
            <td>{id}</td>
            <td>{title}</td>
            <td>{date_created}</td>
            <td>{status}</td>
            <td>{category}</td>
            <td>{user_id}</td>
            <td>
                <button className={"recovery-button-ad"} onClick={()=>showRecoveryConfirmAdvertisement(id)}>
                    <img className={"recoveryImage"} src={recoveryImage}/>
                </button>
            </td>
        </tr>
    );
};

export default BlockedAdvertisements;