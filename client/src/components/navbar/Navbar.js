import React, { useState } from 'react';
import { Layout, Menu, theme, Input } from 'antd';
import { Grid } from 'antd';
import { Dropdown, Button } from 'antd';
import { MenuOutlined } from '@ant-design/icons';
import Logo from './components/Logo';
import './Navbar.css';
import Profile from './components/Profile'; // Import your Profile component
import { Link } from 'react-router-dom';
// import { useAuth } from '../../context/AuthContext';
const { useBreakpoint } = Grid;
const { Header } = Layout;

const getItems = (role) => {
    // Default role is customer?
    const commonItems = [
        { key: '1', label: 'Upcoming Events', route: "/home" },
        { key: '2', label: 'Your Tickets', route: '/tickets' },
    ];

    if (role === 'ROLE_OFFICER') {
        return [
            { key: '1', label: 'Your Events', route: '/home' },
        ];
    } else if (role === 'ROLE_MANAGER') {
        return [
            { key: '1', label: 'Your Events', route: "/home" },
            { key: '2', label: 'Add Event', route: "/add" },
        ];
    }

    return commonItems;
};

const App = () => {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();
    // const { authUser } = useAuth();
    // console.log(authUser.roles);
    const role = 'ROLE_MANAGER'; // Add in logic to retrieve user's role
    const items = getItems(role);
    const [searchWidth, setSearchWidth] = useState('50px');
    const screens = useBreakpoint();
    const dropdownMenu = (
        <Menu
            theme="dark"
            mode="vertical"
            defaultSelectedKeys={['1']}
        >
            {items.map(item => {
                console.log(item.route);
                return (
                    <Menu.Item key={item.key}>
                        <Link to={item.route}>
                            {item.label}
                        </Link>
                    </Menu.Item>
                );
            })}
        </Menu>
    );

    return (
        <Layout>
            <Header
                style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between', // Add this line
                }}
            >
                <Logo className='logo' />
                {screens.xs ? (
                    <Dropdown overlay={dropdownMenu}>
                        <Button
                            className="menu-button"
                            type="primary"
                            icon={<MenuOutlined />}
                        />
                    </Dropdown>
                ) : (
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={['1']}
                        style={{ flex: 1 }}
                    >
                        {items.map(item => (
                            <Menu.Item key={item.key}>
                                {item.label}
                            </Menu.Item>
                        ))}
                    </Menu>
                )}
                <div style={{ display: 'flex', alignItems: 'center', position: 'absolute', right: 0 }}>
                    <Input.Search
                        className='search-bar'
                        enterButton
                        style={{ width: searchWidth, marginRight: '100px' }}
                        onFocus={() => setSearchWidth('200px')}
                        onBlur={() => setSearchWidth('50px')}
                    />
                    <Profile />
                </div>
            </Header>
        </Layout>
    );
}

export default App;