'use client';

import { VacancyCard } from '@/components/vacancy-card';
import { AuthContext } from '@/contexts/auth-context';
import { api } from '@/lib/axios';
import { Ong, Vacancy } from '@/types';
import { Eye, Frown, Phone, Plus } from 'lucide-react';
import Image from 'next/image';
import Link from 'next/link';
import { useParams, useRouter, redirect } from 'next/navigation';

import { useContext, useEffect, useState } from 'react';

export default function OngProfileAdmin() {
  const [ong, setOng] = useState<Ong | null>(null);
  const [vacancies, setVacancies] = useState<Vacancy[] | null>([]);
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

  async function getVacanciesByOng() {
    try {
      const response = await api.get(`/vacancies/findByIdOng/${id}`);

      setVacancies(response.data);
      setIsLoading(false);
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    getOng();
    getVacanciesByOng();
  }, []);

  return (
    <>
      {isLoading ? (
        <div className='flex justify-center items-center h-screen'>
          <div className='animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-neutral-400'></div>
        </div>
      ) : (
        <main className='max-w-4xl w-full mx-auto px-4'>
          <div className='flex flex-col w-full  px-4 pt-10 gap-4'>
            <Link
              className='flex items-center justify-center gap-2 border border-cyan-700 p-4 w-full rounded text-cyan-700 font-bold hover:bg-cyan-800 hover:text-neutral-100 transition-colors'
              href={`/ongs/${id}/funcionarios`}
            >
              <Eye className='w-6 h-6' />
              Visualziar funcionários
            </Link>

            <Link
              className='flex items-center justify-center gap-2 border border-cyan-700 p-4 w-full rounded text-cyan-700 font-bold hover:bg-cyan-800 hover:text-neutral-100 transition-colors'
              href={`/ongs/${id}/criar-vaga`}
            >
              <Plus className='w-6 h-6' />
              Criar nova vaga
            </Link>
          </div>

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

                <button className='p-4 bg-transparent border border-green-600 font-bold rounded text-green-700 flex items-center gap-2 hover:bg-green-600 hover:text-neutral-50 transition-colors justify-center w-full'>
                  <Phone size={26} />
                  Entre em contato
                </button>
              </div>
            </div>
          </div>

          <div className='my-10'>
            <h2 className='text-2xl font-bold text-cyan-700 mb-6'>
              Vagas disponíveis
            </h2>

            {isLoading ? (
              <div className='flex justify-center items-center mt-4'>
                <div className='animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-neutral-400'></div>
              </div>
            ) : vacancies && vacancies.length > 0 ? (
              <div className='grid grid-cols-1 md:grid-cols-2  gap-4'>
                {vacancies.map((vacancy) => (
                  <VacancyCard vacancy={vacancy} key={vacancy.id} />
                ))}
              </div>
            ) : (
              <h3 className='text-xl font-bold mb-2 text-center mt-20 text-neutral-300'>
                <Frown className='w-12 h-12 mx-auto mb-2' />
                Nenhuma vaga disponível
              </h3>
            )}
          </div>
        </main>
      )}
    </>
  );
}
