import React from "react";
import {Link} from "react-router-dom";
import styled from "@emotion/styled";

const NavMenu = styled.menu`
  display: flex;
  flex-direction: row;
  list-style: none;
  align-items: start;
  background: coral;

`

const NavItem = styled.ul`
`

const NavButton = styled.button`
  &:hover {
    color: white;
  }
`

const NavBar = () => {
    return (
        <NavMenu>
            <h4>
                Cooking-time
            </h4>
            <NavItem>
                <NavButton><Link to={"/recipes"}>Recipes</Link></NavButton>
            </NavItem>
            {/*TODO: Figure out how Link is different to link*/}
            <NavItem>
                <NavButton><Link to={"/add"}>Add recipe</Link></NavButton>
            </NavItem>
        </NavMenu>
    )
}

export default NavBar;