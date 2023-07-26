import { Header } from '@/components/Header';

export default function CriarVaga() {
  return (
    <>
      <Header />

      <div className='max-w-3xl w-full mx-auto'>
        <h1 className='text-3xl my-8 font-bold'>Criar vaga</h1>

        <main className='mb-8'>
          <form className='flex flex-col gap-10'>
            <label htmlFor='title' className='flex flex-col gap-2'>
              Título da vaga:
              <input
                id='title'
                type='text'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='Ex: Cuidador de animais'
              />
            </label>

            <label htmlFor='description' className='flex flex-col gap-2'>
              Descrição:
              <textarea
                id='description'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full resize-none h-32'
                placeholder='Descreva aqui tudo sobre a vaga'
              ></textarea>
            </label>

            <label htmlFor='quantity' className='flex flex-col gap-2'>
              Quantidade de vagas:
              <input
                id='quantity'
                type='number'
                className='p-4 rounded border border-neutral-300 bg-transparent w-full'
                placeholder='Ex: 3'
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
