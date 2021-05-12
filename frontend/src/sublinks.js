import { FiSettings } from 'react-icons/fi';
import { GoSignOut, GoSignIn } from 'react-icons/go';
import React from 'react';

const sublinks = [
    {
        page: 'profile',
        links: [
            {label: 'Account Settings', icon: <FiSettings/> , url: '/settings'}
            {label: 'Logout', icon: <GoSignOut/> , url: '/logout'}
            {label: 'Login', icon: <GoSignIn/> , url: '/login'}
        ],
    }
];

export default sublinks;