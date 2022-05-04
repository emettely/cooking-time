import React from "react";

const Recipe = ({recipe}:any) => {
    return (
        <div>
            <h4>{recipe.title}</h4>
            <p>{recipe.description}</p>
        </div>
    )

}
export {Recipe}