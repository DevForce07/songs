import { Header } from '@/components/Header';
import { Inter } from 'next/font/google';
import Link from 'next/link';

const inter = Inter({ subsets: ['latin'] });

export default function Home() {
  return (
    <div className={`flex min-h-screen flex-col items-center justify-between `}>
      <Header />

      <main className='max-w-5xl w-full mx-auto px-4'>
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

      <div className='mx-auto flex min-h-screen max-w-screen-sm items-center justify-center'>
        <div className='h-36 w-full rounded-lg bg-gradient-to-r from-green-500 via-green-500 to-yellow-400 p-1'>
          <div className='flex h-full w-full items-center justify-center bg-gray-800 back rounded-lg'>
            <h1 className='text-2xl font-black text-white px-4'>
              the quick brown fox jumps over the lazy dog
            </h1>
          </div>
        </div>
      </div>
    </div>
  );
}
