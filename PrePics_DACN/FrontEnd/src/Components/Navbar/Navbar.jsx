import * as React from 'react';
import UserAvatar from '../User/UserAvatar';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import MenuItem from '@mui/material/MenuItem';
import AdbIcon from '@mui/icons-material/Adb';
import { useNavigate } from 'react-router-dom';

const pages = [
    { name: 'Trang chủ', path: '/' },
    { name: 'Upload', path: '/Upload' },
    { name: 'Liên hệ', path: '/' },
    { name : 'Video' , path: '/'},
    { name : 'Ảnh' , path: '/'},
];

function Navbar({UserInfo , onClickVideo , handleLogOut , isLogin}){
    const [anchorElNav, setAnchorElNav] = React.useState(null);
    const navigate = useNavigate();

    const handleOpenNavMenu = (event) => {
      setAnchorElNav(event.currentTarget);
    };
  
    function handleCloseNavMenu(page) {
       if(page.name === 'Video'){
            onClickVideo(page.name);
       }
       if(page.name === 'Ảnh'){
            onClickVideo(page.name);
       }
       if(page.name === 'Upload' && !isLogin){
            navigate('/Login');
            return;
       }
       navigate(page.path);
    }
  
    const handlSignIn = () =>{
        navigate('Login');   
    }

    const handlSignUp = () =>{
        navigate('SignUp');
    }

    return (
        <>
            <div>
                <AppBar style={{ backgroundColor: '#379d7d'}}>
                <Container maxWidth="sl">
                    <Toolbar disableGutters>
                    <AdbIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
                    <Typography
                        variant="h6"
                        noWrap
                        component="a"
                        href="/"
                        sx={{
                        mr: 2,
                        display: { xs: 'none', md: 'flex' },
                        fontFamily: 'monospace',
                        fontWeight: 700,
                        letterSpacing: '.3rem',
                        color: 'inherit',
                        textDecoration: 'none',
                        }}
                    >
                        PrePics
                    </Typography>

                    <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
                        <Menu
                        id="menu-appbar"
                        anchorEl={anchorElNav}
                        anchorOrigin={{
                            vertical: 'bottom',
                            horizontal: 'left',
                        }}
                        keepMounted
                        transformOrigin={{
                            vertical: 'top',
                            horizontal: 'left',
                        }}
                        open={Boolean(anchorElNav)}
                        onClose={handleCloseNavMenu}
                        sx={{ display: { xs: 'block', md: 'none' } }}
                        >
                        {pages.map((page) => (
                            <MenuItem key={page.name}>
                            <Typography sx={{ textAlign: 'center' }}>{page.name}</Typography>
                            </MenuItem>
                        ))}
                        </Menu>
                    </Box>
                    <AdbIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
                    <Typography
                        variant="h5"
                        noWrap
                        component="a"
                        href="#app-bar-with-responsive-menu"
                        sx={{
                        mr: 2,
                        display: { xs: 'flex', md: 'none' },
                        flexGrow: 1,
                        fontFamily: 'monospace',
                        fontWeight: 700,
                        letterSpacing: '.3rem',
                        color: 'inherit',
                        textDecoration: 'none',
                        }}
                    >
                        LOGO
                    </Typography>
                    <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
                        {pages.map((page) => (
                        <Button
                            key={page.name}
                            onClick={() => {handleCloseNavMenu(page)}}
                            sx={{ my: 2, color: 'white', display: 'block' }}
                        >
                            {page.name}
                        </Button>
                        ))}
                    </Box>
                    {(UserInfo != null) ? (
                            <UserAvatar User = {UserInfo} Logout = {handleLogOut}/>
                        ) : (
                            <Box sx={{ display: 'flex', alignItems: 'center' }}>
                                <Button
                                    color="inherit"
                                    sx={{ mr: 2 }}
                                    onClick = {handlSignIn}
                                >
                                    Đăng nhập
                                </Button>
                                <Button
                                    color="inherit"
                                    onClick={handlSignUp}
                                >
                                    Đăng ký
                                </Button>
                            </Box>
                        )}
                    </Toolbar>
                </Container>
                </AppBar>
            </div>
        </>
    )
}
export default Navbar;