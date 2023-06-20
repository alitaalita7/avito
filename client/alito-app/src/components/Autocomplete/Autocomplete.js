import React, { useState } from 'react';
import './Autocomplete.css';

const Autocomplete = ({ad_id, selectedKeywords, setSelectedKeywords}) => {
    const [inputValue, setInputValue] = useState('');
    const [keywords, setKeywords] = useState([]);

    const handleChange = (event) => {
        const value = event.target.value;
        setInputValue(value);

        fetch('http://localhost:8080/get-keywords')
            .then((res) => res.json())
            .then((res) => {
                const filteredKeywords = res.filter(
                    (item) =>
                        item.word.toLowerCase().includes(value.toLowerCase()) &&
                        !selectedKeywords.some((selectedItem) => selectedItem.word === item.word)
                );
                setKeywords(filteredKeywords);
            });
    };

    const handleSelect = (value) => {
        setSelectedKeywords([...selectedKeywords, value]);
        setInputValue('');
        setKeywords([]);
    };

    const handleRemove = (value, event) => {
        event.preventDefault();
        const updatedKeywords = selectedKeywords.filter(
            (keyword) => keyword !== value
        );
        setSelectedKeywords(updatedKeywords);
    };

    return (
        <div className="autocomplete">
            <p>Ключевые слова помогут предолжить ваше объявление заинтересованным пользователям.</p>
            <input
                type="text"
                value={inputValue}
                onChange={handleChange}
                className="autocomplete-input"
                placeholder={"Начните вводить слово"}
            />
            {keywords.length > 0 && (
                <ul className="autocomplete-list">
                    {keywords.map((item, index) => (
                        <li
                            key={index}
                            onClick={() => handleSelect(item)}
                            className="autocomplete-item"
                        >
                            {item.word}
                        </li>
                    ))}
                </ul>
            )}
            <div className="selected-keywords">
                {selectedKeywords.map((keyword, index) => (
                    <div key={index} className="selected-keyword">
                        {keyword.word}
                        <button
                            onClick={(event) => handleRemove(keyword, event)}
                            className="remove-button">
                            &times;
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Autocomplete;
