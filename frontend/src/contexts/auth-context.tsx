'use client';

import { createContext, useCallback, useEffect, useState } from 'react';
import { parseCookies, setCookie } from 'nookies';
import { redirect, useRouter } from 'next/navigation';
import { api } from '@/lib/axios';
import { User } from '@/types';

type SignInCredentials = {
  email: string;
  password: string;
};

type AuthContextType = {
  isAuthenticaded: boolean;
  user: User | null;
  setUser: (user: User) => void;
  employeeSignIn: (credentials: SignInCredentials) => Promise<void>;
  adminSignIn: (credentials: SignInCredentials) => Promise<void>;
  signOut: () => void;
  isAuthLoading: boolean;
};

export const AuthContext = createContext({} as AuthContextType);

export function AuthProvider({ children }: any) {
  const [user, setUser] = useState<User | null>(null);
  const [isAuthenticaded, setIsAuthenticaded] = useState(false);
  const [isAuthLoading, setIsAuthLoading] = useState(true);

  const router = useRouter();

  // const isAuthenticaded = !!user;

  async function employeeSignIn({ email, password }: SignInCredentials) {
    const { data } = await api.post('/employees/login', {
      email,
      password,
    });

    setCookie(undefined, 'songs.token', data.token, {
      maxAge: 60 * 60 * 3, // 3 hours
    });

    api.defaults.headers['Authorization'] = `Bearer ${data.token}`;

    // setUser(data.userDTO);

    getUser();
    setIsAuthenticaded(true);
    return data;
  }

  async function adminSignIn({ email, password }: SignInCredentials) {
    const { data } = await api.post('/admins/login', {
      email,
      password,
    });

    setCookie(undefined, 'songs.token', data.token, {
      maxAge: 60 * 60 * 3, // 3 hours
    });

    api.defaults.headers['Authorization'] = `Bearer ${data.token}`;

    // setUser(data.userDTO);
    getUser();
    setIsAuthenticaded(true);
    return data;
  }

  function signOut() {
    setCookie(undefined, 'songs.token', '', {
      maxAge: -1,
    });

    setUser(null);

    setIsAuthLoading(false);

    setIsAuthenticaded(false);
    console.log('signOut isAuthenticaded', isAuthenticaded);
    redirect('/');
  }

  function getUser() {
    const { 'songs.token': token } = parseCookies();

    if (token) {
      api.defaults.headers['Authorization'] = `Bearer ${token}`;
      api
        .get('/current')
        .then((response) => {
          const user = response.data;

          setUser(user);

          if (!user?.admin) {
            api
              .get('/employees/current')
              .then((response) => {
                const user = response.data;

                setUser(user);

                setIsAuthenticaded(true);
                setIsAuthLoading(false);
              })
              .catch((error) => {
                console.log(error);
                setIsAuthLoading(false);

                signOut();
              });
          }

          setIsAuthenticaded(true);
          setIsAuthLoading(false);
        })
        .catch((error) => {
          console.log(error);
          setIsAuthLoading(false);

          signOut();
        });
    } else {
      setIsAuthenticaded(false);
      setIsAuthLoading(false);
    }
  }

  useEffect(() => {
    getUser();
  }, []);

  return (
    <AuthContext.Provider
      value={{
        user,
        setUser,
        isAuthenticaded,
        employeeSignIn,
        adminSignIn,
        signOut,
        isAuthLoading,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
