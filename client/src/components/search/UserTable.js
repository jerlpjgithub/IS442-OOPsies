import React from 'react';
import { Table, Pagination } from 'antd';
import { useNavigate } from 'react-router-dom';

const UserTable = ({ users, loading, pageSize, total, currentPage, onPageChange }) => {
    
    const navigate = useNavigate();

    const columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'Email',
            dataIndex: 'email',
            key: 'email',
        },
        {
            title: 'First Name',
            dataIndex: 'firstName',
            key: 'firstName',
        },
        {
            title: 'Last Name',
            dataIndex: 'lastName',
            key: 'lastName',
        },
        {
            title: 'Roles',
            dataIndex: 'roles',
            key: 'roles',
            render: roles => roles.map(role => role.name).join(', ')
        },
        {
            title: 'Email Verified',
            dataIndex: 'emailVerified',
            key: 'emailVerified',
            render: emailVerified => emailVerified ? 'Yes' : 'No'
        },
        {
            title: 'Account Balance',
            dataIndex: 'accountBalance',
            key: 'accountBalance',
        },
        {
            title: 'Provider',
            dataIndex: 'provider',
            key: 'provider',
        },
    ];

    return (
        <>
            <Table
                dataSource={users}
                columns={columns}
                pagination={false}
                loading={loading}
                rowKey="id"
                onRow={(record) => ({
                    onClick: () => {
                       navigate(`/profile/${record.id}`); // Redirect to the user page on row click
                    }
                })}
            />
            <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
                <Pagination
                    pageSize={pageSize}
                    total={total}
                    current={currentPage}
                    onChange={onPageChange}
                    style={{ marginTop: '20px', textAlign: 'right' }}
                />
            </div>
        </>
    );
};

export default UserTable;
