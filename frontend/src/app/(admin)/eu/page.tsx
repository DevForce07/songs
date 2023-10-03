'use client';

import { AuthContext } from '@/contexts/auth-context';
import { api } from '@/lib/axios';
import { redirect, useRouter } from 'next/navigation';
import { ChangeEvent, useContext, useState } from 'react';
import InputMask from 'react-input-mask';

export default function Eu() {
  const { isAuthenticaded, user, isAuthLoading } = useContext(AuthContext);

  if (isAuthLoading) {
    return (
      <div className='flex justify-center items-center -mt-24 h-screen'>
        <div className='animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-neutral-400'></div>
      </div>
    );
  }

  const [userData, setUserData] = useState({
    name: user?.name,
    cpf: user?.cpf,
    email: user?.email,
    newPassword: '',
    confirmNewPassword: '',
  });

  const [isLoading, setIsLoading] = useState(false);

  if (!isAuthLoading && !isAuthenticaded) {
    redirect('/entrar');
  }

  const router = useRouter();

  function handleInputChange(event: ChangeEvent<HTMLInputElement>) {
    const { id, value } = event.target;

    setUserData({
      ...userData,
      [id]: value,
    });
  }

  async function handleDataFormSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const { name, cpf, email } = userData;

    try {
      setIsLoading(true);
      const response = await api.put('/admins/update', {
        id: user?.id,
        name,
        cpf,
        email,
      });

      if (response.status === 200) {
        alert('Conta editada com sucesso!');

        router.refresh();
        router.push('/painel-de-controle');
        setIsLoading(false);
      }
    } catch (error: any) {
      console.log(error);
      setIsLoading(false);
      alert(error.response.data.details);
    } finally {
      setIsLoading(false);
    }
  }

  async function handlePasswordFormSubmit(
    event: React.FormEvent<HTMLFormElement>
  ) {
    event.preventDefault();

    const { newPassword, confirmNewPassword } = userData;

    if (newPassword !== confirmNewPassword) {
      alert('As senhas não coincidem');
      return;
    }

    try {
      setIsLoading(true);
      const response = await api.patch('/admins/update/password', {
        password: newPassword,
      });

      if (response.status === 200) {
        alert('Senha editada com sucesso!');

        router.push('/painel-de-controle');
        setIsLoading(false);
      }
    } catch (error: any) {
      console.log(error);
      setIsLoading(false);
      alert(error.response.data.details);
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className='max-w-3xl w-full mx-auto px-4'>
      <h1 className='text-3xl my-8 font-bold'>Edite seus dados</h1>

      <main className='mb-8'>
        <form className='flex flex-col gap-10' onSubmit={handleDataFormSubmit}>
          <label htmlFor='name' className='flex flex-col gap-2'>
            Seu nome completo:
            <input
              id='name'
              type='text'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='Fulano de Tal'
              value={userData.name}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='cpf' className='flex flex-col gap-2'>
            CPF:
            <InputMask
              id='cpf'
              type='text'
              mask='999.999.999-99'
              maskChar={null}
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='xxx.xxx.xxx-xx'
              value={userData.cpf}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='email' className='flex flex-col gap-2'>
            E-mail:
            <input
              id='email'
              type='email'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='fulano@email.com'
              value={userData.email}
              onChange={handleInputChange}
            />
          </label>

          <button
            disabled={isLoading}
            type='submit'
            className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors disabled:cursor-not-allowed disabled:bg-cyan-800/70 disabled:hover:bg-cyan-800/70'
          >
            Salvar
          </button>
          <hr />
        </form>

        <form
          className='flex flex-col gap-10'
          onSubmit={handlePasswordFormSubmit}
        >
          <h2 className='text-xl mt-8 font-bold'>Edite sua senha</h2>

          <div className='flex gap-4'>
            <label htmlFor='newPassword' className='flex flex-col gap-2 flex-1'>
              Nova senha:
              <input
                id='newPassword'
                type='password'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='********'
                value={userData.newPassword}
                onChange={handleInputChange}
              />
            </label>

            <label
              htmlFor='confirmNewPassword'
              className='flex flex-col gap-2 flex-1'
            >
              Confirme a nova senha:
              <input
                id='confirmNewPassword'
                type='password'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='********'
                value={userData.confirmNewPassword}
                onChange={handleInputChange}
              />
            </label>
          </div>

          <button
            disabled={isLoading}
            type='submit'
            className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors disabled:cursor-not-allowed disabled:bg-cyan-800/70 disabled:hover:bg-cyan-800/70'
          >
            Salvar
          </button>
        </form>
      </main>
    </div>
  );
}
