import React, {useState} from "react";
import IDropDownItem from "../types/DropDownItem";

const Dropdown = ({title, items, onSelect}: any) => {
    const [isOpen, setIsOpen] = useState(false);
    const [displayTitle, setDisplayTitle] = useState(title);

    let originalTitle = title

    const resetTitle = () => {
        setDisplayTitle(originalTitle)
    }

    const toggleOpen = () => {
        if (!isOpen) {
            resetTitle()
        }
        setIsOpen(!isOpen)
    }

    const selectItem = (item: IDropDownItem) => {
        const {title, id, key} = item
        setDisplayTitle(title)
        setIsOpen(false)
        onSelect(title, id, key)
    }

    return (
        <div>
            <button onClick={toggleOpen}>
                <div>{displayTitle}</div>
            </button>
            {
                isOpen && <div>
                    {items.map((item: any) => (
                        <button onClick={() => selectItem(item)}>
                            {item.title}
                        </button>))}
                </div>
            }
        </div>
    )
}

export default Dropdown;