'use client';

import { AuthContext } from '@/contexts/auth-context';
import { LayoutDashboard, LogOut } from 'lucide-react';
import Link from 'next/link';
import { useContext } from 'react';

export function Header() {
  const { isAuthenticaded, isAuthLoading, signOut } = useContext(AuthContext);

  return (
    <header className='w-full flex justify-between h-24 items-center shadow-sm px-4'>
      <div className='container flex justify-between items-center mx-auto'>
        <Link href='/'>
          <h2 className='text-2xl font-bold'>
            s<span className='text-cyan-700'>ONG</span>s
          </h2>
        </Link>

        {isAuthLoading ? (
          <div className='animate-spin rounded-full h-4 w-4 border-t-2 border-b-2 border-neutral-400'></div>
        ) : isAuthenticaded ? (
          <div className='flex gap-4 items-center'>
            <Link
              className='text-cyan-700 flex gap-2 items-center'
              href='/painel-de-controle'
            >
              <LayoutDashboard className='w-4 h-4' />
              Painel de Controle
            </Link>

            <button
              onClick={signOut}
              className='text-red-500 hover:text-red-600 flex gap-2 items-center'
            >
              Sair
              <LogOut className='w-4 h-4' />
            </button>
          </div>
        ) : (
          <Link href='/entrar' className='text-cyan-700'>
            Faço parte de uma ONG
          </Link>
        )}
      </div>
    </header>
  );
}
