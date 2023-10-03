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
  signIn: (credentials: SignInCredentials) => Promise<void>;
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

  async function signIn({ email, password }: SignInCredentials) {
    const { data } = await api.post('/login', {
      email,
      password,
    });

    setCookie(undefined, 'songs.token', data.token, {
      maxAge: 60 * 60 * 3, // 3 hours
    });

    api.defaults.headers['Authorization'] = `Bearer ${data.token}`;

    setUser(data.userDTO);
    setIsAuthenticaded(true);
    return data;
  }

  const signOut = useCallback(() => {
    setCookie(undefined, 'songs.token', '', {
      maxAge: -1,
    });
    setUser(null);
    setIsAuthenticaded(false);
    setIsAuthLoading(false);

    console.log('SAAAAAAAAAAAIIIIIIRRRRR');

    redirect('/');
  }, []);

  useEffect(() => {
    const { 'songs.token': token } = parseCookies();

    api.defaults.headers['Authorization'] = `Bearer ${token}`;

    if (token) {
      api
        .get('/current')
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
    } else {
      setIsAuthenticaded(false);
      setIsAuthLoading(false);
    }
  }, [user]);

  return (
    <AuthContext.Provider
      value={{
        user,
        setUser,
        isAuthenticaded,
        signIn,
        signOut,
        isAuthLoading,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
