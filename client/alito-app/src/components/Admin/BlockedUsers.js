import React from 'react';
import recoveryImage from "../../image/recovery.png";
import {set} from "react-hook-form";

const BlockedUsers = ({id, name, surname, phone, is_admin, setUsers}) => {

    const showRecoveryConfirmUser = (id) => {
        fetch(`http://localhost:8080/admin/recovery-user`,{
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({admin_id: JSON.parse(localStorage.getItem("userInfo")).id, user_id:id}),
        })
            .then((res) => {
                if (res.status == 204){
                    setUsers(state=>{
                     return state.filter((el)=>el.id!==id)
                    })
                }
            })
    }
    return (
        <tr>
            <td>{id}</td>
            <td>{name}</td>
            <td>{surname}</td>
            <td>{phone}</td>
            <td>{is_admin? "true": "false"}</td>
            <td>
                <button className={"recovery-button-ad"} onClick={()=>showRecoveryConfirmUser(id)}>
                    <img className={"recoveryImage"} src={recoveryImage}/>
                </button>
            </td>
        </tr>
    )
};

export default BlockedUsers;