import SearchIcon from "@mui/icons-material/Search";
import React, {useState} from "react";
import {alpha, InputBase, styled} from "@mui/material";
import Box from "@mui/material/Box";

const Search = styled('div')(({theme}) => ({
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.white, 0.15),
    '&:hover': {
        backgroundColor: alpha(theme.palette.common.white, 0.25),
    },
    marginLeft: 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
        marginLeft: theme.spacing(1),
        width: 'auto',
    },
}));

const SearchIconWrapper = styled('div')(({theme}) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
}));


const StyledInputBase = styled(InputBase)(({theme: {breakpoints, spacing, transitions}}) => ({
    color: 'inherit',
    '& .MuiInputBase-input': {
        padding: spacing(1, 1, 1, 0),
        // vertical padding + font size from searchIcon
        paddingLeft: `calc(1em + ${spacing(4)})`,
        transition: transitions.create('width'),
        width: '100%',
        [breakpoints.up('sm')]: {
            width: '12ch',
            '&:focus': {
                width: '20ch',
            },
        },
    },
}));

const SearchBar = ({search, handleChange}: any) => {
    return (
        <Box>
            <Search>
                <SearchIconWrapper>
                    <SearchIcon/>
                </SearchIconWrapper>
                <StyledInputBase
                    value={search}
                    placeholder="Search by title"
                    onChange={handleChange}
                    inputProps={{'aria-label': 'search'}}
                />
            </Search>
        </Box>
    )
}

export {SearchBar}