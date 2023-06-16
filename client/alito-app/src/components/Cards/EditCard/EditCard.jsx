import React, {useEffect, useState} from "react";
import EditImg from "../../../image/edit.svg"
import {useNavigate, useParams} from "react-router-dom";
import './EditCard.css'

const EditCard = () => {
    const categories = [
        {
            id: 1,
            title: "Транспорт",
        },
        {
            id: 2,
            title: "Работа",
        },
        {
            id: 3,
            title: "Недвижимость",
        },
        {
            id: 4,
            title: "Услуги"
        },
        {
            id: 5,
            title: "Для дома и дачи",
        },
        {
            id: 6,
            title: "Личные вещи",
        },
        {
            id: 7,
            title: "Электроника",
        },
        {
            id: 8,
            title: "Хобби и отдых"
        },
        {
            id: 9,
            title: "Животные"
        }];

    const {id} = useParams();
    const navigate = useNavigate()
    const [cardData, setCardData] = useState({});

    const [titleValue, setTitleValue] = useState('');
    const [categoryValue, setCategoryValue] = useState('');
    const [priceValue, setPriceValue] = useState('');
    const [cityValue, setCityValue] = useState('');
    const [districtValue, setDistrictValue] = useState('');
    const [streetValue, setStreetValue] = useState('');
    const [houseValue, setHouseValue] = useState('');
    const [photoValue, setPhotoValue] = useState('');
    const [descriptionValue, setDescriptionValue] = useState('');

    const getCardData = () => {
        fetch("http://localhost:8080/get-advertisement-by-id", {
            method: "POST",
            headers: {'content-type': "application/json"},
            body: JSON.stringify({id: id})
        })
            .then(res => res.json())
            .then(res => {
                setCardData(res)
                setTitleValue(res.title)
                setCategoryValue(res.category)
                setPriceValue(res.price)
                setCityValue(res.city)
                setDistrictValue(res.district)
                setStreetValue(res.street)
                setHouseValue(res.house)
                setPhotoValue(res.photo)
                setDescriptionValue(res.description)
            })
    }

    const updateCardData = async (data) => {

        const response = await fetch(`http://localhost:8080/edit-advertisement/${id}`, {
            method: 'PUT',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        })
        const res_json = await response.json()
        setCardData(res_json)
    }

    const handleUpdateCardData = async (e) => {
        e.preventDefault()
        const inputs = document.querySelectorAll('.set-input')
        const data = {}

        inputs.forEach(item => {
            const field_name = item.name
            if (item.value) {
                data[field_name] = item.value
            } else {
                data[field_name] = item.placeholder
            }
        })

        await updateCardData(data)
        navigate(`/ad/${id}`)
    }

    useEffect(() => {
        getCardData()
    }, [])

    return (
        <form onSubmit={handleUpdateCardData} className={"edit-container"}>
            <h1 className="title-edit-card">Редактирование объявления</h1>
            <div className="edit">
                <p>Название:</p>
                <input className="set-input"
                       type="text"
                       name={"title"}
                       value={titleValue}
                       onChange={(event) => setTitleValue(event.target.value)}
                />
            </div>
            <div className="edit">
                <p>Категория:</p>
                <select className="set-input"
                        name={"category"}
                        value={categoryValue}
                        onChange={(event) => setCategoryValue(event.target.value)}>
                    {/*<option>{categoryValue}</option>*/}
                    {categories.map((category) => (
                        <option key={category.id}>{category.title}</option>
                    ))}
                </select>
            </div>
            <div className="edit">
                <p>Цена:</p>
                <input className="set-input"
                       type="text"
                       name={"price"}
                       value={priceValue}
                       onChange={(event) => setPriceValue(event.target.value)}
                />
            </div>
            <div className="edit">
                <p>Город:</p>
                <input className="set-input"
                       type="text"
                       name={"city"}
                       value={cityValue}
                       onChange={(event) => setCityValue(event.target.value)}
                />
            </div>
            <div className="edit">
                <p>Район:</p>
                <input className="set-input"
                       type="text"
                       name={"district"}
                       value={districtValue}
                       onChange={(event) => setDistrictValue(event.target.value)}
                />
            </div>
            <div className="edit">
                <p>Улица:</p>
                <input className="set-input"
                       type="text"
                       name={"street"}
                       value={streetValue}
                       onChange={(event) => setStreetValue(event.target.value)}
                />
            </div>
            <div className="edit">
                <p>Дом:</p>
                <input className="set-input"
                       type="text"
                       name={"house"}
                       value={houseValue}
                       onChange={(event) => setHouseValue(event.target.value)}
                />
            </div>
            <div className="edit">
                <p>Фото:</p>
                <input className="set-input"
                       type="text"
                       name={"photo"}
                       value={photoValue}
                       onChange={(event) => setPhotoValue(event.target.value)}
                />
            </div>
            <div className="edit">
                <p>Описание:</p>
                <input className="set-input"
                       type="text"
                       name={"description"}
                       value={descriptionValue}
                       onChange={(event) => setDescriptionValue(event.target.value)}
                />
            </div>
            <button type={"submit"} className={"button-confirm"}>Сохранить изменения</button>
        </form>
    );
};

export default EditCard
