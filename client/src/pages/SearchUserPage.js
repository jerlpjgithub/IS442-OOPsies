import React, { useState, useEffect } from 'react';
import SearchUsers from '../components/search/SearchUsers';
import UserTable from '../components/search/UserTable';
import axios from 'axios';

export const SearchUserPage = () => {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize] = useState(10); // Adjust as needed
    const [total, setTotal] = useState(0);
    const [searchTerm, setSearchTerm] = useState(''); // Added to track the search term

    const fetchUsers = async () => {
        setLoading(true);
        try {
            // Updated to include the searchTerm in the request
            const response = await axios.get(`http://localhost:8080/api/user?page=${currentPage - 1}&size=${pageSize}&query=${searchTerm}`);
            setUsers(response.data.content); // Adjust according to your API response structure
            setTotal(response.data.totalElements); // Adjust according to your API response structure
        } catch (error) {
            console.error('Failed to fetch users:', error);
            // Implement your error handling logic here
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, [currentPage, searchTerm]); // Added searchTerm as a dependency

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    const handleSearch = (term) => {
        setSearchTerm(term); // Set the search term state
        setCurrentPage(1); // Reset to first page for new search results
    };

    return (
        <div style={{ padding: '20px' }}>
            <SearchUsers onSearch={handleSearch} />
            <UserTable
                users={users}
                loading={loading}
                pageSize={pageSize}
                total={total}
                currentPage={currentPage}
                onPageChange={handlePageChange}
            />
        </div>
    );
};
