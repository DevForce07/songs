import { Header } from '@/components/Header';

export default function CriarVaga() {
  return (
    <>
      <Header />

      <div className='max-w-3xl w-full mx-auto'>
        <h1 className='text-3xl my-8 font-bold'>Crie a vaga</h1>

        <main className='mb-8'>
          <form className='flex flex-col gap-10'>
            <label htmlFor='title' className='flex flex-col gap-2'>
              Título da vaga:
              <input
                id='title'
                type='text'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='Cuidador de animais asquerosos e peçonhentos'
              />
            </label>

            <label htmlFor='description' className='flex flex-col gap-2'>
              Descrição da vaga:
              <textarea
                id='description'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full resize-none'
                placeholder='lorem ipsum dolor sit amet'
              />
            </label>

            <label htmlFor='quantity' className='flex flex-col gap-2'>
              Quantidade de vagas:
              <input
                id='quantity'
                type='number'
                className='p-4 rounded border border-neutral-300 bg-transparent w-40'
                placeholder='1'
              />
            </label>

            <button className='bg-cyan-700 p-4 rounded text-neutral-50 font-bold hover:bg-cyan-800 transition-colors'>
              Criar vaga
            </button>
          </form>
        </main>
      </div>
    </>
  );
}
