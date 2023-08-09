import { Header } from '@/components/Header';
import { WhatsappLogo } from 'phosphor-react';

export default function OngProfile() {
  return (
    <>
      <Header />

      <main className='max-w-4xl w-full mx-auto px-4'>
        <div className='h-80 bg-neutral-800'></div>

        <div className='mt-10'>
          <h1 className='text-3xl font-bold mb-6 text-cyan-700'>ONG do Bem</h1>

          <div className='flex flex-col md:flex-row gap-6'>
            <p className='flex-1 text-lg leading-relaxed'>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Quis
              voluptatum, quibusdam, voluptate, quia voluptas quod
              exercitationem quos voluptatibus quas doloribus quidem
              consequatur. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam,
              voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam,
              voluptatum. Quisquam, voluptatum.
            </p>

            <div className='md:max-w-xs'>
              <h2 className='text-xl font-bold text-cyan-700'>Endereço</h2>
              <p className='text-lg mt-2 mb-6'>
                Rua das flores líquidas, 123, Centro, Maringá - PR
              </p>

              <button className='p-4 bg-transparent border border-green-600 font-bold rounded text-green-700 flex items-center gap-2 hover:bg-green-600 hover:text-neutral-50 transition-colors justify-center w-full'>
                <WhatsappLogo size={26} />
                Entre em contato
              </button>
            </div>
          </div>
        </div>

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
    </>
  );
}
