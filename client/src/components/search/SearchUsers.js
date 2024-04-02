import React, { useState } from 'react';
import { Input, Button } from 'antd';
import UserTable from './UserTable';

const SearchUsers = ({ onSearch }) => {
    const [searchTerm, setSearchTerm] = useState('');

    const resetSearch = () => {
        setSearchTerm('');
        onSearch('');
    };

    return (
        <div style={{ marginBottom: '20px' }}>
            <Input
                placeholder="Search users..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{ width: '200px', marginRight: '10px' }}
            />
            <Button type="primary" onClick={() => onSearch(searchTerm)}>Search</Button>
            <Button onClick={resetSearch} style={{ marginLeft: '10px' }}>Reset</Button>
        </div>
    );
};

export default SearchUsers;
