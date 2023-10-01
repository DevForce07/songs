import { Header } from '@/components/Header';
import Image from 'next/image';
import Link from 'next/link';
import * as Tabs from '@radix-ui/react-tabs';

export default function Entrar() {
  return (
    <>
      <Header />

      <div className='container mx-auto'>
        <main className='flex'>
          <div className='flex-1 flex flex-col items-center mt-6'>
            <h1 className='text-3xl my-8 font-bold'>Entre em sua conta</h1>

            <Tabs.Root defaultValue='ong'>
              <Tabs.List className='flex justify-between gap-3 bg-neutral-100 p-2 rounded mb-4'>
                <Tabs.Trigger
                  value='ong'
                  className='data-[state=active]:bg-cyan-600 data-[state=active]:text-neutral-50 hover:bg-neutral-200 p-3 rounded flex-1'
                >
                  Sou ONG
                </Tabs.Trigger>
                <Tabs.Trigger
                  value='voluntario'
                  className='data-[state=active]:bg-cyan-600 data-[state=active]:text-neutral-50 hover:bg-neutral-200 p-3 rounded flex-1'
                >
                  Sou voluntário
                </Tabs.Trigger>
              </Tabs.List>

              <Tabs.Content value='ong'>
                <form className='flex flex-col gap-10'>
                  <label htmlFor='' className='flex flex-col gap-2'>
                    CNPJ:
                    <input
                      type='text'
                      className='p-4 rounded border border-neutral-300 w-[400px] bg-transparent'
                      placeholder='xx.xxx.xxx/xxxx-xx'
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
                    href='/criar-conta-ong'
                    className='text-cyan-700 hover:underline'
                  >
                    Crie aqui
                  </Link>
                </p>
              </Tabs.Content>

              <Tabs.Content value='voluntario'>
                <form className='flex flex-col gap-10'>
                  <label htmlFor='' className='flex flex-col gap-2'>
                    E-mail:
                    <input
                      type='text'
                      className='p-4 rounded border border-neutral-300 w-[400px] bg-transparent'
                      placeholder='fulano@email.com'
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
                    href='/criar-conta-voluntario'
                    className='text-cyan-700 hover:underline'
                  >
                    Crie aqui
                  </Link>
                </p>
              </Tabs.Content>
            </Tabs.Root>
          </div>
        </main>
      </div>
    </>
  );
}
