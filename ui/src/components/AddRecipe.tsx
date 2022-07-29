import React, {useState} from "react";
import RecipeService from "../services/RecipeService";

const AddRecipe = () => {
    const [url, setUrl] = useState('https://www.epicurious.com/recipes/food/views/buttermilk-french-toast-358115');
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setUrl(event.target.value);
    };

    const loadUrl = async () => {
        console.log(url)
        let result = await RecipeService.create({url: url})
        console.log(result)
    }

    return(
        <div>
            <input value={url} onChange={handleInputChange} />
            <button onClick={loadUrl}>Add</button>
        </div>
    )

}
export {AddRecipe}