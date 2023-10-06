'use client';

import { AuthContext } from '@/contexts/auth-context';
import Link from 'next/link';
import { redirect } from 'next/navigation';
import { ChangeEvent, FormEvent, useContext, useState } from 'react';
import toast from 'react-hot-toast';
import * as Tabs from '@radix-ui/react-tabs';

export default function Entrar() {
  const [userData, setUserData] = useState({
    email: '',
    password: '',
  });
  const [isLoading, setIsLoading] = useState(false);

  const { employeeSignIn, adminSignIn, isAuthenticaded } =
    useContext(AuthContext);

  if (isAuthenticaded) {
    redirect('/painel-de-controle');
  }

  function handleInputChange(event: ChangeEvent<HTMLInputElement>) {
    const { id, value } = event.target;

    setUserData({
      ...userData,
      [id]: value,
    });
  }

  async function handleEmployeeSignIn(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setIsLoading(true);

    const { email, password } = userData;

    if (!email || !password) {
      toast.error('Preencha todos os campos');
      setIsLoading(false);
      return;
    }

    try {
      await employeeSignIn({ email, password });
      setIsLoading(false);
    } catch (error: any) {
      console.log(error);
      toast.error(error.response.data.details);
      setIsLoading(false);
    } finally {
      setIsLoading(false);
    }
  }

  async function handleAdminSignIn(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setIsLoading(true);

    const { email, password } = userData;

    if (!email || !password) {
      toast.error('Preencha todos os campos');
      setIsLoading(false);
      return;
    }

    try {
      await adminSignIn({ email, password });
      setIsLoading(false);
    } catch (error: any) {
      console.log(error);
      toast.error(error.response.data.details);
      setIsLoading(false);
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className='container mx-auto'>
      <main className='flex'>
        <div className='flex-1 flex flex-col items-center mt-6'>
          <h1 className='text-3xl my-8 font-bold'>Entre em sua conta</h1>

          <Tabs.Root
            defaultValue='employee'
            onValueChange={() => setUserData({ email: '', password: '' })}
          >
            <Tabs.List className='flex justify-between gap-3 bg-zinc-100 p-2 rounded mb-4'>
              <Tabs.Trigger
                value='employee'
                className='data-[state=active]:bg-cyan-600 data-[state=active]:text-zinc-100 hover:bg-zinc-200 p-3 rounded flex-1'
              >
                Sou Funcionário
              </Tabs.Trigger>
              <Tabs.Trigger
                value='admin'
                className='data-[state=active]:bg-cyan-600 data-[state=active]:text-zinc-100 hover:bg-zinc-200 p-3 rounded flex-1'
              >
                Sou Administrador
              </Tabs.Trigger>
            </Tabs.List>

            <Tabs.Content value='employee'>
              <form
                className='flex flex-col gap-10'
                onSubmit={handleEmployeeSignIn}
              >
                <label htmlFor='email' className='flex flex-col gap-2'>
                  E-mail:
                  <input
                    type='email'
                    id='email'
                    className='p-4 rounded border border-neutral-300 w-[400px] bg-transparent'
                    placeholder='fulano@email.com'
                    value={userData.email}
                    onChange={handleInputChange}
                  />
                </label>

                <label htmlFor='password' className='flex flex-col gap-2'>
                  Senha:
                  <input
                    type='password'
                    id='password'
                    className='p-4 rounded border border-neutral-300 w-[400px] bg-transparent'
                    placeholder='************'
                    value={userData.password}
                    onChange={handleInputChange}
                  />
                </label>

                <button
                  disabled={isLoading}
                  type='submit'
                  className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors disabled:cursor-not-allowed disabled:bg-cyan-800/70 disabled:hover:bg-cyan-800/70'
                >
                  Entrar
                </button>
              </form>
            </Tabs.Content>

            <Tabs.Content value='admin'>
              <form
                className='flex flex-col gap-10'
                onSubmit={handleAdminSignIn}
              >
                <label htmlFor='email' className='flex flex-col gap-2'>
                  E-mail:
                  <input
                    type='email'
                    id='email'
                    className='p-4 rounded border border-neutral-300 w-[400px] bg-transparent'
                    placeholder='fulano@email.com'
                    value={userData.email}
                    onChange={handleInputChange}
                  />
                </label>

                <label htmlFor='password' className='flex flex-col gap-2'>
                  Senha:
                  <input
                    type='password'
                    id='password'
                    className='p-4 rounded border border-neutral-300 w-[400px] bg-transparent'
                    placeholder='************'
                    value={userData.password}
                    onChange={handleInputChange}
                  />
                </label>

                <button
                  disabled={isLoading}
                  type='submit'
                  className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors disabled:cursor-not-allowed disabled:bg-cyan-800/70 disabled:hover:bg-cyan-800/70'
                >
                  Entrar
                </button>
              </form>
              <p className='mt-4'>
                Ainda não possui uma conta?{' '}
                <Link
                  href='/criar-conta'
                  className='text-cyan-700 hover:underline'
                >
                  Crie aqui
                </Link>
              </p>
            </Tabs.Content>
          </Tabs.Root>
        </div>
      </main>
    </div>
  );
}
