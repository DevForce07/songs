'use client';

import { AuthContext } from '@/contexts/auth-context';
import { api } from '@/lib/axios';
import { redirect, useParams, useRouter } from 'next/navigation';
import { ChangeEvent, FormEvent, useContext, useState } from 'react';
import toast from 'react-hot-toast';
import InputMask from 'react-input-mask';

export default function CriarFuncionario() {
  const [employeeData, setEmployeeData] = useState({
    name: '',
    email: '',
    cpf: '',
    birthDate: '',
    sex: '',
    password: '',
    confirmPassword: '',
  });

  const [isLoading, setIsLoading] = useState(false);

  const { id } = useParams();
  const router = useRouter();

  const { isAuthenticaded, isAuthLoading } = useContext(AuthContext);

  if (!isAuthLoading && !isAuthenticaded) {
    redirect('/entrar');
  }

  function handleInputChange(event: ChangeEvent<HTMLInputElement>) {
    const { id, value } = event.target;

    setEmployeeData({
      ...employeeData,
      [id]: value,
    });
  }

  async function handleFormSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const { name, cpf, email, birthDate, sex, password, confirmPassword } =
      employeeData;

    if (password !== confirmPassword) {
      toast.error('As senhas não coincidem');
      return;
    }

    try {
      setIsLoading(true);
      const response = await api.post('/employees/create', {
        ongEmployeeId: id,
        name,
        cpf,
        email,
        password,
        birthDate: new Date(birthDate),
        sex,
      });

      if (response.status === 201) {
        toast.success('Conta criada com sucesso!');

        router.push(`/ongs/${id}/funcionarios`);
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
    <div className='max-w-3xl w-full mx-auto'>
      <h1 className='text-3xl my-8 font-bold'>Crie a conta do funcionário</h1>

      <main className='mb-8'>
        <form className='flex flex-col gap-10' onSubmit={handleFormSubmit}>
          <label htmlFor='name' className='flex flex-col gap-2'>
            Nome completo:
            <input
              id='name'
              type='text'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='Fulano de Tal'
              value={employeeData.name}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='email' className='flex flex-col gap-2'>
            E-mail:
            <input
              id='email'
              type='email'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='nome@email.com'
              value={employeeData.email}
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
              value={employeeData.cpf}
              onChange={handleInputChange}
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
              value={employeeData.birthDate}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='sex' className='flex flex-col gap-2'>
            Sexo:
            <input
              id='sex'
              type='text'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='F ou M'
              value={employeeData.sex}
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
              value={employeeData.password}
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
              value={employeeData.confirmPassword}
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
