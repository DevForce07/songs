'use client';

import { AuthContext } from '@/contexts/auth-context';
import { api } from '@/lib/axios';
import { redirect, useParams, useRouter } from 'next/navigation';
import { ChangeEvent, useContext, useState } from 'react';
import toast from 'react-hot-toast';

export default function CriarVaga() {
  const { isAuthLoading, isAuthenticaded } = useContext(AuthContext);
  const [vacancyData, setVacancyData] = useState({
    title: '',
    description: '',
    qtdVacacies: undefined,
  });

  const router = useRouter();

  const { id } = useParams();

  async function handleCreateVacancy(event: ChangeEvent<HTMLFormElement>) {
    event.preventDefault();

    const { title, description, qtdVacacies } = vacancyData;
    try {
      const response = await api.post('/vacancies/create', {
        ong: id,
        title,
        description,
        qtdVacacies,
      });

      if (response.status === 201) {
        router.push(`/ongs/${id}`);
        toast.success('Vaga criada com sucesso!');
      }
    } catch (error) {
      console.log(error);
      toast.error('Erro ao criar vaga');
    }
  }

  function handleInputChange(
    event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) {
    const { id, value } = event.target;

    setVacancyData({
      ...vacancyData,
      [id]: value,
    });
  }

  // console.log(id);

  if (!isAuthLoading && !isAuthenticaded) {
    redirect('/entrar');
  }

  return (
    <div className='max-w-3xl w-full mx-auto'>
      <h1 className='text-3xl my-8 font-bold'>Crie a vaga</h1>

      <main className='mb-8'>
        <form className='flex flex-col gap-10' onSubmit={handleCreateVacancy}>
          <label htmlFor='title' className='flex flex-col gap-2'>
            Título da vaga:
            <input
              id='title'
              type='text'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='Ex: Cuidador de animais'
              value={vacancyData.title}
              onChange={handleInputChange}
            />
          </label>

          <label htmlFor='description' className='flex flex-col gap-2'>
            Descrição:
            <textarea
              id='description'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full resize-none h-32'
              placeholder='Descreva aqui tudo sobre a vaga'
              value={vacancyData.description}
              onChange={handleInputChange}
            ></textarea>
          </label>

          <label htmlFor='qtdVacacies' className='flex flex-col gap-2'>
            Quantidade de vagas:
            <input
              id='qtdVacacies'
              type='number'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='Ex: 3'
              value={vacancyData.qtdVacacies}
              onChange={handleInputChange}
            />
          </label>

          <button
            type='submit'
            className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors'
          >
            Criar
          </button>
        </form>
      </main>
    </div>
  );
}
