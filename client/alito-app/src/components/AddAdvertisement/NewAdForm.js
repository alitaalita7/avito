import React, {useEffect, useState} from 'react';
import './NewAdForm.css'
const NewAdForm = ({categories}) => {

    const [title, setTitle] = useState('');
    const [category, setCategory] = useState('');
    const [price, setPrice] = useState('');
    const [photo, setPhoto] = useState('');
    const [description, setDescription] = useState('');
    const handleTitleChange = (event) => {setTitle(event.target.value);};
    const handleCategoryChange = (event) => {setCategory(event.target.value);};
    const handlePriceChange = (event) => {setPrice(event.target.value);};
    const handlePhotosChange = (event) => {setPhoto(event.target.value);};
    const handleDescriptionChange = (event) => {setDescription(event.target.value);};

    const [city, setCity] = useState('');
    const [district, setDistrict] = useState('');
    const [street, setStreet] = useState('');
    const [house, setHouse] = useState('');
    const handleSubmit = (event) => {
        event.preventDefault();
        // TODO: валидация собранных данных, сплит фото по слешу(брать последний элемент)
        const data = {
            title,
            description,
            "date_created": null,
            "status": null,
            price,
            category,
            city,
            district,
            street,
            house,
            photo,
            "user_id": JSON.parse(localStorage.getItem("userInfo")).id
        };
        fetch("http://localhost:8080/advertisement/add", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
            .then((res) => alert('123'))
    };

    return (
        <form onSubmit={handleSubmit}>
            <div className={"input-block"}>
                <label htmlFor="title">Название объявления:</label>
                <input id="title" type="text" value={title} onChange={handleTitleChange} required/>
            </div>
            <div className={"input-block"}>
                <label htmlFor="category">Категория:</label>
                <select id="category" value={category} onChange={handleCategoryChange} required>
                    <option value="">-- Выберите категорию --</option>
                    {categories.map((category) => (
                        <option key={category.id} value={category.title}>{category.title}</option>
                    ))}
                </select>
            </div>
            <div className={"input-block"}>
                <label htmlFor="price">Цена:</label>
                <input id="price" type="number" value={price} onChange={handlePriceChange} required/>
            </div>
            <div className={"input-block"}>
                <label htmlFor="city">Город:</label>
                <input id="city" type="text" value={city} onChange={(event) => setCity(event.target.value)} required/>
            </div>
            <div className={"input-block"}>
                <label htmlFor="district">Район:</label>
                <input id="district" type="text" value={district} onChange={(event) => setDistrict(event.target.value)}
                       required/>
            </div>
            <div className={"input-block"}>
                <label htmlFor="street">Улица:</label>
                <input id="street" type="text" value={street} onChange={(event) => setStreet(event.target.value)}
                       required/>
            </div>
            <div className={"input-block"}>
                <label htmlFor="house">Дом:</label>
                <input id="house" type="text" value={house} onChange={(event) => setHouse(event.target.value)}
                       required/>
            </div>
            <div className={"input-block"}>
                <label htmlFor="photo">Фотографии:</label>
                <input id="photo" type="text" multiple onChange={handlePhotosChange}/>
            </div>
            <div className={"input-block"}>
                <label htmlFor="description">Описание:</label>
                <textarea id="description" value={description} onChange={handleDescriptionChange}/>
            </div>
            <button type="submit">Добавить объявление</button>
        </form>
    );
};

export default NewAdForm;
