'use client';

import { AuthContext } from '@/contexts/auth-context';
import { api } from '@/lib/axios';
import { redirect, useRouter } from 'next/navigation';
import { ChangeEvent, useContext, useEffect, useState } from 'react';
import toast from 'react-hot-toast';
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

  // useEffect(() => {
  //   // window.location.reload();
  // }, [isAuthenticaded]);

  console.log('oi');

  const [userAdminData, setUserAdminData] = useState({
    name: user?.name,
    cpf: user?.cpf,
    email: user?.email,
    newPassword: '',
    confirmNewPassword: '',
  });
  const [userEmployeeData, setUserEmployeeData] = useState({
    name: user?.name,
    cpf: user?.cpf,
    email: user?.email,
    birthDate: String(user?.birthDate),
    newPassword: '',
    sex: user?.sex,
    confirmNewPassword: '',
  });

  const [isLoading, setIsLoading] = useState(false);

  if (!isAuthLoading && !isAuthenticaded) {
    redirect('/entrar');
  }

  const router = useRouter();

  function handleAdminInputChange(event: ChangeEvent<HTMLInputElement>) {
    const { id, value } = event.target;

    setUserAdminData({
      ...userAdminData,
      [id]: value,
    });
  }

  function handleEmployeeInputChange(event: ChangeEvent<HTMLInputElement>) {
    const { id, value } = event.target;

    setUserEmployeeData({
      ...userEmployeeData,
      [id]: value,
    });
  }

  async function handleAdminUpdate(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const { name, cpf, email } = userAdminData;

    try {
      setIsLoading(true);
      const response = await api.put('/admins/update', {
        id: user?.id,
        name,
        cpf,
        email,
      });

      if (response.status === 200) {
        toast.success('Conta editada com sucesso!');

        router.refresh();
        router.push('/painel-de-controle');
        setIsLoading(false);
      }
    } catch (error: any) {
      console.log(error);
      setIsLoading(false);
      toast.error(error.response.data.details);
    } finally {
      setIsLoading(false);
    }
  }

  async function handleAdminPasswordUpdate(
    event: React.FormEvent<HTMLFormElement>
  ) {
    event.preventDefault();

    const { newPassword, confirmNewPassword } = userAdminData;

    if (newPassword !== confirmNewPassword) {
      toast.error('As senhas não coincidem');
      return;
    }

    try {
      setIsLoading(true);
      const response = await api.patch('/admins/update/password', {
        password: newPassword,
      });

      if (response.status === 200) {
        toast.success('Senha editada com sucesso!');

        router.push('/painel-de-controle');
        setIsLoading(false);
      }
    } catch (error: any) {
      console.log(error);
      setIsLoading(false);
      toast.error(error.response.data.details);
    } finally {
      setIsLoading(false);
    }
  }

  async function handleEmployeeUpdate(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const { name, cpf, email, birthDate, sex } = userEmployeeData;

    try {
      setIsLoading(true);
      const response = await api.put('/employees/update', {
        id: user?.id,
        ongEmployeeId: user?.ongEmployeeId,
        name,
        cpf,
        email,
        birthDate: new Date(birthDate),
        sex,
      });

      if (response.status === 200) {
        toast.success('Conta editada com sucesso!');

        router.refresh();
        router.push('/painel-de-controle');
        setIsLoading(false);
      }
    } catch (error: any) {
      console.log(error);
      setIsLoading(false);
      toast.error(error.response.data.details);
    } finally {
      setIsLoading(false);
    }
  }

  async function handleEmployeePasswordUpdate(
    event: React.FormEvent<HTMLFormElement>
  ) {
    event.preventDefault();

    const { newPassword, confirmNewPassword } = userEmployeeData;

    if (newPassword !== confirmNewPassword) {
      toast.error('As senhas não coincidem');
      return;
    }

    try {
      setIsLoading(true);
      const response = await api.patch('/employees/update/password', {
        password: newPassword,
      });

      if (response.status === 200) {
        toast.success('Senha editada com sucesso!');

        router.push('/painel-de-controle');
        setIsLoading(false);
      }
    } catch (error: any) {
      console.log(error);
      setIsLoading(false);
      toast.error(error.response.data.details);
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className='max-w-3xl w-full mx-auto px-4'>
      <h1 className='text-3xl my-8 font-bold'>Edite seus dados</h1>

      <main className='mb-8'>
        {user?.admin ? (
          <>
            <form className='flex flex-col gap-10' onSubmit={handleAdminUpdate}>
              <label htmlFor='name' className='flex flex-col gap-2'>
                Seu nome completo:
                <input
                  id='name'
                  type='text'
                  className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                  placeholder='Fulano de Tal'
                  value={userAdminData.name}
                  onChange={handleAdminInputChange}
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
                  value={userAdminData.cpf}
                  onChange={handleAdminInputChange}
                />
              </label>

              <label htmlFor='email' className='flex flex-col gap-2'>
                E-mail:
                <input
                  id='email'
                  type='email'
                  className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                  placeholder='fulano@email.com'
                  value={userAdminData.email}
                  onChange={handleAdminInputChange}
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
              onSubmit={handleAdminPasswordUpdate}
            >
              <h2 className='text-xl mt-8 font-bold'>Edite sua senha</h2>

              <div className='flex gap-4'>
                <label
                  htmlFor='newPassword'
                  className='flex flex-col gap-2 flex-1'
                >
                  Nova senha:
                  <input
                    id='newPassword'
                    type='password'
                    className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                    placeholder='********'
                    value={userAdminData.newPassword}
                    onChange={handleAdminInputChange}
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
                    value={userAdminData.confirmNewPassword}
                    onChange={handleAdminInputChange}
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
          </>
        ) : (
          <>
            <form
              className='flex flex-col gap-10'
              onSubmit={handleEmployeeUpdate}
            >
              <label htmlFor='name' className='flex flex-col gap-2'>
                Seu nome completo:
                <input
                  id='name'
                  type='text'
                  className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                  placeholder='Fulano de Tal'
                  value={userEmployeeData.name}
                  onChange={handleEmployeeInputChange}
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
                  value={userEmployeeData.cpf}
                  onChange={handleEmployeeInputChange}
                />
              </label>

              <label htmlFor='email' className='flex flex-col gap-2'>
                E-mail:
                <input
                  id='email'
                  type='email'
                  className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                  placeholder='fulano@email.com'
                  value={userEmployeeData.email}
                  onChange={handleEmployeeInputChange}
                />
              </label>

              <label htmlFor='birthDate' className='flex flex-col gap-2'>
                Data de nascimento:
                <InputMask
                  id='birthDate'
                  type='text'
                  mask='99/99/9999'
                  maskChar={null}
                  className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                  placeholder='DD/MM/AAAA'
                  value={new Date(user?.birthDate!).toLocaleDateString(
                    'pt-BR',
                    {
                      timeZone: 'UTC',
                    }
                  )}
                  onChange={handleEmployeeInputChange}
                />
              </label>

              <label htmlFor='sex' className='flex flex-col gap-2'>
                Sexo:
                <input
                  id='sex'
                  type='text'
                  className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                  placeholder='F ou M'
                  value={userEmployeeData.sex}
                  onChange={handleEmployeeInputChange}
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
              onSubmit={handleEmployeePasswordUpdate}
            >
              <h2 className='text-xl mt-8 font-bold'>Edite sua senha</h2>

              <div className='flex gap-4'>
                <label
                  htmlFor='newPassword'
                  className='flex flex-col gap-2 flex-1'
                >
                  Nova senha:
                  <input
                    id='newPassword'
                    type='password'
                    className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                    placeholder='********'
                    value={userEmployeeData.newPassword}
                    onChange={handleEmployeeInputChange}
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
                    value={userEmployeeData.confirmNewPassword}
                    onChange={handleEmployeeInputChange}
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
          </>
        )}
      </main>
    </div>
  );
}
