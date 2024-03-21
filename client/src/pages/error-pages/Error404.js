import React from 'react';
import './ErrorPage.css';
import { Result, Button } from 'antd';
// Import your error image directly if using it as an img src
import error404Image from './error404.svg';

const Error404 = () => {
    React.useEffect(() => {
        document.title = 'Page Not Found';
    }, []);

    return (
        <Result
            status="404"
            title="404"
            subTitle="Sorry, the page you visited does not exist."
            extra={<Button type="primary" href="/">Back Home</Button>}
            icon={<img src={error404Image} alt="Error" style={{ height: '20rem' }} />}
        />
    );
};

export default Error404;
