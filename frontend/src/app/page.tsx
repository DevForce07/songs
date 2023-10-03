'use client';

import Image from 'next/image';
import { Frown, Search } from 'lucide-react';
import volunteerImg from 'public/voluntarios2.jpg';
import { FormEvent, useEffect, useState } from 'react';
import { Ong, Vacancy } from '@/types';
import { api } from '@/lib/axios';
import Link from 'next/link';
import { VacancyCard } from '@/components/vacancy-card';

export default function Home() {
  const [vacancies, setVacancies] = useState<Vacancy[]>([]);
  const [search, setSearch] = useState('');
  const [isLoading, setIsLoading] = useState(true);

  async function getVacancies() {
    try {
      const response = await api.get('/vacancies/findAll');

      setVacancies(response.data);
      setIsLoading(false);
    } catch (error) {
      console.log(error);
      setIsLoading(false);
    }
  }

  useEffect(() => {
    getVacancies();
  }, []);

  async function searchVacancies(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    if (!search) return alert('Digite algo para pesquisar');

    const response = await api.get(`/vacancies/findByTitle/${search}`);

    setVacancies(response.data);
  }

  return (
    <main className='max-w-5xl w-full mx-auto px-4'>
      <div className='h-80'>
        <Image
          src={volunteerImg}
          placeholder='blur'
          alt='Voluntários'
          className='w-full h-full object-cover object-center rounded-lg'
        />
      </div>

      <form className='mt-5 flex gap-4' onSubmit={searchVacancies}>
        <input
          id='search'
          type='search'
          className='p-4 rounded border border-neutral-300 bg-transparent w-full'
          placeholder='Cuidador de animais asquerosos e peçonhentos'
          value={search}
          onChange={(event) => setSearch(event.target.value)}
          onInput={(event) => {
            if (!event.currentTarget.value) getVacancies();
          }}
        />
        <button className='bg-cyan-700 text-neutral-50 px-8 rounded hover:bg-cyan-600 transition-colors'>
          <Search className='w-5 h-5' />
        </button>
      </form>

      <div className='my-10'>
        <h2 className='text-2xl font-bold text-cyan-700 mb-6'>
          Vagas disponíveis
        </h2>

        {isLoading ? (
          <div className='flex justify-center items-center mt-4'>
            <div className='animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-neutral-400'></div>
          </div>
        ) : vacancies.length > 0 ? (
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
  );
}
