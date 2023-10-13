'use client';

import { api } from '@/lib/axios';
import { Application, Vacancy } from '@/types';
import axios from 'axios';
import { Phone } from 'lucide-react';
import Image from 'next/image';
import Link from 'next/link';
import { useParams } from 'next/navigation';

import volunteerImg from 'public/voluntarios.jpg';
import { ChangeEvent, useEffect, useState } from 'react';
import toast from 'react-hot-toast';

export default function Vaga() {
  const [vacancy, setVacancy] = useState<Vacancy | null>(null);
  const [application, setApplication] = useState<Application>({
    name: '',
    email: '',
    message: '',
  });
  const [isLoading, setIsLoading] = useState(false);

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

  async function handleApplicationSubmit(
    event: React.FormEvent<HTMLFormElement>
  ) {
    event.preventDefault();

    if (
      application.name === '' ||
      application.email === '' ||
      application.message === ''
    ) {
      toast.error('Preencha todos os campos!');
      return;
    }

    setIsLoading(true);

    try {
      const response = await axios.post(
        `http://localhost:3000/api/vacancies/apply`,
        {
          application,
          vacancy,
        }
      );

      if (response.status === 200) {
        setIsLoading(false);
        toast.success('Candidatura enviada com sucesso!');
        setApplication({
          name: '',
          email: '',
          message: '',
        });
      } else {
        toast.error('Erro ao enviar candidatura!');
        setIsLoading(false);
      }
    } catch (error) {
      console.log(error);
      setIsLoading(false);
    }
  }

  function handleInputChange(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) {
    const { id, value } = event.target;

    setApplication({
      ...application,
      [id]: value,
    });
  }

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

            <Link
              href={`https://api.whatsapp.com/send/?phone=${vacancy?.ong.phoneNumber}&text=Olá, vim do sONGs!&type=phone_number&app_absent=0`}
              target='_blank'
              className='p-4 bg-transparent border border-green-600 font-bold rounded text-green-700 flex items-center gap-2 hover:bg-green-600 hover:text-neutral-50 transition-colors justify-center w-full'
            >
              <Phone size={26} />
              Entre em contato
            </Link>
          </div>
        </div>
      </div>

      <div>
        <h2 className='text-xl font-bold text-cyan-700'>
          Vagas disponíveis:{' '}
          <span className='text-neutral-800'>{vacancy?.qtdVacancies}</span>
        </h2>
      </div>

      <div className='mb-10'>
        <h2 className='text-2xl font-bold text-cyan-700 mb-6'>Candidate-se</h2>

        <div className='w-full'>
          <form
            className='flex flex-col gap-8'
            onSubmit={handleApplicationSubmit}
          >
            <div className='flex flex-col gap-8 md:flex-row'>
              <label htmlFor='name' className='flex flex-col gap-2 w-full'>
                Nome:
                <input
                  type='text'
                  id='name'
                  className='p-4 rounded border border-neutral-300 w-full bg-transparent'
                  placeholder='************'
                  value={application.name}
                  onChange={handleInputChange}
                />
              </label>

              <label htmlFor='email' className='flex flex-col gap-2 w-full'>
                E-mail:
                <input
                  type='email'
                  id='email'
                  className='p-4 rounded border border-neutral-300 w-full bg-transparent'
                  placeholder='fulano@email.com'
                  value={application.email}
                  onChange={handleInputChange}
                />
              </label>
            </div>

            <label htmlFor='message' className='flex flex-col gap-2 w-full'>
              Mensagem:
              <textarea
                id='message'
                className='p-4 rounded border border-neutral-300 w-full bg-transparent'
                placeholder='Sua mensagem aqui...'
                value={application.message}
                onChange={handleInputChange}
              ></textarea>
            </label>

            <button
              disabled={isLoading}
              type='submit'
              className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors disabled:cursor-not-allowed disabled:bg-cyan-800/70 disabled:hover:bg-cyan-800/70'
            >
              Enviar
            </button>
          </form>
        </div>
      </div>
    </main>
  );
}
