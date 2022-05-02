import React from "react";
import {AppBar, BottomNavigation, BottomNavigationAction, Container, Link, Toolbar, Typography} from "@mui/material";
import {ListAlt, PlusOne} from "@mui/icons-material";

const TopMenu = () => {
    const [value, setValue] = React.useState(0);
    return (
        <AppBar position={'static'}>
            <Container maxWidth='xl'>
            <Toolbar disableGutters>
                <Typography
                    variant="h6"
                    noWrap
                    component="div"
                    sx={{mr: 2, display: {xs: 'none', md: 'flex'}}}
                >
                    Cooking-time
                </Typography>
                <BottomNavigation showLabels value={value} onChange={(event, newValue) => {
                    setValue(newValue);
                }}>
                    <BottomNavigationAction label="Recipes" icon={<ListAlt/>} href={"/recipes"}/>
                    <BottomNavigationAction label="Add" icon={<PlusOne/>} href={"/add"}/>
                </BottomNavigation>
            </Toolbar>
            </Container>
        </AppBar>
    )
}

export {TopMenu};