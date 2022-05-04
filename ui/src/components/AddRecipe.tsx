import React, {useState} from "react";
import RecipeService from "../services/RecipeService";

const AddRecipe = () => {
    const [url, setUrl] = useState('');
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setUrl(event.target.value);
    };

    const loadUrl = async () => {
        console.log(url)
        await RecipeService.create({url: url})
    }

    return(
        <div>
            <input value={url} onChange={handleInputChange} />
            <button onClick={loadUrl}>Add</button>
        </div>
    )

}
export {AddRecipe}