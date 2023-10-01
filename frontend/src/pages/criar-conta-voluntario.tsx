import { Header } from '@/components/Header';

export default function CriarContaVoluntario() {
  return (
    <>
      <Header />

      <div className='max-w-3xl w-full mx-auto'>
        <h1 className='text-3xl my-8 font-bold'>Crie a sua conta</h1>

        <main className='mb-8'>
          <form className='flex flex-col gap-10'>
            <label htmlFor='name' className='flex flex-col gap-2'>
              Seu nome completo:
              <input
                id='name'
                type='text'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='Fulano de Tal'
              />
            </label>

            <label htmlFor='cpf' className='flex flex-col gap-2'>
              CPF:
              <input
                id='cpf'
                type='text'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='xxx.xxx.xxx-xx'
              />
            </label>

            <label htmlFor='email' className='flex flex-col gap-2'>
              E-mail:
              <input
                id='email'
                type='email'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='fulano@email.com'
              />
            </label>

            <label htmlFor='birthDate'>
              Data de nascimento:
              <input
                id='birthDate'
                type='date'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
              />
            </label>

            <label htmlFor='password' className='flex flex-col gap-2'>
              Escolha uma senha:
              <input
                id='password'
                type='password'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='********'
              />
            </label>

            <label htmlFor='confirm-password' className='flex flex-col gap-2'>
              Confirme a senha:
              <input
                id='confirm-password'
                type='password'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='********'
              />
            </label>

            <button className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors'>
              Criar
            </button>
          </form>
        </main>
      </div>
    </>
  );
}
