'use client';

import { api } from '@/lib/axios';
import { LogSystem, User } from '@/types';
import { useParams } from 'next/navigation';
import { useEffect, useState } from 'react';

const logSystemEnum = {
  LOGIN: 'Login',
  CREATE_ONG: 'Criação de ONG',
  CREATE_VACANCIES: 'Criação de Vaga',
  CREATE_EMPLOYEES: 'Criação de Funcionário',
  UPDATE_ADMIN: 'Atualização de Administrador',
  UPDATE_ONG: 'Atualização de ONG',
  UPDATE_VACANCIES: 'Atualização de Vaga',
  UPDATE_EMPLOYEES: 'Atualização de Funcionário',
  DELETE_ADMIN: 'Exclusão de Administrador',
  DELETE_ONG: 'Exclusão de ONG',
  DELETE_VACANCIES: 'Exclusão de Vaga',
  DELETE_EMPLOYEES: 'Exclusão de Funcionário',
};

export default function Auditoria() {
  const [audits, setAudits] = useState<LogSystem[]>([]);

  const { id } = useParams();

  async function getAudits() {
    try {
      const response = await api.get(`/audit/findByOng/${id}`);

      setAudits(response.data);
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    getAudits();
  }, []);

  return (
    <main className='max-w-4xl w-full mx-auto px-4 flex flex-col gap-10'>
      <h1 className='text-3xl font-bold mt-10 text-cyan-700'>
        Registro de Auditoria
      </h1>
      <div className='flex flex-col gap-4 max-h-[500px] overflow-y-scroll mb-6 border p-4 rounded-lg'>
        {audits.map((audit) => (
          <div className='flex flex-col gap-2 border p-4 rounded-lg bg-neutral-100'>
            <span className='text-gray-500 text-sm'>
              {new Date(audit.dateTime).toLocaleDateString('pt-BR', {
                day: 'numeric',
                month: 'long',
                year: 'numeric',
              })}
            </span>

            <div className='flex gap-1 flex-col'>
              <p className='font-bold text-cyan-700'>
                {logSystemEnum[audit.logSystem as keyof typeof logSystemEnum]}
              </p>
              <span className='text-sm text-neutral-500'>
                por: {audit.userName}
              </span>
            </div>
          </div>
        ))}
      </div>
    </main>
  );
}
