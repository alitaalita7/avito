import React from 'react';
import NewAdForm from './NewAdForm';
// import SearchBar from './SearchBar';
// import { useState } from 'react';

function NewAd() {
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
    return (
      <div className="new-add">
        <NewAdForm categories={categories} />
      </div>
    );
  }
export default NewAd