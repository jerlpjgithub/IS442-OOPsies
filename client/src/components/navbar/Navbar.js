import React, { useState, useEffect } from 'react';
import { Layout, Menu } from 'antd';
import { Grid } from 'antd';
import { Dropdown, Button } from 'antd';
import { MenuOutlined } from '@ant-design/icons';
import Logo from './components/Logo';
import './Navbar.css';
import Profile from './components/Profile';
import { Link, useLocation } from 'react-router-dom';
const { useBreakpoint } = Grid;
const { Header } = Layout;

const getItems = (role) => {
    const commonItems = [
        { key: '1', label: 'Upcoming Events', route: `/events` },
        { key: '2', label: 'Your Tickets', route: `/tickets` },
    ];

    if (role == 'ROLE_OFFICER') {
        return [
            { key: '1', label: 'Ticketing Home', route: `/ticketing-officer/home`},
            { key: '2', label: 'Upcoming Events', route: `/ticketing-officer/events` },
            { key: '3', label: 'User Management', route: '/ticketing-officer/usermanagement' },
        ];
    } else if (role == 'ROLE_MANAGER') {
        return [

            { key: '1', label: 'Your Events', route: "/home" },
            { key: '2', label: 'Events Management', route: "/event-manager/eventmanagement" },
            { key: '3', label: 'User Management', route: '/event-manager/usermanagement' },
        ];
    }

    return commonItems;
};

const App = () => {
    const user = JSON.parse(localStorage.getItem("authUser"));
    const role = user.roles;
    const items = getItems(role);
    const screens = useBreakpoint();
    const [selectedKey, setSelectedKey] = useState([]);
    const location = useLocation();

    const dropdownMenu = (
        <Menu
            theme="dark"
            mode="vertical"
            defaultSelectedKeys={[]}
            selectedKeys={selectedKey}
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

    useEffect(() => {
        const currentPath = location.pathname;
        const matchingItem = items.find(item => item.route === currentPath);
        if (matchingItem) {
            setSelectedKey([matchingItem.key]);
        } else {
            setSelectedKey([]);
        }
    }, [location]);

    return (
        <Layout>
            <Header
                style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between',
                }}
            >
                <Link to="/home">
                    <Logo className='logo' />
                </Link>
                {screens.xs ? (
                    <Dropdown menu={dropdownMenu}>
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
                        selectedKeys={selectedKey}
                        style={{ flex: 1 }}
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
                )}
                <div style={{ display: 'flex', alignItems: 'center', position: 'absolute', right: 10 }}>
                    <Profile />
                </div>
            </Header>
        </Layout>
    );
}

export default App;