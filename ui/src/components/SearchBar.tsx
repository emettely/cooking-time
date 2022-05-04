import React from "react";


const SearchBar = ({search, handleChange, handleSubmit}: any) => {
    return (
        <div>
            <input
                value={search}
                placeholder="Search by title"
                onChange={handleChange}
                onKeyPress={handleSubmit}
            />
        </div>
    )
}

export {SearchBar}