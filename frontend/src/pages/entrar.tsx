import { Header } from '@/components/Header';
import Image from 'next/image';
import Link from 'next/link';

export default function Entrar() {
  return (
    <>
      <Header />

      <div className='container mx-auto'>
        <main className='flex'>
          {/* <div className='w-[500px]'>
            <Image
              className='w-full object-cover h-full'
              src='https://github.com/yuripiresalves.png'
              alt='imagem'
              width={300}
              height={900}
            />
          </div> */}

          <div className='flex-1 flex flex-col items-center mt-32'>
            <h1 className='text-3xl my-8 font-bold'>Entre para criar a vaga</h1>
            <form className='flex flex-col gap-10'>
              <label htmlFor='' className='flex flex-col gap-2'>
                CNPJ:
                <input
                  type='text'
                  className='p-4 rounded border border-neutral-300 w-[400px] bg-transparent'
                  placeholder='xxxxxx'
                />
              </label>

              <label htmlFor='' className='flex flex-col gap-2'>
                Senha:
                <input
                  type='password'
                  className='p-4 rounded border border-neutral-300 w-[400px] bg-transparent'
                  placeholder='************'
                />
              </label>

              <button className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors'>
                Entrar
              </button>
            </form>

            <p className='mt-4'>
              Ainda não possui uma conta?{' '}
              <Link
                href='/criar-conta'
                className='text-cyan-700 hover:underline'
              >
                Crie aqui
              </Link>
            </p>
          </div>
        </main>
      </div>
    </>
  );
}
