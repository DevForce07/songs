'use client';

import { AuthContext } from '@/contexts/auth-context';
import { api } from '@/lib/axios';
import { redirect, useParams, useRouter } from 'next/navigation';
import { ChangeEvent, useContext, useEffect, useState } from 'react';
import toast from 'react-hot-toast';

export default function EditarVaga() {
  const { isAuthLoading, isAuthenticaded } = useContext(AuthContext);

  if (!isAuthLoading && !isAuthenticaded) {
    redirect('/entrar');
  }

  const router = useRouter();

  const { id } = useParams();

  const [vacancyData, setVacancyData] = useState({
    title: '',
    description: '',
    qtdVacancies: undefined,
    ong: {
      id: '',
    },
  });

  useEffect(() => {
    async function getVancy() {
      const { data } = await api.get(`/vacancies/findById/${id}`);

      const { title, description, qtdVacancies, ong } = data;

      setVacancyData({
        title,
        description,
        qtdVacancies,
        ong,
      });
    }

    getVancy();
  }, []);

  async function handleUpdateVacancy(event: ChangeEvent<HTMLFormElement>) {
    event.preventDefault();

    const { title, description, qtdVacancies, ong } = vacancyData;

    try {
      const response = await api.put('/vacancies/update', {
        id,
        title,
        ong: ong.id,
        description,
        qtdVacancies,
      });

      if (response.status === 200) {
        router.push(`/ongs/${ong.id}`);
        toast.success('Vaga editada com sucesso!');
      }
    } catch (error) {
      console.log(error);
      toast.error('Erro ao editar vaga');
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

  return (
    <div className='max-w-3xl w-full mx-auto'>
      <h1 className='text-3xl my-8 font-bold'>Edite a vaga</h1>

      <main className='mb-8'>
        <form className='flex flex-col gap-10' onSubmit={handleUpdateVacancy}>
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

          <label htmlFor='qtdVacancies' className='flex flex-col gap-2'>
            Quantidade de vagas:
            <input
              id='qtdVacancies'
              type='number'
              className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              placeholder='Ex: 3'
              value={vacancyData.qtdVacancies}
              onChange={handleInputChange}
            />
          </label>

          <button
            type='submit'
            className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors'
          >
            Salvar
          </button>
        </form>
      </main>
    </div>
  );
}
