'use client';

import { AuthContext } from '@/contexts/auth-context';
import { api } from '@/lib/axios';
import { Ong, User, Vacancy } from '@/types';
import { Eye, Frown, Pencil, Phone, Plus, Trash } from 'lucide-react';
import Link from 'next/link';
import { useParams, useRouter, redirect } from 'next/navigation';

import { useContext, useEffect, useState } from 'react';
import toast from 'react-hot-toast';

export default function Funcionarios() {
  const [ong, setOng] = useState<Ong | null>(null);
  const [employees, setEmployees] = useState<User[] | null>([]);
  const [isLoading, setIsLoading] = useState(true);

  const { isAuthLoading, isAuthenticaded } = useContext(AuthContext);

  const router = useRouter();

  const { id } = useParams();

  if (!isAuthLoading && !isAuthenticaded) {
    redirect('/entrar');
  }

  async function getOng() {
    try {
      const response = await api.get(`/ongs/findById/${id}`);

      setOng(response.data);
      setIsLoading(false);
    } catch (error) {
      console.log(error);
      router.push('/');
    }
  }

  async function getEmployeesByOng() {
    try {
      const response = await api.get(`/employees/findAllByIdOng/${id}`);

      setEmployees(response.data);
      setIsLoading(false);
    } catch (error) {
      console.log(error);
    }
  }

  async function handleDeleteEmployee(id: number) {
    const response = await api.delete(`/employees/delete/byId/${id}`);

    if (response.status === 200) {
      toast.success('Funcionário deletado com sucesso!');
      window.location.reload();
    }
  }

  useEffect(() => {
    getOng();
    getEmployeesByOng();
  }, []);

  return (
    <>
      {isLoading ? (
        <div className='flex justify-center items-center h-screen'>
          <div className='animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-neutral-400'></div>
        </div>
      ) : (
        <main className='max-w-4xl w-full mx-auto px-4'>
          <div className='mt-10'>
            <h1 className='text-3xl font-bold mb-6 text-cyan-700'>
              {ong?.name}
            </h1>

            <div className='flex flex-col md:flex-row gap-6'>
              <p className='flex-1 text-lg leading-relaxed'>
                {ong?.description}
              </p>

              <div className='md:max-w-xs'>
                <h2 className='text-xl font-bold text-cyan-700'>Endereço</h2>
                <p className='text-lg mt-2 mb-6'>{ong?.address}</p>
              </div>
            </div>
          </div>

          <div>
            <div className='flex sm:items-center w-full py-10 gap-4 justify-between flex-col sm:flex-row items-start'>
              <h2 className='text-2xl font-bold text-cyan-700'>Funcionários</h2>

              <Link
                className='flex items-center justify-center gap-2 border sm:max-w-xs border-cyan-700 p-4 w-full rounded text-cyan-700 font-bold hover:bg-cyan-800 hover:text-neutral-100 transition-colors'
                href={`/ongs/${id}/funcionarios/criar`}
              >
                <Plus className='w-6 h-6' />
                Criar novo funcionário
              </Link>
            </div>

            {isLoading ? (
              <div className='flex justify-center items-center mt-4'>
                <div className='animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-neutral-400'></div>
              </div>
            ) : employees && employees.length > 0 ? (
              <div className='grid grid-cols-1 md:grid-cols-2 gap-4 pb-10'>
                {employees.map((employee) => (
                  <div
                    className='rounded-lg p-4 border border-neutral-300'
                    key={employee.id}
                  >
                    <h3
                      className='text-xl font-bold mb-2'
                      title={employee.name}
                    >
                      {employee.name.length >= 80
                        ? employee.name.substring(0, 80).concat('...')
                        : employee.name}
                    </h3>

                    <div className='flex flex-col gap-2 mb-4'>
                      <p>
                        <span
                          className='
                        font-bold texcya7'
                        >
                          E-mail
                        </span>
                        : {employee.email}
                      </p>
                      <p>
                        <span
                          className='
                        font-bold texcya7'
                        >
                          CPF:
                        </span>{' '}
                        {employee.cpf}
                      </p>
                      <p>
                        <span
                          className='
                        font-bold texcya7'
                        >
                          Nascimento:{' '}
                        </span>
                        {new Date(employee.birthDate!).toLocaleDateString()}
                      </p>
                    </div>

                    <div className='flex flex-col lg:flex-row justify-between gap-4 lg:gap-0'>
                      <button
                        onClick={() =>
                          confirm(
                            'Tem certeza que deseja deletar esse funcionário?'
                          ) && handleDeleteEmployee(employee.id)
                        }
                        className='p-4 py-2 bg-red-600 font-bold rounded text-neutral-50 flex items-center justify-center lg:justify-start gap-2 hover:bg-red-700 transition-colors'
                      >
                        <Trash className='w-4 h-4' />
                        Deletar
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <h3 className='text-xl font-bold mb-2 text-center mt-20 text-neutral-300'>
                <Frown className='w-12 h-12 mx-auto mb-2' />
                Nenhum funcionário encontrado
              </h3>
            )}
          </div>
        </main>
      )}
    </>
  );
}
