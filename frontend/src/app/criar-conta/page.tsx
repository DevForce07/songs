'use client';

import { AuthContext } from '@/contexts/auth-context';
import { api } from '@/lib/axios';
import { redirect, useRouter } from 'next/navigation';
import { ChangeEvent, FormEvent, useContext, useState } from 'react';
import InputMask from 'react-input-mask';

export default function CriarConta() {
  const [userData, setUserData] = useState({
    name: '',
    cpf: '',
    email: '',
    password: '',
    confirmPassword: '',
  });
  const [isLoading, setIsLoading] = useState(false);

  const router = useRouter();

  const { isAuthenticaded } = useContext(AuthContext);

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

  async function handleFormSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const { name, cpf, email, password, confirmPassword } = userData;

    if (password !== confirmPassword) {
      alert('As senhas não coincidem');
      return;
    }

    try {
      setIsLoading(true);
      const response = await api.post('/admins/create', {
        name,
        cpf,
        email,
        password,
      });

      if (response.status === 201) {
        alert('Conta criada com sucesso!');

        router.push('/entrar');
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
      <h1 className='text-3xl my-8 font-bold'>Crie a sua conta</h1>

      <main className='mb-8'>
        <form className='flex flex-col gap-10' onSubmit={handleFormSubmit}>
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

          <label htmlFor='password' className='flex flex-col gap-2'>
            Escolha uma senha:
            <input
              id='password'
              type='password'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='********'
              value={userData.password}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='confirmPassword' className='flex flex-col gap-2'>
            Confirme a senha:
            <input
              id='confirmPassword'
              type='password'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='********'
              value={userData.confirmPassword}
              onChange={handleInputChange}
            />
          </label>

          <button
            disabled={isLoading}
            type='submit'
            className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors disabled:cursor-not-allowed disabled:bg-cyan-800/70 disabled:hover:bg-cyan-800/70'
          >
            Criar
          </button>
        </form>
      </main>
    </div>
  );
}
