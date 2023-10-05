'use client';

import { AuthContext } from '@/contexts/auth-context';
import { api } from '@/lib/axios';
import { Ong } from '@/types';
import { Eye, Frown, Pencil, Plus, Trash } from 'lucide-react';
import Link from 'next/link';
import { redirect } from 'next/navigation';
import { useContext, useEffect, useState } from 'react';
import toast from 'react-hot-toast';

export default function Ongs() {
  const { isAuthenticaded, user, isAuthLoading } = useContext(AuthContext);
  const [ongs, setOngs] = useState<Ong[]>([]);
  // const [ong, setOng] = useState<Ong>();
  const [isLoading, setIsLoading] = useState(true);

  if (!isAuthLoading && !isAuthenticaded) {
    redirect('/entrar');
  }
  useEffect(() => {
    setOngs(user?.ongs! || []);
    getOngsByEmployeeId();

    setIsLoading(false);
  }, [ongs]);

  // if (user?.admin === true) {
  //   // useEffect(() => {
  //   setOngs(user?.ongs! || []);
  //   // getOngsByEmployeeId();

  //   setIsLoading(false);
  //   // }, [ongs]);
  // } else {
  //   // useEffect(() => {
  //   getOngByEmployeeId();
  //   // }, [ongs]);
  // }

  // async function getOngByEmployeeId() {
  //   try {
  //     const response = await api.get(`/ongs/findById/${user?.ongEmployeeId}`);

  //     setOng(response.data);
  //     setIsLoading(false);
  //   } catch (error) {
  //     console.log(error);
  //   }
  // }

  async function handleDeleteOng(id: number) {
    const response = await api.delete(`/ongs/deleteById/${id}`);

    if (response.status === 200) {
      toast.success('ONG deletada com sucesso!');
      window.location.reload();
    }
  }

  return (
    <div className='container mx-auto'>
      <main className='flex'>
        <div className='flex-1 flex flex-col items-center mt-6 gap-12'>
          <h1 className='text-3xl mt-8 font-bold'>Gerenciar ONGs</h1>

          {user?.admin && (
            <div className='flex flex-col w-full max-w-md px-4 gap-4'>
              <Link
                className='flex items-center justify-center gap-2 border border-cyan-700 p-4 w-full rounded text-cyan-700 font-bold hover:bg-cyan-800 hover:text-neutral-100 transition-colors'
                href='/ongs/criar'
              >
                <Plus className='w-6 h-6' />
                Criar nova ONG
              </Link>
            </div>
          )}

          {isLoading ? (
            <div className='flex justify-center items-center mt-4'>
              <div className='animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-neutral-400'></div>
            </div>
          ) : user?.admin === true && ongs.length > 0 ? (
            <div className='grid grid-cols-1 md:grid-cols-2 gap-4 px-4 w-full max-w-4xl pb-10'>
              {ongs.map((ong) => (
                <div
                  key={ong.id}
                  className='rounded-lg p-4 border border-neutral-300'
                >
                  <h3 className='text-xl font-bold mb-2'>{ong.name}</h3>
                  <p className='text-lg mb-4'>{ong.description || '⠀'}</p>

                  <div className='flex flex-col lg:flex-row justify-between gap-4 lg:gap-0'>
                    <Link
                      href={`/ongs/${ong.id}`}
                      className='p-4 py-2 bg-cyan-600 font-bold rounded text-neutral-50 flex items-center justify-center lg:justify-start gap-2 hover:bg-cyan-700 transition-colors'
                    >
                      <Eye className='w-4 h-4' />
                      Ver detalhes
                    </Link>

                    <Link
                      href={`/ongs/${ong.id}/editar`}
                      className='p-4 py-2 bg-emerald-600 font-bold rounded text-neutral-50 flex items-center justify-center lg:justify-start gap-2 hover:bg-emerald-700 transition-colors'
                    >
                      <Pencil className='w-4 h-4' />
                      Editar
                    </Link>

                    <button
                      onClick={() =>
                        confirm('Tem certeza que deseja deletar essa ONG?') &&
                        handleDeleteOng(ong.id)
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
          ) : ongs ? (
            <div className='grid grid-cols-1 md:grid-cols-2 gap-4 px-4 w-full max-w-4xl pb-10'>
              <div
                key={ong?.id}
                className='rounded-lg p-4 border border-neutral-300'
              >
                <h3 className='text-xl font-bold mb-2'>{ong?.name}</h3>
                <p className='text-lg mb-4'>{ong?.description || '⠀'}</p>

                <div className='flex flex-col lg:flex-row justify-between gap-4 lg:gap-0'>
                  <Link
                    href={`/ongs/${ong?.id}`}
                    className='p-4 py-2 bg-cyan-600 font-bold rounded text-neutral-50 flex items-center justify-center lg:justify-start gap-2 hover:bg-cyan-700 transition-colors'
                  >
                    <Eye className='w-4 h-4' />
                    Ver detalhes
                  </Link>

                  <Link
                    href={`/ongs/${ong?.id}/editar`}
                    className='p-4 py-2 bg-emerald-600 font-bold rounded text-neutral-50 flex items-center justify-center lg:justify-start gap-2 hover:bg-emerald-700 transition-colors'
                  >
                    <Pencil className='w-4 h-4' />
                    Editar
                  </Link>

                  <button
                    // onClick={() =>
                    //   confirm('Tem certeza que deseja deletar essa ONG?') &&
                    //   handleDeleteOng(ong?.id)
                    // }
                    className='p-4 py-2 bg-red-600 font-bold rounded text-neutral-50 flex items-center justify-center lg:justify-start gap-2 hover:bg-red-700 transition-colors'
                  >
                    <Trash className='w-4 h-4' />
                    Deletar
                  </button>
                </div>
              </div>
            </div>
          ) : (
            <h3 className='text-xl font-bold mb-2 text-center mt-20 text-neutral-300'>
              <Frown className='w-12 h-12 mx-auto mb-2' />
              Nenhuma ONG encontrada
            </h3>
          )}
          {/* </div> */}
        </div>
      </main>
    </div>
  );
}
