import React from 'react';
import { Layout, Menu, theme } from 'antd';
import Logo from './components/Logo';
import './Navbar.css';
import Profile from './components/Profile'; // Import your Profile component

const { Header } = Layout;

const items = [
    { key: '1', label: 'Upcoming Events' },
    { key: '2', label: 'Your Tickets' },
];

const App = () => {
    const {
        token: { colorBgContainer, borderRadiusLG },
    } = theme.useToken();

    return (
        <Layout>
            <Header
                style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'space-between', // Add this line
                }}
            >
                <Logo className='logo'  />
                <Menu
                    theme="dark"
                    mode="horizontal"
                    defaultSelectedKeys={['1']}
                    style={{ flex: 1 }} // Add this line
                >
                    {items.map(item => (
                        <Menu.Item key={item.key}>
                            {item.label}
                        </Menu.Item>
                    ))}
                </Menu>
                <Profile />
            </Header>
        </Layout>
    );
}

export default App;