'use client';

import { AuthContext } from '@/contexts/auth-context';
import { Building, Settings, User } from 'lucide-react';
import Link from 'next/link';
import { redirect } from 'next/navigation';
import { useContext } from 'react';

export default function PainelDeControle() {
  const { user, isAuthenticaded, isAuthLoading } = useContext(AuthContext);

  if (!isAuthLoading && !isAuthenticaded) {
    redirect('/entrar');
  }

  return (
    <div className='container mx-auto'>
      <main className='flex'>
        <div className='flex-1 flex flex-col items-center mt-6 gap-12'>
          <h1 className='text-3xl mt-8 font-bold'>
            Olá,{' '}
            <span className='text-cyan-700'>{user?.name.split(' ')[0]}</span> 👋
          </h1>

          <div className='flex flex-col w-full max-w-md px-4 gap-4'>
            <Link
              className='flex items-center justify-center gap-2 border border-cyan-700 p-4 w-full rounded text-cyan-700 font-bold hover:bg-cyan-800 hover:text-neutral-100 transition-colors'
              href='/ongs'
            >
              <Building className='w-6 h-6' />
              Gerenciar ONGs
            </Link>

            {/* <Link
              className='flex items-center justify-center gap-2 border border-cyan-700 p-4 w-full rounded text-cyan-700 font-bold hover:bg-cyan-800 hover:text-neutral-100 transition-colors'
              href='/usuarios'
            >
              <User className='w-6 h-6' />
              Gerenciar Usuários
            </Link> */}

            <Link
              className='flex items-center justify-center gap-2 border border-cyan-700 p-4 w-full rounded text-cyan-700 font-bold hover:bg-cyan-800 hover:text-neutral-100 transition-colors'
              href='/eu'
            >
              <Settings className='w-6 h-6' />
              Gerenciar sua conta
            </Link>
          </div>
        </div>
      </main>
    </div>
  );
}
