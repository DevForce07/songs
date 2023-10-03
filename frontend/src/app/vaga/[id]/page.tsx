'use client';

import { api } from '@/lib/axios';
import { Vacancy } from '@/types';
import { Phone } from 'lucide-react';
import Image from 'next/image';
import Link from 'next/link';
import { useParams } from 'next/navigation';

import volunteerImg from 'public/voluntarios.jpg';
import { useEffect, useState } from 'react';

export default function Vaga() {
  const [vacancy, setVacancy] = useState<Vacancy | null>(null);

  const { id } = useParams();

  async function getVacancy() {
    try {
      const response = await api.get(`/vacancies/findById/${id}`);

      setVacancy(response.data);
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    getVacancy();
  }, []);

  return (
    <main className='max-w-4xl w-full mx-auto px-4 flex flex-col gap-10'>
      <div className='mt-10'>
        <div className='flex flex-col gap-2 mb-8'>
          <h1 className='text-3xl font-bold text-cyan-700'>{vacancy?.title}</h1>
          <div className='flex justify-between'>
            <p>
              Por:{' '}
              <Link
                href={`/ong/${vacancy?.ong.id}`}
                className='text-cyan-700 hover:underline'
              >
                {vacancy?.ong.name}
              </Link>
            </p>

            <div className='flex gap-2 text-neutral-400 text-sm'>
              <p>
                Criado: {new Date(vacancy?.dateCreated!).toLocaleDateString()}
              </p>
              |
              <p>
                Ultima atualização:{' '}
                {new Date(vacancy?.lastUpdate!).toLocaleDateString()}
              </p>
            </div>
          </div>
        </div>

        <div className='flex flex-col md:flex-row gap-6'>
          <p className='flex-1 text-lg leading-relaxed'>
            {vacancy?.description}
          </p>

          <div className='md:max-w-xs flex-1'>
            <h2 className='text-xl font-bold text-cyan-700'>Endereço</h2>
            <p className='text-lg mt-2 mb-6'>{vacancy?.ong.address}</p>

            <button className='p-4 bg-transparent border border-green-600 font-bold rounded text-green-700 flex items-center gap-2 hover:bg-green-600 hover:text-neutral-50 transition-colors justify-center w-full'>
              <Phone size={26} />
              Entre em contato
            </button>
          </div>
        </div>
      </div>

      <div>
        <h2 className='text-xl font-bold text-cyan-700'>
          Vagas disponíveis:{' '}
          <span className='text-neutral-800'>{vacancy?.qtdVacancies}</span>
        </h2>
      </div>

      {/* <div className='my-10'>
        <h2 className='text-2xl font-bold text-cyan-700 mb-6'>
          Vagas disponíveis
        </h2>

        <div className='grid grid-cols-1 md:grid-cols-2  gap-4'>
          <div className='rounded-lg p-4 border border-neutral-300'>
            <h3 className='text-xl font-bold mb-2'>
              Cuidador de animais asquerosos e peçonhentos
            </h3>
            <p className='text-lg mb-4'>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Quis
              voluptatum, quibusdam...
            </p>

            <button className='p-4 py-2 bg-cyan-600 font-bold rounded text-neutral-50 flex items-center gap-2 hover:bg-cyan-700 transition-colors'>
              Ver detalhes
            </button>
          </div>

          <div className='rounded-lg p-4 border border-neutral-300'>
            <h3 className='text-xl font-bold mb-2'>
              Cuidador de animais asquerosos e peçonhentos
            </h3>
            <p className='text-lg mb-4'>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Quis
              voluptatum, quibusdam...
            </p>

            <button className='p-4 py-2 bg-cyan-600 font-bold rounded text-neutral-50 flex items-center gap-2 hover:bg-cyan-700 transition-colors'>
              Ver detalhes
            </button>
          </div>

          <div className='rounded-lg p-4 border border-neutral-300'>
            <h3 className='text-xl font-bold mb-2'>
              Cuidador de animais asquerosos e peçonhentos
            </h3>
            <p className='text-lg mb-4'>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Quis
              voluptatum, quibusdam...
            </p>

            <button className='p-4 py-2 bg-cyan-600 font-bold rounded text-neutral-50 flex items-center gap-2 hover:bg-cyan-700 transition-colors'>
              Ver detalhes
            </button>
          </div>
        </div>
      </div> */}
    </main>
  );
}
