import React, { useState } from 'react';
import { Layout, Menu, theme, Input } from 'antd';
import { Grid } from 'antd';
import { Dropdown, Button } from 'antd';
import { MenuOutlined } from '@ant-design/icons';
import Logo from './components/Logo';
import './Navbar.css';
import Profile from './components/Profile'; 
import { Link } from 'react-router-dom';
const { useBreakpoint } = Grid;
const { Header } = Layout;

const getItems = (role) => {
    const commonItems = [
        { key: '1', label: 'Upcoming Events', route: `/events` },
        { key: '2', label: 'Your Tickets', route: `/tickets` },
    ];

    if (role == 'ROLE_OFFICER') {
        return [
            { key: '1', label: 'Your Events', route: `/home` },
        ];
    } else if (role == 'ROLE_MANAGER') {
        return [
            { key: '1', label: 'Your Events', route: `/home` },
            { key: '2', label: 'Add Event', route: `/add` },
        ];
    }

    return commonItems;
};

const App = () => {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();
    const user = JSON.parse(localStorage.getItem("authUser"));
    const role = user.roles;
    const items = getItems(role);
    const screens = useBreakpoint();
    const dropdownMenu = (
        <Menu
            theme="dark"
            mode="vertical"
            defaultSelectedKeys={[]}
        >
            {items.map(item => {
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
                    justifyContent: 'space-between', 
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
                        defaultSelectedKeys={[]}
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
                    <Profile />
                </div>
            </Header>
        </Layout>
    );
}

export default App;