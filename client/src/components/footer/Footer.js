import React from 'react';
import { Breadcrumb, Layout, Menu, theme } from 'antd';
const { Footer } = Layout;

const App = () => {
    return <Footer
        style={{
            textAlign: 'center',
        }}
    >
        OOPsies Ticketing ©{new Date().getFullYear()} Created by OOPsies
    </Footer>
}

export default App;