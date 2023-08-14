import { Header } from '@/components/Header';
import { Inter } from 'next/font/google';
import Link from 'next/link';
import { MagnifyingGlass } from 'phosphor-react';

const inter = Inter({ subsets: ['latin'] });

export default function Home() {
  return (
    <div className={`flex min-h-screen flex-col items-center justify-between `}>
      <Header />

      <main className='max-w-5xl w-full mx-auto px-4'>
        <div className='h-80 bg-neutral-800'></div>

        <form className='mt-5 flex gap-4'>
          <input
            id='search'
            type='text'
            className='p-4 rounded border border-neutral-300 bg-transparent w-full'
            placeholder='Cuidador de animais asquerosos e peçonhentos'
          />
          <button className='bg-cyan-700 text-neutral-50 px-8 rounded hover:bg-cyan-600 transition-colors'>
            <MagnifyingGlass className='w-5 h-5' />
          </button>
        </form>

        <div className='my-10'>
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
        </div>
      </main>
    </div>
  );
}
